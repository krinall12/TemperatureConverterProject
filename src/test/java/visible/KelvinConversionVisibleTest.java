import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Features 1-3: Kelvin conversions.
 */
class KelvinConversionVisibleTest {

    private static final double DELTA = 0.0001;

    // Feature 1: kelvinToCelsius
    @Test
    void kelvinToCelsius_absoluteZero_returnsMinus273point15() {
        assertEquals(-273.15, TemperatureConverter.kelvinToCelsius(0.0), DELTA);
    }

    @Test
    void kelvinToCelsius_freezingPoint_returnsZero() {
        assertEquals(0.0, TemperatureConverter.kelvinToCelsius(273.15), DELTA);
    }

    @Test
    void kelvinToCelsius_negativeKelvin_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.kelvinToCelsius(-1.0));
    }

    // Feature 2: kelvinToFahrenheit
    @Test
    void kelvinToFahrenheit_absoluteZero_returnsMinus459point67() {
        assertEquals(-459.67, TemperatureConverter.kelvinToFahrenheit(0.0), DELTA);
    }

    @Test
    void kelvinToFahrenheit_freezingPoint_returns32() {
        assertEquals(32.0, TemperatureConverter.kelvinToFahrenheit(273.15), DELTA);
    }

    @Test
    void kelvinToFahrenheit_negativeKelvin_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.kelvinToFahrenheit(-1.0));
    }

    // Feature 3: fahrenheitToKelvin
    @Test
    void fahrenheitToKelvin_absoluteZero_returnsZero() {
        assertEquals(0.0, TemperatureConverter.fahrenheitToKelvin(-459.67), DELTA);
    }

    @Test
    void fahrenheitToKelvin_freezingPoint_returns273point15() {
        assertEquals(273.15, TemperatureConverter.fahrenheitToKelvin(32.0), DELTA);
    }

    @Test
    void fahrenheitToKelvin_belowAbsoluteZero_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.fahrenheitToKelvin(-460.0));
    }
}
