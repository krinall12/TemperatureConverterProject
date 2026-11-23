# Run Hidden Tests
# Copies hidden tests to a temp location, runs them, and writes results to results/results.csv

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $projectRoot
Set-Location $projectRoot

$resultsDir = "results"
New-Item -ItemType Directory -Force -Path $resultsDir | Out-Null
$resultsFile = Join-Path $resultsDir "results.csv"

Write-Host "=== Running Hidden Tests ===" -ForegroundColor Cyan

# Copy hidden tests to temp location
$tempTestDir = "src\test\java\hidden-temp"
New-Item -ItemType Directory -Force -Path $tempTestDir | Out-Null
Copy-Item "hidden-tests\*.java" $tempTestDir -Force

# Run hidden tests
mvn test -Dtest=hidden-temp.* -q

# Parse surefire reports
$reportDir = "target\surefire-reports"
$csvLines = @()
$csvLines += "feature,tests_passed,tests_failed,total"

Get-ChildItem "$reportDir\TEST-hidden-temp\*.xml" -ErrorAction SilentlyContinue | ForEach-Object {
    $xml = [xml](Get-Content $_.FullName)
    $testSuite = $xml.testsuite
    $name = $testSuite.name
    $passed = [int]$testSuite.tests - [int]$testSuite.failures - [int]$testSuite.errors
    $failed = [int]$testSuite.failures + [int]$testSuite.errors
    $total = [int]$testSuite.tests
    $csvLines += "$name,$passed,$failed,$total"
}

# If no hidden-temp subdirectory, try flat XML files
if ($csvLines.Count -le 1) {
    Get-ChildItem "$reportDir\TEST-*.xml" | ForEach-Object {
        $xml = [xml](Get-Content $_.FullName)
        $testSuite = $xml.testsuite
        if ($testSuite.name -like "*Hidden*") {
            $name = $testSuite.name
            $passed = [int]$testSuite.tests - [int]$testSuite.failures - [int]$testSuite.errors
            $failed = [int]$testSuite.failures + [int]$testSuite.errors
            $total = [int]$testSuite.tests
            $csvLines += "$name,$passed,$failed,$total"
        }
    }
}

$csvLines | Set-Content $resultsFile -Encoding UTF8

# Clean up
Remove-Item $tempTestDir -Recurse -Force

Write-Host "`nResults written to $resultsFile" -ForegroundColor Green
Write-Host "Done." -ForegroundColor Cyan
