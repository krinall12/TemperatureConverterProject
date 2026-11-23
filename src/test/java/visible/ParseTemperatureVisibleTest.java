import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Visible tests for Feature 8: Parse string like "25C".
 */
class ParseTemperatureVisibleTest {

    @Test
    void parseTemperature_simpleCelsius() {
        Temperature t = TemperatureConverter.parseTemperature("25C");
        assertEquals(25.0, t.value(), 0.0001);
        assertEquals("C", t.unit());
    }

    @Test
    void parseTemperature_negativeFahrenheit() {
        Temperature t = TemperatureConverter.parseTemperature("-40F");
        assertEquals(-40.0, t.value(), 0.0001);
        assertEquals("F", t.unit());
    }

    @Test
    void parseTemperature_lowercaseUnit() {
        Temperature t = TemperatureConverter.parseTemperature("300k");
        assertEquals(300.0, t.value(), 0.0001);
        assertEquals("K", t.unit());
    }

    @Test
    void parseTemperature_nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureConverter.parseTemperature(null));
    }
}
