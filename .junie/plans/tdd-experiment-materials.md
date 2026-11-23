---
sessionId: session-261123-090133-a149
---

# Project Overview

### Current Project Structure

- **Language**: Java 21 (no package, default package)
- **Build**: Maven (`pom.xml`) with JUnit 5.10.0 and maven-surefire-plugin 3.1.2
- **CI**: GitHub Actions (`.github/workflows/maven.yml`) — compiles and tests on push to `main`, deploys to GitHub Pages
- **Existing code**:
  - `src/main/java/TemperatureConverter.java` — 3 static methods: `celsiusToFahrenheit`, `fahrenheitToCelsius`, `celsiusToKelvin`
  - `src/test/java/TemperatureConverterTest.java` — 3 basic tests (one per method)
- **Other files**: `site/index.html` (GitHub Pages), `.gitignore` (target/, .idea/, *.iml)
- **No README.md** exists yet

### Design Decision: Default Package
The existing code uses no package (default package). All new methods, tests, and the reference solution will follow the same convention to keep consistency with the student project style.

# Requirements

### Overview
Prepare experiment materials for a university research study (COMP 3520) comparing incremental TDD (one failing test at a time) vs. bulk requirements when using AI to generate code.

### 12 Features (Specified in `docs/FEATURES.md`)

| # | Feature | Method Signature | Description |
|---|---------|-----------------|-------------|
| 1 | Kelvin to Celsius | `kelvinToCelsius(double kelvin)` | K→C conversion: C = K − 273.15 |
| 2 | Kelvin to Fahrenheit | `kelvinToFahrenheit(double kelvin)` | K→F conversion: F = (K−273.15)×9/5 + 32 |
| 3 | Fahrenheit to Kelvin | `fahrenheitToKelvin(double fahrenheit)` | F→K conversion: K = (F−32)×5/9 + 273.15 |
| 4 | Reject below absolute zero (C) | `celsiusToFahrenheit(double celsius)` — extend existing | Throw `IllegalArgumentException` if celsius < −273.15 |
| 5 | Reject below absolute zero (F) | `fahrenheitToCelsius(double fahrenheit)` — extend existing | Throw `IllegalArgumentException` if fahrenheit < −459.67 |
| 6 | Reject below absolute zero (K) | `celsiusToKelvin` / `kelvinToCelsius` / `kelvinToFahrenheit` — extend | Throw `IllegalArgumentException` if kelvin < 0 |
| 7 | Round to N decimals | `roundResult(double value, int decimals)` | Round a double to N decimal places |
| 8 | Parse string like "25C" | `parseTemperature(String input)` | Return a `Temperature` record(value, unit) or throw on invalid |
| 9 | Invalid unit handling | `convert(double value, String fromUnit, String toUnit)` | Generic converter; throw on unknown unit |
| 10 | Convert list of values | `convertAll(double[] values, String from, String to)` | Batch conversion returning `double[]` |
| 11 | Convert with rounding | `convertAndRound(double value, String from, String to, int decimals)` | Combine convert + round |
| 12 | Absolute zero check | `isBelowAbsoluteZero(double value, String unit)` | Return true if value is below absolute zero for the given unit |

### Deliverables
1. `docs/FEATURES.md` — full specs with inputs, outputs, edge cases, exceptions
2. Method stubs in `src/main/java/TemperatureConverter.java` (throw `UnsupportedOperationException`)
3. Visible tests in `src/test/java/visible/` (3–4 per feature)
4. Hidden tests in `hidden-tests/` (4–6 per feature, off Maven build path)
5. Reference solution in `reference-solution/` (complete implementations)
6. Scripts in `scripts/` for running tests and collecting results
7. Results templates in `results/`
8. Cyclomatic complexity measurement added to Maven build
9. Updated `README.md` with setup and run instructions
10. `.gitignore` updated to exclude `hidden-tests/` and `reference-solution/`

### Out of Scope
- No feature logic implementation in `src/main/java/` (only stubs)
- No commits
- No changes to existing CI workflow

# Technical Design

