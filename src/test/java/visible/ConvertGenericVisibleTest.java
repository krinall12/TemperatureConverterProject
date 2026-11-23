import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Feature 9: Generic convert with unit handling.
 */
class ConvertGenericVisibleTest {

    private static final double DELTA = 0.0001;

    @Test
    void convert_celsiusToFahrenheit() {
        assertEquals(212.0, TemperatureConverter.convert(100.0, "C", "F"), DELTA);
    }

    @Test
    void convert_celsiusToKelvin() {
        assertEquals(273.15, TemperatureConverter.convert(0.0, "C", "K"), DELTA);
    }

    @Test
    void convert_sameUnit_returnsSameValue() {
        assertEquals(100.0, TemperatureConverter.convert(100.0, "C", "C"), DELTA);
    }

    @Test
    void convert_invalidFromUnit_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.convert(100.0, "X", "F"));
    }
}
