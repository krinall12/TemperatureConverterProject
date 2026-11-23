#!/bin/bash
# Validate Reference Solution
# Copies the reference solution over the stub, runs all tests, and restores the stub.

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$PROJECT_ROOT"

STUB_FILE="src/main/java/TemperatureConverter.java"
REF_FILE="reference-solution/TemperatureConverter.java"
BACKUP_FILE="src/main/java/TemperatureConverter.java.backup"

echo "=== Validating Reference Solution ==="

# Backup the stub
cp "$STUB_FILE" "$BACKUP_FILE"
echo "Backed up stub to $BACKUP_FILE"

# Copy reference solution over stub
cp "$REF_FILE" "$STUB_FILE"
echo "Copied reference solution to $STUB_FILE"

# Run visible tests
echo ""
echo "=== Running Visible Tests ==="
if mvn test -Dtest=visible.* -q; then
    echo "Visible tests passed."
else
    echo "VISIBLE TESTS FAILED!"
fi

# Copy hidden tests to a temp location and run them
echo ""
echo "=== Running Hidden Tests ==="
TEMP_TEST_DIR="src/test/java/hidden-temp"
mkdir -p "$TEMP_TEST_DIR"
cp hidden-tests/*.java "$TEMP_TEST_DIR/"

if mvn test -Dtest=hidden-temp.* -q; then
    echo "Hidden tests passed."
else
    echo "HIDDEN TESTS FAILED!"
fi

# Clean up hidden temp tests
rm -rf "$TEMP_TEST_DIR"

# Restore the stub
cp "$BACKUP_FILE" "$STUB_FILE"
rm "$BACKUP_FILE"
echo ""
echo "Restored stub version."

echo ""
echo "=== Validation Complete ==="
