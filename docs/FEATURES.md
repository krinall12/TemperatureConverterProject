# Temperature Converter — Feature Specifications

This document specifies 12 independent features to add to the TemperatureConverter class.
Each feature includes a method signature, formula, inputs, outputs, edge cases, and expected exceptions.

---

## Feature 1: Kelvin to Celsius

**Method**: `public static double kelvinToCelsius(double kelvin)`

**Formula**: `C = K − 273.15`

**Inputs**:
- `kelvin` (double): Temperature in Kelvin. Must be ≥ 0.

**Outputs**:
- (double): Temperature in Celsius.

**Edge Cases**:
- `kelvinToCelsius(0.0)` returns `-273.15` (absolute zero).
- `kelvinToCelsius(273.15)` returns `0.0` (freezing point of water).
- `kelvinToCelsius(373.15)` returns `100.0` (boiling point of water).

**Exceptions**:
- `IllegalArgumentException` if `kelvin < 0`.

---

## Feature 2: Kelvin to Fahrenheit

**Method**: `public static double kelvinToFahrenheit(double kelvin)`

**Formula**: `F = (K − 273.15) × 9/5 + 32`

**Inputs**:
- `kelvin` (double): Temperature in Kelvin. Must be ≥ 0.

**Outputs**:
- (double): Temperature in Fahrenheit.

**Edge Cases**:
- `kelvinToFahrenheit(0.0)` returns `-459.67` (absolute zero).
- `kelvinToFahrenheit(273.15)` returns `32.0` (freezing point of water).
- `kelvinToFahrenheit(373.15)` returns `212.0` (boiling point of water).

**Exceptions**:
- `IllegalArgumentException` if `kelvin < 0`.

---

## Feature 3: Fahrenheit to Kelvin

**Method**: `public static double fahrenheitToKelvin(double fahrenheit)`

**Formula**: `K = (F − 32) × 5/9 + 273.15`

**Inputs**:
- `fahrenheit` (double): Temperature in Fahrenheit. Must be ≥ −459.67.

**Outputs**:
- (double): Temperature in Kelvin.

**Edge Cases**:
- `fahrenheitToKelvin(-459.67)` returns `0.0` (absolute zero).
- `fahrenheitToKelvin(32.0)` returns `273.15` (freezing point of water).
- `fahrenheitToKelvin(212.0)` returns `373.15` (boiling point of water).

**Exceptions**:
- `IllegalArgumentException` if `fahrenheit < -459.67`.

---

## Feature 4: Reject below absolute zero (Celsius)

**Method**: Extends existing `celsiusToFahrenheit(double celsius)`

**Description**: The existing method must throw an exception when the input is below absolute zero in Celsius.

**Absolute Zero Threshold**: `celsius < -273.15`

**Inputs**:
- `celsius` (double): Temperature in Celsius.

**Outputs**:
- (double): Temperature in Fahrenheit, if valid.

**Edge Cases**:
- `celsiusToFahrenheit(-273.15)` must NOT throw (exactly at absolute zero is valid).
- `celsiusToFahrenheit(-273.16)` must throw `IllegalArgumentException`.
- `celsiusToFahrenheit(-300.0)` must throw `IllegalArgumentException`.

**Exceptions**:
- `IllegalArgumentException` if `celsius < -273.15`.

---

## Feature 5: Reject below absolute zero (Fahrenheit)

**Method**: Extends existing `fahrenheitToCelsius(double fahrenheit)`

**Description**: The existing method must throw an exception when the input is below absolute zero in Fahrenheit.

**Absolute Zero Threshold**: `fahrenheit < -459.67`

**Inputs**:
- `fahrenheit` (double): Temperature in Fahrenheit.

**Outputs**:
- (double): Temperature in Celsius, if valid.

**Edge Cases**:
- `fahrenheitToCelsius(-459.67)` must NOT throw (exactly at absolute zero is valid).
- `fahrenheitToCelsius(-459.68)` must throw `IllegalArgumentException`.
- `fahrenheitToCelsius(-500.0)` must throw `IllegalArgumentException`.

