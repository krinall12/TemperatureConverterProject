#!/bin/bash
# Run Hidden Tests
# Copies hidden tests to a temp location, runs them, and writes results to results/results.csv

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$PROJECT_ROOT"

RESULTS_DIR="results"
mkdir -p "$RESULTS_DIR"
RESULTS_FILE="$RESULTS_DIR/results.csv"

echo "=== Running Hidden Tests ==="

# Copy hidden tests to temp location
TEMP_TEST_DIR="src/test/java/hidden-temp"
mkdir -p "$TEMP_TEST_DIR"
cp hidden-tests/*.java "$TEMP_TEST_DIR/"

# Run hidden tests
mvn test -Dtest=hidden-temp.* -q

# Parse surefire reports
REPORT_DIR="target/surefire-reports"
echo "feature,tests_passed,tests_failed,total" > "$RESULTS_FILE"

for report in "$REPORT_DIR"/TEST-*.xml; do
    [ -f "$report" ] || continue
    name=$(grep -oP 'name="\K[^"]+' "$report" | head -1)
    if echo "$name" | grep -qi "Hidden"; then
        tests=$(grep -oP 'tests="\K[0-9]+' "$report" | head -1)
        failures=$(grep -oP 'failures="\K[0-9]+' "$report" | head -1)
        errors=$(grep -oP 'errors="\K[0-9]+' "$report" | head -1)
        failed=$((failures + errors))
        passed=$((tests - failed))
        echo "$name,$passed,$failed,$tests" >> "$RESULTS_FILE"
    fi
done

# Clean up
rm -rf "$TEMP_TEST_DIR"

echo ""
echo "Results written to $RESULTS_FILE"
echo "Done."
