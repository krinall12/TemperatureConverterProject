#!/bin/bash
# Run Visible Tests
# Runs all visible tests and writes per-feature pass/fail counts to results/results.csv

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$PROJECT_ROOT"

RESULTS_DIR="results"
mkdir -p "$RESULTS_DIR"
RESULTS_FILE="$RESULTS_DIR/results.csv"

echo "=== Running Visible Tests ==="

# Run visible tests
mvn test -Dtest=visible.* -q

# Parse surefire reports
REPORT_DIR="target/surefire-reports"
echo "feature,tests_passed,tests_failed,total" > "$RESULTS_FILE"

for report in "$REPORT_DIR"/TEST-*.xml; do
    [ -f "$report" ] || continue
    name=$(grep -oP 'name="\K[^"]+' "$report" | head -1)
    tests=$(grep -oP 'tests="\K[0-9]+' "$report" | head -1)
    failures=$(grep -oP 'failures="\K[0-9]+' "$report" | head -1)
    errors=$(grep -oP 'errors="\K[0-9]+' "$report" | head -1)
    failed=$((failures + errors))
    passed=$((tests - failed))
    echo "$name,$passed,$failed,$tests" >> "$RESULTS_FILE"
done

echo ""
echo "Results written to $RESULTS_FILE"
echo "Done."
