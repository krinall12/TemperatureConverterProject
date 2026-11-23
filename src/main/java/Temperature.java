/**
 * Simple data carrier for a parsed temperature value and its unit.
 * Unit is always uppercase: "C", "F", or "K".
 */
public record Temperature(double value, String unit) {
}
