package org.hibernate.jpa.boot.internal;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.bytecode.enhance.spi.EnhancementContext;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Describes the information gleaned from a {@code <persistence-unit/>} element in a {@code persistence.xml} file
 * whether parsed directly by Hibernate or passed to us by an EE container as a
 * {@link jakarta.persistence.spi.PersistenceUnitInfo}.
 *
 * Easier to consolidate both views into a single contract and extract information through that shared contract.
 *
 * @author Steve Ebersole
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ParsedPersistenceXmlDescriptorMixin  {
    @JsonIgnore
    private URL persistenceUnitRootUrl;

    private String name;
    @JsonProperty("non-jta-data-source")
    private Object nonJtaDataSource;
    @JsonProperty("jta-data-source")
    private Object jtaDataSource;
    @JsonProperty("provider")
    private String providerClassName;
    @JsonProperty("transaction-type")
    private PersistenceUnitTransactionType transactionType;
    @JsonProperty("use-quoted-identifiers")
    private boolean useQuotedIdentifiers;
    @JsonProperty("exclude-unlisted-classes")
    private boolean excludeUnlistedClasses;
    @JsonProperty("validation-mode")
    private ValidationMode validationMode;
    @JsonProperty("shared-cache-mode")
    private SharedCacheMode sharedCacheMode;

    public ParsedPersistenceXmlDescriptorMixin() {

    }

    @JsonProperty("properties")
    private Properties properties = new Properties();

    @JsonProperty("class")
    private List<String> classes = new ArrayList<>();

    private List<String> mappingFiles = new ArrayList<>();
    private List<URL> jarFileUrls = new ArrayList<>();

    public ParsedPersistenceXmlDescriptorMixin(URL persistenceUnitRootUrl) {
        //super(persistenceUnitRootUrl);
        this.persistenceUnitRootUrl = persistenceUnitRootUrl;
    }

    
    public URL getPersistenceUnitRootUrl() {
        return persistenceUnitRootUrl;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Object getNonJtaDataSource() {
        return nonJtaDataSource;
    }

    public void setNonJtaDataSource(Object nonJtaDataSource) {
        this.nonJtaDataSource = nonJtaDataSource;
    }

    
    public Object getJtaDataSource() {
        return jtaDataSource;
    }

    public void setJtaDataSource(Object jtaDataSource) {
        this.jtaDataSource = jtaDataSource;
    }

    
    public String getProviderClassName() {
        return providerClassName;
    }

    public void setProviderClassName(String providerClassName) {
        this.providerClassName = providerClassName;
    }

    
    public PersistenceUnitTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(PersistenceUnitTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    
    public boolean isUseQuotedIdentifiers() {
        return useQuotedIdentifiers;
    }

    public void setUseQuotedIdentifiers(boolean useQuotedIdentifiers) {
        this.useQuotedIdentifiers = useQuotedIdentifiers;
    }

    
    public Properties getProperties() {
        return properties;
    }

    
    public boolean isExcludeUnlistedClasses() {
        return excludeUnlistedClasses;
    }

    public void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
        this.excludeUnlistedClasses = excludeUnlistedClasses;
    }

    
    public ValidationMode getValidationMode() {
        return validationMode;
    }

    public void setValidationMode(String validationMode) {
        this.validationMode = ValidationMode.valueOf( validationMode );
    }

    
    public SharedCacheMode getSharedCacheMode() {
        return sharedCacheMode;
    }

    public void setSharedCacheMode(String sharedCacheMode) {
        this.sharedCacheMode = SharedCacheMode.valueOf( sharedCacheMode );
    }

    
    public List<String> getManagedClassNames() {
        return classes;
    }

    public void addClasses(String... classes) {
        addClasses( Arrays.asList( classes ) );
    }

    public void addClasses(List<String> classes) {
        this.classes.addAll( classes );
    }

    
    public List<String> getMappingFileNames() {
        return mappingFiles;
    }

    public void addMappingFiles(String... mappingFiles) {
        addMappingFiles( Arrays.asList( mappingFiles ) );
    }

    public void addMappingFiles(List<String> mappingFiles) {
        this.mappingFiles.addAll( mappingFiles );
    }

    
    public List<URL> getJarFileUrls() {
        return jarFileUrls;
    }

    public void addJarFileUrl(URL jarFileUrl) {
        jarFileUrls.add( jarFileUrl );
    }

    
    public ClassLoader getClassLoader() {
        return null;
    }

    
    public ClassLoader getTempClassLoader() {
        return null;
    }

    
    public void pushClassTransformer(EnhancementContext enhancementContext) {
        // todo : log a message that this is currently not supported...
    }
}
