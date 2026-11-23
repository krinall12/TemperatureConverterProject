import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Visible tests for Feature 10: Convert list of values.
 */
class ConvertListVisibleTest {

    private static final double DELTA = 0.0001;

    @Test
    void convertAll_celsiusToFahrenheit() {
        double[] result = TemperatureConverter.convertAll(new double[]{0, 100}, "C", "F");
        assertArrayEquals(new double[]{32.0, 212.0}, result, DELTA);
    }

    @Test
    void convertAll_emptyArray() {
        double[] result = TemperatureConverter.convertAll(new double[]{}, "C", "F");
        assertArrayEquals(new double[]{}, result, DELTA);
    }

    @Test
    void convertAll_singleElement() {
        double[] result = TemperatureConverter.convertAll(new double[]{0.0}, "C", "K");
        assertArrayEquals(new double[]{273.15}, result, DELTA);
    }

    @Test
    void convertAll_nullArray_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.convertAll(null, "C", "F"));
    }
}