### Current Implementation
- `TemperatureConverter.java`: 3 static methods, default package, no dependencies
- `TemperatureConverterTest.java`: 3 tests, uses `assertEquals` with DELTA=0.0001
- Maven: Java 21 compiler, JUnit 5.10.0, surefire 3.1.2

### Key Decisions

1. **Default package throughout** — matches existing code style
2. **`Temperature` record** for Feature 8 — simple data carrier `(double value, String unit)`
3. **Hidden tests outside `src/`** — placed in top-level `hidden-tests/` directory, not on Maven build path, run via separate Maven invocation with `-Dtest` pointing to the hidden test classes copied temporarily
4. **Reference solution** mirrors `TemperatureConverter.java` structure — a complete `TemperatureConverter.java` in `reference-solution/`
5. **Cyclomatic complexity** — add `jacoco-maven-plugin` (which includes complexity reporting) or `cobertura-maven-plugin`. Using `jacoco-maven-plugin` as it is the most widely adopted and integrates with surefire.
6. **Scripts** — PowerShell-compatible `.ps1` and Bash `.sh` variants for Windows (matching the user's OS)

### File Structure

```
project-root/
├── docs/
│   └── FEATURES.md                          # 12 feature specs
├── src/
│   ├── main/java/
│   │   ├── TemperatureConverter.java        # modified: add stubs + Temperature record
│   │   └── Temperature.java                 # new: record for parsed temperature
│   └── test/java/
│       ├── visible/                         # new: visible tests
│       │   ├── KelvinConversionVisibleTest.java
│       │   ├── AbsoluteZeroVisibleTest.java
│       │   ├── RoundingVisibleTest.java
│       │   ├── ParseTemperatureVisibleTest.java
│       │   ├── ConvertGenericVisibleTest.java
│       │   ├── ConvertListVisibleTest.java
│       │   ├── ConvertAndRoundVisibleTest.java
│       │   └── BelowAbsoluteZeroVisibleTest.java
│       └── TemperatureConverterTest.java    # existing, unchanged
├── hidden-tests/                            # new: off build path
│   ├── KelvinConversionHiddenTest.java
│   ├── AbsoluteZeroHiddenTest.java
│   ├── RoundingHiddenTest.java
│   ├── ParseTemperatureHiddenTest.java
│   ├── ConvertGenericHiddenTest.java
│   ├── ConvertListHiddenTest.java
│   ├── ConvertAndRoundHiddenTest.java
│   └── BelowAbsoluteZeroHiddenTest.java
├── reference-solution/
│   └── TemperatureConverter.java            # full implementation
├── scripts/
│   ├── run_visible.sh / run_visible.ps1     # run visible tests → results.csv
│   ├── run_hidden.sh / run_hidden.ps1       # run hidden tests → results.csv
│   └── validate_reference.sh / .ps1         # copy ref solution, run all tests
├── results/
│   ├── results.csv                          # per-feature pass/fail
│   └── cycle_log.csv                        # template with columns
├── .gitignore                               # updated
├── pom.xml                                  # updated: add jacoco plugin
└── README.md                                # new
```

### Method Stubs (in `TemperatureConverter.java`)

```java
// Feature 1
public static double kelvinToCelsius(double kelvin) { throw new UnsupportedOperationException(); }

// Feature 2
public static double kelvinToFahrenheit(double kelvin) { throw new UnsupportedOperationException(); }

// Feature 3
public static double fahrenheitToKelvin(double fahrenheit) { throw new UnsupportedOperationException(); }

// Feature 7
public static double roundResult(double value, int decimals) { throw new UnsupportedOperationException(); }

// Feature 8
public static Temperature parseTemperature(String input) { throw new UnsupportedOperationException(); }

// Feature 9
public static double convert(double value, String fromUnit, String toUnit) { throw new UnsupportedOperationException(); }

// Feature 10
public static double[] convertAll(double[] values, String from, String to) { throw new UnsupportedOperationException(); }

// Feature 11
public static double convertAndRound(double value, String from, String to, int decimals) { throw new UnsupportedOperationException(); }

// Feature 12
public static boolean isBelowAbsoluteZero(double value, String unit) { throw new UnsupportedOperationException(); }
```

Features 4–6 extend existing methods with validation at the top of each method (still stubs — the validation logic is part of the reference solution only).

### Test Structure
- **Visible tests**: happy path + one simple edge case per feature (3–4 tests)
- **Hidden tests**: boundary values, exception cases, null/empty inputs, precision edge cases (4–6 tests)
- Both use `@Test` with `assertEquals` (DELTA=0.0001) and `assertThrows`

### Scripts
- `run_visible.sh`: runs `mvn test -Dtest=visible.*` and parses surefire reports into `results/results.csv`
- `run_hidden.sh`: copies hidden tests into a temp test source dir, runs them, writes results
- `validate_reference.sh`: copies reference solution over `src/main/java/TemperatureConverter.java`, runs all tests, restores original

### Cyclomatic Complexity
Add `jacoco-maven-plugin` to `pom.xml`:
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals><goal>report</goal></goals>
        </execution>
    </executions>
</plugin>
```
Report generated at `target/site/jacoco/index.html` — includes cyclomatic complexity per class/method.

# Testing Strategy

### Validation Approach
After all files are created, the reference solution will be validated by:
1. Copying `reference-solution/TemperatureConverter.java` over `src/main/java/TemperatureConverter.java`
2. Running `mvn test` (visible tests)
3. Running hidden tests via the script
4. Confirming 100% pass rate
5. Restoring the stub version

Any test that fails against the reference solution will be fixed (the test is wrong, not the solution).

### Key Scenarios
- Each visible test covers the primary use case of its feature
- Each hidden test covers at least one edge case not in visible tests
- Reference solution passes all 12×(3-4 + 4-6) ≈ 120+ tests

### Edge Cases
- Absolute zero boundaries: exactly −273.15°C, −459.67°F, 0K (should pass), values 0.0001 below (should fail)
- Null/empty string inputs to parse and convert methods
- Negative decimals in `roundResult`
- Empty array in `convertAll`
- Unknown unit strings in `convert` and `isBelowAbsoluteZero`
- Precision: values that produce long decimals (e.g., 1°F in Celsius)

# Delivery Steps

###   Step 1: Add method stubs and Temperature record
- Create `src/main/java/Temperature.java` record with `value` (double) and `unit` (String).\n- Add 9 new method stubs to `TemperatureConverter.java` (all throw `UnsupportedOperationException`).\n- Keep existing 3 methods unchanged.

###   Step 2: Write visible tests for all 12 features
- Create `src/test/java/visible/` package.\n- Write 8 visible test classes (grouped by related features), each with 3–4 tests covering happy paths and simple edge cases.\n- Tests use JUnit 5 `@Test`, `assertEquals` with DELTA, and `assertThrows`.

###   Step 3: Write hidden tests for all 12 features
- Create `hidden-tests/` at project root (off Maven build path).\n- Write 8 hidden test classes, each with 4–6 tests covering boundary values, exception cases, null/empty inputs, and precision edge cases.\n- Update `.gitignore` to exclude `hidden-tests/` and `reference-solution/`.

###   Step 4: Write reference solution and validation script
- Create `reference-solution/TemperatureConverter.java` with complete implementations of all 12 features.\n- Create `scripts/validate_reference.ps1` (and `.sh`) that copies the reference solution over the stub, runs all visible + hidden tests, reports results, and restores the stub.\n- Fix any test that fails against the reference solution.

###   Step 5: Create run scripts and results templates
- `scripts/run_visible.ps1` / `.sh`: runs visible tests via Maven, writes per-feature pass/fail to `results/results.csv`.\n- `scripts/run_hidden.ps1` / `.sh`: copies hidden tests temporarily, runs them, appends results to `results/results.csv`.\n- `results/cycle_log.csv`: template with columns `condition,feature,attempt,tests_passed,tests_failed,regression_failures,notes`.

### ✓ Step 6: Add complexity plugin and README
- Added `jacoco-maven-plugin` (v0.8.11) to `pom.xml` for cyclomatic complexity reporting.\n- Created `README.md` with: project overview, setup instructions (`mvn compile`, `mvn test`), how to run visible/hidden scripts, how to interpret results, and how to generate the complexity report (`mvn test` → `target/site/jacoco/index.html`).