# Validate Reference Solution
# Copies the reference solution over the stub, runs all tests, and restores the stub.

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $projectRoot
Set-Location $projectRoot

$stubFile = "src\main\java\TemperatureConverter.java"
$refFile = "reference-solution\TemperatureConverter.java"
$backupFile = "src\main\java\TemperatureConverter.java.backup"

Write-Host "=== Validating Reference Solution ===" -ForegroundColor Cyan

# Backup the stub
Copy-Item $stubFile $backupFile
Write-Host "Backed up stub to $backupFile"

# Copy reference solution over stub
Copy-Item $refFile $stubFile -Force
Write-Host "Copied reference solution to $stubFile"

# Also need Temperature.java for reference solution
Copy-Item "src\main\java\Temperature.java" "src\main\java\Temperature.java.backup" -ErrorAction SilentlyContinue

# Run visible tests
Write-Host "`n=== Running Visible Tests ===" -ForegroundColor Yellow
$visibleResult = mvn test -Dtest=visible.* -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "VISIBLE TESTS FAILED!" -ForegroundColor Red
} else {
    Write-Host "Visible tests passed." -ForegroundColor Green
}

# Copy hidden tests to a temp location and run them
Write-Host "`n=== Running Hidden Tests ===" -ForegroundColor Yellow
$tempTestDir = "src\test\java\hidden-temp"
New-Item -ItemType Directory -Force -Path $tempTestDir | Out-Null
Copy-Item "hidden-tests\*.java" $tempTestDir -Force

$hiddenResult = mvn test -Dtest=hidden-temp.* -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "HIDDEN TESTS FAILED!" -ForegroundColor Red
} else {
    Write-Host "Hidden tests passed." -ForegroundColor Green
}

# Clean up hidden temp tests
Remove-Item $tempTestDir -Recurse -Force

# Restore the stub
Copy-Item $backupFile $stubFile -Force
Remove-Item $backupFile
Write-Host "`nRestored stub version." -ForegroundColor Cyan

Write-Host "`n=== Validation Complete ===" -ForegroundColor Cyan
