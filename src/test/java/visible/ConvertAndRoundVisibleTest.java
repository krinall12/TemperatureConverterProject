import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Feature 11: Convert with rounding.
 */
class ConvertAndRoundVisibleTest {

    private static final double DELTA = 0.0001;

    @Test
    void convertAndRound_fahrenheitToCelsius_twoDecimals() {
        assertEquals(-17.22, TemperatureConverter.convertAndRound(1.0, "F", "C", 2), DELTA);
    }

    @Test
    void convertAndRound_celsiusToFahrenheit_zeroDecimals() {
        assertEquals(212.0, TemperatureConverter.convertAndRound(100.0, "C", "F", 0), DELTA);
    }

    @Test
    void convertAndRound_celsiusToKelvin_threeDecimals() {
        assertEquals(273.150, TemperatureConverter.convertAndRound(0.0, "C", "K", 3), DELTA);
    }

    @Test
    void convertAndRound_negativeDecimals_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.convertAndRound(100.0, "C", "F", -1));
    }
}
