"""Compare upstream 1.8.0 / GuicedEE output on representative Maven 4 fixtures."""
from pathlib import Path
import argparse, json, subprocess, xml.etree.ElementTree as ET

parser = argparse.ArgumentParser()
parser.add_argument('--maven', default='mvn.cmd')
parser.add_argument('--out', type=Path, default=Path(__file__).resolve().parent / 'target' / 'compatibility')
args = parser.parse_args()
out = args.out.resolve(); out.mkdir(parents=True, exist_ok=True)
CUSTOM = 'com.guicedee.maven:flatten-maven-plugin:2.0'
UPSTREAM = 'org.codehaus.mojo:flatten-maven-plugin:1.8.0'

def pom(version, body):
    return '<project xmlns="http://maven.apache.org/POM/'+version+'"><modelVersion>'+version+'</modelVersion>'+body+'</project>'

def normalized(node):
    return (node.tag.split('}')[-1], tuple(sorted(node.attrib.items())), (node.text or '').strip(), tuple(normalized(c) for c in node))

results=[]
for case in ['library', 'bom', 'shaded']:
    baseline=None
    for variant, version, plugin in [('upstream40','4.0.0',UPSTREAM),('fork40','4.0.0',CUSTOM),('fork41','4.1.0',CUSTOM)]:
        root=out/case/variant; child=root/'child'; child.mkdir(parents=True,exist_ok=True)
        parent='<groupId>local.flatten.fixture</groupId><artifactId>parent</artifactId><version>1</version><packaging>pom</packaging><properties><gson.version>2.15.0</gson.version></properties><licenses><license><name>Apache-2.0</name></license></licenses><scm><url>https://example.invalid/fixture</url></scm>'
        (root/'pom.xml').write_text(pom(version,parent))
        body='<parent><groupId>local.flatten.fixture</groupId><artifactId>parent</artifactId><version>1</version></parent><artifactId>'+case+'</artifactId><name>Flatten fixture</name>'
        gson='<dependency><groupId>com.google.code.gson</groupId><artifactId>gson</artifactId><version>${gson.version}</version></dependency>'
        if case=='bom':
            body+='<packaging>pom</packaging><dependencyManagement><dependencies>'+gson+'</dependencies></dependencyManagement>'
            config='<flattenMode>bom</flattenMode><pomElements><properties>remove</properties><dependencyManagement>resolve</dependencyManagement></pomElements>'
        elif case=='library':
            body+='<dependencies>'+gson.replace('</dependency>','<optional>true</optional><exclusions><exclusion><groupId>com.google.errorprone</groupId><artifactId>error_prone_annotations</artifactId></exclusion></exclusions></dependency>')+'</dependencies><profiles><profile><id>fixture-profile</id><properties><fixture.active>true</fixture.active></properties><dependencies><dependency><groupId>org.apache.commons</groupId><artifactId>commons-lang3</artifactId><version>3.21.0</version><scope>runtime</scope></dependency></dependencies></profile></profiles>'
            config='<flattenMode>ossrh</flattenMode><flattenDependencyMode>direct</flattenDependencyMode>'
        else:
            body+='<dependencies>'+gson+'</dependencies>'
            config='<flattenMode>ossrh</flattenMode>'
            src=child/'src/main/java/fixture';src.mkdir(parents=True,exist_ok=True)
            (src/'Probe.java').write_text('package fixture; public class Probe { public String value() { return new com.google.gson.Gson().toJson("probe"); } }')
        group,artifact,pv=plugin.split(':')
        plugins='<plugin><groupId>'+group+'</groupId><artifactId>'+artifact+'</artifactId><version>'+pv+'</version><configuration><updatePomFile>true</updatePomFile>'+config+'</configuration><executions><execution><phase>verify</phase><goals><goal>flatten</goal></goals></execution></executions></plugin>'
        if case=='shaded':
            plugins+='<plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-compiler-plugin</artifactId><version>3.14.1</version><configuration><release>25</release></configuration></plugin><plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-shade-plugin</artifactId><version>3.6.1</version><executions><execution><phase>package</phase><goals><goal>shade</goal></goals></execution></executions></plugin>'
        body+='<build><plugins>'+plugins+'</plugins></build>'
        (child/'pom.xml').write_text(pom(version,body))
        cmd=[args.maven,'-B','-ntp','-N','-f',str(child/'pom.xml'),'install','-Dmaven.consumer.pom=false','-Dmaven.javadoc.skip=true']
        if case=='library':cmd+=['-Pfixture-profile']
        with (root/'build.log').open('w') as log:
            done=subprocess.run(cmd,stdout=log,stderr=subprocess.STDOUT)
        if done.returncode:raise RuntimeError('Fixture failed: '+str(root/'build.log'))
        flat=child/'.flattened-pom.xml'; model=ET.parse(flat).getroot(); canonical=normalized(model)
        if baseline is None:baseline=canonical
        assert canonical==baseline, (case,variant,'differs from upstream 4.0 output')
        ns={'m':model.tag.split('}')[0][1:]}
        assert model.findtext('m:modelVersion',namespaces=ns)=='4.0.0'
        assert model.find('m:licenses/m:license',ns) is not None
        if case=='shaded':
            assert not any(d.findtext('m:artifactId',namespaces=ns)=='gson' for d in model.findall('m:dependencies/m:dependency',ns)), 'Shaded Gson was reintroduced'
        if case=='library':
            assert model.find('m:dependencies/m:dependency/m:exclusions/m:exclusion',ns) is not None
            assert model.findtext('m:dependencies/m:dependency/m:optional',namespaces=ns)=='true'
        if case=='bom':
            assert model.findtext('m:dependencyManagement/m:dependencies/m:dependency/m:version',namespaces=ns)=='2.15.0'
        installed=Path.home()/'.m2/repository/local/flatten/fixture'/case/'1'/f'{case}-1.pom'
        assert normalized(ET.parse(installed).getroot())==canonical, 'Installed POM differs from flatten output'
        results.append({'case':case,'variant':variant,'outputMatchesUpstream':True,'installedPomMatches':True})
        print(case,variant,'passed',flush=True)
(out/'results.json').write_text(json.dumps(results,indent=2))
print('All nine representative builds passed.')
