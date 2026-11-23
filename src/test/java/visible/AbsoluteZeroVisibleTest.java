import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Features 4-6: Reject values below absolute zero.
 */
class AbsoluteZeroVisibleTest {

    private static final double DELTA = 0.0001;

    // Feature 4: Reject below absolute zero (Celsius)
    @Test
    void celsiusToFahrenheit_atAbsoluteZero_doesNotThrow() {
        assertEquals(-459.67, TemperatureConverter.celsiusToFahrenheit(-273.15), DELTA);
    }

    @Test
    void celsiusToFahrenheit_belowAbsoluteZero_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.celsiusToFahrenheit(-273.16));
    }

    // Feature 5: Reject below absolute zero (Fahrenheit)
    @Test
    void fahrenheitToCelsius_atAbsoluteZero_doesNotThrow() {
        assertEquals(-273.15, TemperatureConverter.fahrenheitToCelsius(-459.67), DELTA);
    }

    @Test
    void fahrenheitToCelsius_belowAbsoluteZero_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.fahrenheitToCelsius(-459.68));
    }

    // Feature 6: Reject below absolute zero (Kelvin)
    @Test
    void celsiusToKelvin_atAbsoluteZero_doesNotThrow() {
        assertEquals(0.0, TemperatureConverter.celsiusToKelvin(-273.15), DELTA);
    }

    @Test
    void celsiusToKelvin_belowAbsoluteZero_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.celsiusToKelvin(-273.16));
    }
}