**Exceptions**:
- `IllegalArgumentException` if `fahrenheit < -459.67`.

---

## Feature 6: Reject below absolute zero (Kelvin)

**Method**: Extends existing `celsiusToKelvin(double celsius)` and new methods `kelvinToCelsius`, `kelvinToFahrenheit`

**Description**: All methods accepting Kelvin as input must throw an exception when the Kelvin value is below 0.

**Absolute Zero Threshold**: `kelvin < 0`

**Affected Methods**:
- `kelvinToCelsius(double kelvin)` — throw if `kelvin < 0`.
- `kelvinToFahrenheit(double kelvin)` — throw if `kelvin < 0`.
- `celsiusToKelvin(double celsius)` — throw if the resulting Kelvin would be < 0 (i.e., `celsius < -273.15`).

**Edge Cases**:
- `kelvinToCelsius(0.0)` must NOT throw.
- `kelvinToCelsius(-0.0001)` must throw `IllegalArgumentException`.
- `celsiusToKelvin(-273.15)` must NOT throw (result is exactly 0K).
- `celsiusToKelvin(-273.16)` must throw `IllegalArgumentException`.

**Exceptions**:
- `IllegalArgumentException` if the Kelvin value (input or computed) is `< 0`.

---

## Feature 7: Round to N decimals

**Method**: `public static double roundResult(double value, int decimals)`

**Formula**: Uses `Math.round(value * 10^decimals) / 10^decimals`

**Inputs**:
- `value` (double): The number to round.
- `decimals` (int): Number of decimal places. Must be ≥ 0.

**Outputs**:
- (double): The rounded value.

**Edge Cases**:
- `roundResult(3.14159, 2)` returns `3.14`.
- `roundResult(3.14159, 0)` returns `3.0`.
- `roundResult(2.555, 2)` returns `2.56` (rounds half up).
- `roundResult(0.0, 5)` returns `0.0`.

**Exceptions**:
- `IllegalArgumentException` if `decimals < 0`.
- `IllegalArgumentException` if `decimals > 15` (precision limit of double).

---

## Feature 8: Parse string like "25C"

**Method**: `public static Temperature parseTemperature(String input)`

**Description**: Parses a string containing a numeric value followed by a unit letter (C, F, or K) and returns a `Temperature` record.

**Temperature Record**: `record Temperature(double value, String unit)`

**Inputs**:
- `input` (String): Format is `<number><unit>`, e.g., `"25C"`, `"-40F"`, `"300K"`.
  - Unit letter is case-insensitive: "c", "C", "f", "F", "k", "K" are all valid.
  - Number can be integer or decimal, positive or negative.

**Outputs**:
- `Temperature`: A record with `value` (parsed double) and `unit` (uppercase string "C", "F", or "K").

**Edge Cases**:
- `parseTemperature("0C")` returns `Temperature(0.0, "C")`.
- `parseTemperature("-273.15k")` returns `Temperature(-273.15, "K")`.
- `parseTemperature("100.5F")` returns `Temperature(100.5, "F")`.

**Exceptions**:
- `IllegalArgumentException` if `input` is null or empty.
- `IllegalArgumentException` if `input` has no recognized unit letter.
- `IllegalArgumentException` if the numeric part is not a valid number.

---

## Feature 9: Generic convert with unit handling

**Method**: `public static double convert(double value, String fromUnit, String toUnit)`

**Description**: Converts a temperature value from one unit to another using the unit string.

**Supported Units**: `"C"`, `"F"`, `"K"` (case-insensitive input, but internally treated as uppercase).

**Inputs**:
- `value` (double): Temperature value.
- `fromUnit` (String): Source unit ("C", "F", or "K").
- `toUnit` (String): Target unit ("C", "F", or "K").

**Outputs**:
- (double): Converted temperature value.

