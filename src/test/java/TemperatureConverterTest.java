import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureConverterTest {

    private static final double DELTA = 0.0001;

    @Test
    void celsiusToFahrenheit_zeroCelsius_returns32() {
        assertEquals(32.0, TemperatureConverter.celsiusToFahrenheit(0.0), DELTA);
    }
}