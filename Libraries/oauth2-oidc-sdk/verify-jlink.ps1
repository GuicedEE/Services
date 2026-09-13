param([string]$JdkHome = $env:JAVA_HOME)
$ErrorActionPreference = 'Stop'
if (-not $JdkHome) { throw 'Set JAVA_HOME or pass -JdkHome (JDK 25+).' }
$env:JAVA_HOME = $JdkHome
$smoke = Join-Path $PSScriptRoot 'src/test/jlink'
Push-Location $smoke
try {
    & mvn -B package
    if ($LASTEXITCODE -ne 0) { throw 'Smoke consumer build failed.' }
    $target = Join-Path $smoke 'target'
    $image = Join-Path $target ('runtime-' + [Guid]::NewGuid().ToString('N'))
    $modulePath = @((Join-Path $target 'modules'), (Join-Path $target 'nimbus-jlink-smoke-1.0.0.jar')) -join [IO.Path]::PathSeparator
    & "$JdkHome/bin/jlink" --module-path $modulePath --add-modules com.guicedee.services.nimbus.test --output $image
    if ($LASTEXITCODE -ne 0) { throw 'jlink failed.' }
    & "$image/bin/java" -m com.guicedee.services.nimbus.test/com.guicedee.services.nimbus.test.NimbusSmoke
    if ($LASTEXITCODE -ne 0) { throw 'Linked runtime checks failed.' }
    Write-Output "Verified runtime: $image"
} finally {
    Pop-Location
}