**Edge Cases**:
- `convert(100.0, "C", "F")` returns `212.0`.
- `convert(0.0, "C", "K")` returns `273.15`.
- `convert(212.0, "F", "K")` returns `373.15`.
- `convert(100.0, "C", "C")` returns `100.0` (same unit returns same value).
- Unit strings are case-insensitive: `convert(100.0, "c", "f")` works the same as `"C"`, `"F"`.

**Exceptions**:
- `IllegalArgumentException` if `fromUnit` is null, empty, or not "C"/"F"/"K".
- `IllegalArgumentException` if `toUnit` is null, empty, or not "C"/"F"/"K".
- `IllegalArgumentException` if the value is below absolute zero for the given `fromUnit`.

---

## Feature 10: Convert list of values

**Method**: `public static double[] convertAll(double[] values, String from, String to)`

**Description**: Converts an array of temperature values from one unit to another.

**Inputs**:
- `values` (double[]): Array of temperature values.
- `from` (String): Source unit ("C", "F", or "K").
- `to` (String): Target unit ("C", "F", or "K").

**Outputs**:
- `double[]`: Array of converted values, same length as input.

**Edge Cases**:
- `convertAll(new double[]{0, 100}, "C", "F")` returns `new double[]{32.0, 212.0}`.
- `convertAll(new double[]{}, "C", "F")` returns `new double[]{}` (empty array).
- Single element array works correctly.

**Exceptions**:
- `IllegalArgumentException` if `values` is null.
- `IllegalArgumentException` if `from` or `to` is invalid (delegates to `convert` validation).
- `IllegalArgumentException` if any value in the array is below absolute zero for the given `from` unit.

---

## Feature 11: Convert with rounding

**Method**: `public static double convertAndRound(double value, String from, String to, int decimals)`

**Description**: Converts a temperature value and rounds the result to the specified number of decimal places.

**Inputs**:
- `value` (double): Temperature value.
- `from` (String): Source unit ("C", "F", or "K").
- `to` (String): Target unit ("C", "F", or "K").
- `decimals` (int): Number of decimal places for rounding.

**Outputs**:
- (double): Converted and rounded temperature value.

**Edge Cases**:
- `convertAndRound(1.0, "F", "C", 2)` returns `-17.22` (1°F ≈ -17.222...°C, rounded to 2 decimals).
- `convertAndRound(100.0, "C", "F", 0)` returns `212.0`.
- `convertAndRound(0.0, "C", "K", 3)` returns `273.150`.

**Exceptions**:
- `IllegalArgumentException` if `from` or `to` is invalid.
- `IllegalArgumentException` if `decimals < 0` or `decimals > 15`.
- `IllegalArgumentException` if the value is below absolute zero for the given `from` unit.

---

## Feature 12: Absolute zero check

**Method**: `public static boolean isBelowAbsoluteZero(double value, String unit)`

**Description**: Returns true if the given value is below absolute zero for the specified unit.

**Thresholds**:
- Celsius: below `-273.15`
- Fahrenheit: below `-459.67`
- Kelvin: below `0.0`

**Inputs**:
- `value` (double): Temperature value.
- `unit` (String): Unit ("C", "F", or "K", case-insensitive).

**Outputs**:
- (boolean): `true` if below absolute zero, `false` otherwise.

**Edge Cases**:
- `isBelowAbsoluteZero(-273.15, "C")` returns `false` (exactly at absolute zero).
- `isBelowAbsoluteZero(-273.16, "C")` returns `true`.
- `isBelowAbsoluteZero(0.0, "K")` returns `false`.
- `isBelowAbsoluteZero(-0.001, "K")` returns `true`.
- `isBelowAbsoluteZero(-459.67, "F")` returns `false`.
- `isBelowAbsoluteZero(-459.68, "F")` returns `true`.

**Exceptions**:
- `IllegalArgumentException` if `unit` is null, empty, or not "C"/"F"/"K".

---
