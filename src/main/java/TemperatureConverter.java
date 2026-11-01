/**
 * Converts temperatures between Celsius, Fahrenheit and Kelvin.
 * Each method is independent and does not call any other method.
 */
public class TemperatureConverter {

    // F = (C x 9/5) + 32
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    // C = (F - 32) x 5/9
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * (5.0 / 9.0);
    }

    // K = C + 273.15
    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }
}