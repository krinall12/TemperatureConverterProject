import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Feature 7: Round to N decimals.
 */
class RoundingVisibleTest {

    private static final double DELTA = 0.0001;

    @Test
    void roundResult_twoDecimals() {
        assertEquals(3.14, TemperatureConverter.roundResult(3.14159, 2), DELTA);
    }

    @Test
    void roundResult_zeroDecimals() {
        assertEquals(3.0, TemperatureConverter.roundResult(3.14159, 0), DELTA);
    }

    @Test
    void roundResult_negativeDecimals_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.roundResult(3.14, -1));
    }

    @Test
    void roundResult_zeroValue() {
        assertEquals(0.0, TemperatureConverter.roundResult(0.0, 5), DELTA);
    }
}
