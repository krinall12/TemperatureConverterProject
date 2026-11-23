/**
 * Converts temperatures between Celsius, Fahrenheit and Kelvin.
 * Each method is independent and does not call any other method.
 */
public class TemperatureConverter {

    // F = (C x 9/5) + 32
    public static double celsiusToFahrenheit(double celsius) {
        if (celsius < -273.15) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        return (celsius * 9.0 / 5.0) + 32;
    }

    // C = (F - 32) x 5/9
    public static double fahrenheitToCelsius(double fahrenheit) {
        if (fahrenheit < -459.67) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        return (fahrenheit - 32) * (5.0 / 9.0);
    }

    // K = C + 273.15
    public static double celsiusToKelvin(double celsius) {
        if (celsius < -273.15) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        return celsius + 273.15;
    }

    // Feature 1: Kelvin to Celsius
    public static double kelvinToCelsius(double kelvin) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        return kelvin - 273.15;
    }

    // Feature 2: Kelvin to Fahrenheit
    public static double kelvinToFahrenheit(double kelvin) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        return (kelvin - 273.15) * 9.0 / 5.0 + 32;
    }

    // Feature 3: Fahrenheit to Kelvin
    public static double fahrenheitToKelvin(double fahrenheit) {
        if (fahrenheit < -459.67) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        return (fahrenheit - 32) * 5.0 / 9.0 + 273.15;
    }

    // Feature 7: Round to N decimals
    public static double roundResult(double value, int decimals) {
        if (decimals < 0 || decimals > 15) {
            throw new IllegalArgumentException("Decimals must be between 0 and 15");
        }
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

    // Feature 8: Parse string like "25C"
    public static Temperature parseTemperature(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input must not be null or empty");
        }
        String unit = String.valueOf(input.charAt(input.length() - 1)).toUpperCase();
        if (!unit.equals("C") && !unit.equals("F") && !unit.equals("K")) {
            throw new IllegalArgumentException("Unknown unit: " + unit);
        }
        String numPart = input.substring(0, input.length() - 1).trim();
        if (numPart.isEmpty()) {
            throw new IllegalArgumentException("No numeric value found");
        }
        double value = Double.parseDouble(numPart);
        return new Temperature(value, unit);
    }

    // Feature 9: Generic convert with unit handling
    public static double convert(double value, String fromUnit, String toUnit) {
        if (fromUnit == null || fromUnit.isEmpty()) {
            throw new IllegalArgumentException("From unit must not be null or empty");
        }
        if (toUnit == null || toUnit.isEmpty()) {
            throw new IllegalArgumentException("To unit must not be null or empty");
        }
        String from = fromUnit.toUpperCase();
        String to = toUnit.toUpperCase();
        if (!from.equals("C") && !from.equals("F") && !from.equals("K")) {
            throw new IllegalArgumentException("Unknown from unit: " + fromUnit);
        }
        if (!to.equals("C") && !to.equals("F") && !to.equals("K")) {
            throw new IllegalArgumentException("Unknown to unit: " + toUnit);
        }
        // Check absolute zero
        if (isBelowAbsoluteZero(value, from)) {
            throw new IllegalArgumentException("Temperature below absolute zero");
        }
        // Same unit
        if (from.equals(to)) {
            return value;
        }
        // Convert to Celsius first
        double celsius;
        switch (from) {
            case "C": celsius = value; break;
            case "F": celsius = fahrenheitToCelsius(value); break;
            case "K": celsius = kelvinToCelsius(value); break;
            default: throw new IllegalArgumentException("Unknown from unit: " + fromUnit);
        }
        // Convert from Celsius to target
        switch (to) {
            case "C": return celsius;
            case "F": return celsiusToFahrenheit(celsius);
            case "K": return celsiusToKelvin(celsius);
            default: throw new IllegalArgumentException("Unknown to unit: " + toUnit);
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
        if (unit == null || unit.isEmpty()) {
            throw new IllegalArgumentException("Unit must not be null or empty");
        }
        String u = unit.toUpperCase();
        switch (u) {
            case "C": return value < -273.15;
            case "F": return value < -459.67;
            case "K": return value < 0.0;
            default: throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }
}