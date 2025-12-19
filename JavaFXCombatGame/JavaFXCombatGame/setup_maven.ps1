$ErrorActionPreference = "Stop"

$mavVersion = "3.9.6"
$mavUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$mavVersion/apache-maven-$mavVersion-bin.zip"
$toolsDir = Join-Path $PSScriptRoot ".tools"
$mavDir = Join-Path $toolsDir "apache-maven-$mavVersion"
$mavBin = Join-Path $mavDir "bin"
$mvnExe = Join-Path $mavBin "mvn.cmd"

# Check if Maven is already installed locally
if (Test-Path $mvnExe) {
    Write-Output $mvnExe
    exit 0
}

# Create tools directory
if (-not (Test-Path $toolsDir)) {
    New-Item -ItemType Directory -Path $toolsDir | Out-Null
}

$zipPath = Join-Path $toolsDir "maven.zip"

Write-Host "Maven not found. Downloading Maven $mavVersion..."
Invoke-WebRequest -Uri $mavUrl -OutFile $zipPath

Write-Host "Extracting Maven..."
Expand-Archive -Path $zipPath -DestinationPath $toolsDir -Force

# Cleanup zip
Remove-Item $zipPath

# Verify
if (Test-Path $mvnExe) {
    Write-Output $mvnExe
} else {
    Write-Error "Failed to install Maven."
    exit 1
}
