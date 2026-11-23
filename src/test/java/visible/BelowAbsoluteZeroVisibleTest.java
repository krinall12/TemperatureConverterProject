import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Feature 12: Absolute zero check.
 */
class BelowAbsoluteZeroVisibleTest {

    @Test
    void isBelowAbsoluteZero_celsiusAtZero_returnsFalse() {
        assertEquals(false, TemperatureConverter.isBelowAbsoluteZero(-273.15, "C"));
    }

    @Test
    void isBelowAbsoluteZero_celsiusBelowZero_returnsTrue() {
        assertEquals(true, TemperatureConverter.isBelowAbsoluteZero(-273.16, "C"));
    }

    @Test
    void isBelowAbsoluteZero_kelvinAtZero_returnsFalse() {
        assertEquals(false, TemperatureConverter.isBelowAbsoluteZero(0.0, "K"));
    }

    @Test
    void isBelowAbsoluteZero_invalidUnit_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.isBelowAbsoluteZero(0.0, "X"));
    }
}
