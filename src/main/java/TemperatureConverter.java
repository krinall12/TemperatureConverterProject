/**
 * Converts temperatures between Celsius, Fahrenheit and Kelvin.
 * Each method is independent and does not call any other method.
 */
public class TemperatureConverter {

    // Absolute zero thresholds
    private static final double ABS_ZERO_C = -273.15;
    private static final double ABS_ZERO_F = -459.67;
    private static final double ABS_ZERO_K = 0.0;

    // F = (C x 9/5) + 32
    public static double celsiusToFahrenheit(double celsius) {
        if (celsius < ABS_ZERO_C) {
            throw new IllegalArgumentException("Celsius value below absolute zero: " + celsius);
        }
        return (celsius * 9.0 / 5.0) + 32;
    }

    // C = (F - 32) x 5/9
    public static double fahrenheitToCelsius(double fahrenheit) {
        if (fahrenheit < ABS_ZERO_F) {
            throw new IllegalArgumentException("Fahrenheit value below absolute zero: " + fahrenheit);
        }
        return (fahrenheit - 32) * (5.0 / 9.0);
    }

    // K = C + 273.15
    public static double celsiusToKelvin(double celsius) {
        if (celsius < ABS_ZERO_C) {
            throw new IllegalArgumentException("Celsius value below absolute zero: " + celsius);
        }
        return celsius + 273.15;
    }

    // Feature 1: Kelvin to Celsius
    public static double kelvinToCelsius(double kelvin) {
        if (kelvin < ABS_ZERO_K) {
            throw new IllegalArgumentException("Kelvin value below absolute zero: " + kelvin);
        }
        return kelvin - 273.15;
    }

    // Feature 2: Kelvin to Fahrenheit
    public static double kelvinToFahrenheit(double kelvin) {
        if (kelvin < ABS_ZERO_K) {
            throw new IllegalArgumentException("Kelvin value below absolute zero: " + kelvin);
        }
        return (kelvin - 273.15) * 9.0 / 5.0 + 32;
    }

    // Feature 3: Fahrenheit to Kelvin
    public static double fahrenheitToKelvin(double fahrenheit) {
        if (fahrenheit < ABS_ZERO_F) {
            throw new IllegalArgumentException("Fahrenheit value below absolute zero: " + fahrenheit);
        }
        return (fahrenheit - 32) * 5.0 / 9.0 + 273.15;
    }

    // Feature 7: Round to N decimals
    public static double roundResult(double value, int decimals) {
        if (decimals < 0 || decimals > 15) {
            throw new IllegalArgumentException("Decimals must be between 0 and 15: " + decimals);
        }
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

    // Feature 8: Parse string like "25C"
    public static Temperature parseTemperature(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input must not be null or empty");
        }

        // Find the last character as the unit
        char lastChar = input.charAt(input.length() - 1);
        String unitUpper = String.valueOf(lastChar).toUpperCase();

        if (!unitUpper.equals("C") && !unitUpper.equals("F") && !unitUpper.equals("K")) {
            throw new IllegalArgumentException("Invalid unit: " + lastChar);
        }

        String numPart = input.substring(0, input.length() - 1).trim();
        if (numPart.isEmpty()) {
            throw new IllegalArgumentException("No numeric value in input: " + input);
        }

        double value;
        try {
            value = Double.parseDouble(numPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + numPart);
        }

        return new Temperature(value, unitUpper);
    }

    // Feature 9: Generic convert with unit handling
    public static double convert(double value, String fromUnit, String toUnit) {
        String from = normalizeUnit(fromUnit, "fromUnit");
        String to = normalizeUnit(toUnit, "toUnit");

        // Check absolute zero
        if (isBelowAbsoluteZero(value, from)) {
            throw new IllegalArgumentException("Value below absolute zero for unit " + from + ": " + value);
        }

        // Same unit — no conversion needed
        if (from.equals(to)) {
            return value;
        }

        // Convert to Celsius first, then to target
        double celsius;
        switch (from) {
            case "C": celsius = value; break;
            case "F": celsius = fahrenheitToCelsius(value); break;
            case "K": celsius = kelvinToCelsius(value); break;
            default: throw new IllegalArgumentException("Unknown unit: " + from);
        }

        switch (to) {
            case "C": return celsius;
            case "F": return celsiusToFahrenheit(celsius);
            case "K": return celsiusToKelvin(celsius);
            default: throw new IllegalArgumentException("Unknown unit: " + to);
        }
    }

    // Feature 10: Convert list of values
    public static double[] convertAll(double[] values, String from, String to) {
        if (values == null) {
            throw new IllegalArgumentException("Values array must not be null");
        }

        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = convert(values[i], from, to);
        }
        return result;
    }

    // Feature 11: Convert with rounding
    public static double convertAndRound(double value, String from, String to, int decimals) {
        double converted = convert(value, from, to);
        return roundResult(converted, decimals);
    }

    // Feature 12: Absolute zero check
    public static boolean isBelowAbsoluteZero(double value, String unit) {
        String u = normalizeUnit(unit, "unit");
        switch (u) {
            case "C": return value < ABS_ZERO_C;
            case "F": return value < ABS_ZERO_F;
            case "K": return value < ABS_ZERO_K;
            default: throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }

    // Helper: normalize unit string to uppercase and validate
    private static String normalizeUnit(String unit, String paramName) {
        if (unit == null || unit.isEmpty()) {
            throw new IllegalArgumentException(paramName + " must not be null or empty");
        }
        String upper = unit.toUpperCase();
        if (!upper.equals("C") && !upper.equals("F") && !upper.equals("K")) {
            throw new IllegalArgumentException("Invalid unit: " + unit);
        }
        return upper;
    }
}