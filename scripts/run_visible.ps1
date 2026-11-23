# Run Visible Tests
# Runs all visible tests and writes per-feature pass/fail counts to results/results.csv

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $projectRoot
Set-Location $projectRoot

$resultsDir = "results"
New-Item -ItemType Directory -Force -Path $resultsDir | Out-Null
$resultsFile = Join-Path $resultsDir "results.csv"

Write-Host "=== Running Visible Tests ===" -ForegroundColor Cyan

# Run visible tests
mvn test -Dtest=visible.* -q

# Parse surefire reports
$reportDir = "target\surefire-reports"
$csvLines = @()
$csvLines += "feature,tests_passed,tests_failed,total"

Get-ChildItem "$reportDir\TEST-*.xml" | ForEach-Object {
    $xml = [xml](Get-Content $_.FullName)
    $testSuite = $xml.testsuite
    $name = $testSuite.name
    $passed = [int]$testSuite.tests - [int]$testSuite.failures - [int]$testSuite.errors
    $failed = [int]$testSuite.failures + [int]$testSuite.errors
    $total = [int]$testSuite.tests
    $csvLines += "$name,$passed,$failed,$total"
}

$csvLines | Set-Content $resultsFile -Encoding UTF8
Write-Host "`nResults written to $resultsFile" -ForegroundColor Green
Write-Host "Done." -ForegroundColor Cyan
