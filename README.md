# Temperature Converter — TDD Experiment

A Java Maven project for a university research study (COMP 3520) comparing incremental TDD (one failing test at a time) vs. bulk requirements when using AI to generate code.

## Project Overview

This project contains a `TemperatureConverter` class with method stubs for 12 temperature-related features. Students work with AI to implement each feature by writing and running JUnit 5 tests.

- **Language**: Java 21 (default package)
- **Build**: Maven
- **Testing**: JUnit 5.10.0
- **Coverage**: JaCoCo 0.8.11

## Setup

```bash
mvn compile
```

## Running Tests

### All tests (existing + visible)

```bash
mvn test
```

### Only visible tests

```bash
mvn test -Dtest=visible.*
```

### Using the run scripts

**PowerShell (Windows):**

```powershell
.\scripts\run_visible.ps1
.\scripts\run_hidden.ps1
```

**Bash (Linux/Mac):**

```bash
bash scripts/run_visible.sh
bash scripts/run_hidden.sh
```

Results are written to `results/results.csv`.

## Feature Specifications

See [`docs/FEATURES.md`](docs/FEATURES.md) for detailed specs of all 12 features, including method signatures, formulas, inputs, outputs, edge cases, and expected exceptions.

## Experiment Structure

| Directory | Purpose |
|-----------|---------|
| `src/main/java/` | Source code with method stubs |
| `src/test/java/visible/` | Visible tests (3–4 per feature) |
| `hidden-tests/` | Hidden tests (4–6 per feature, not on build path) |
| `reference-solution/` | Complete reference implementation |
| `scripts/` | Run and validation scripts |
| `results/` | CSV templates for tracking results |

## Validating the Reference Solution

To confirm all tests pass against a correct implementation:

**PowerShell:**

```powershell
.\scripts\validate_reference.ps1
```

**Bash:**

```bash
bash scripts/validate_reference.sh
```

This temporarily replaces the stubs with the reference solution, runs all visible and hidden tests, then restores the stubs.

## Cyclomatic Complexity Report

After running tests, JaCoCo generates a coverage and complexity report:

```bash
mvn test
```

Open `target/site/jacoco/index.html` in a browser to view:
- Line and branch coverage
- Cyclomatic complexity per class and method

## Tracking Results

Use `results/cycle_log.csv` to record each TDD cycle:

| Column | Description |
|--------|-------------|
| `condition` | "incremental" or "bulk" |
| `feature` | Feature number (1–12) |
| `attempt` | Attempt number for this feature |
| `tests_passed` | Number of tests that passed |
| `tests_failed` | Number of tests that failed |
| `regression_failures` | Previously passing tests that now fail |
| `notes` | Free-form notes |

## Files Created for the Experiment

- `docs/FEATURES.md` — 12 feature specifications
- `src/main/java/Temperature.java` — Record for parsed temperatures
- `src/main/java/TemperatureConverter.java` — Method stubs (throw `UnsupportedOperationException`)
- `src/test/java/visible/` — 8 visible test classes
- `hidden-tests/` — 8 hidden test classes
- `reference-solution/TemperatureConverter.java` — Full reference implementation
- `scripts/run_visible.ps1`, `run_visible.sh` — Run visible tests
- `scripts/run_hidden.ps1`, `run_hidden.sh` — Run hidden tests
- `scripts/validate_reference.ps1`, `validate_reference.sh` — Validate reference solution
- `results/cycle_log.csv` — Template for tracking TDD cycles
- `pom.xml` — Updated with JaCoCo plugin
- `README.md` — This file
