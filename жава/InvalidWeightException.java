/**
 * Вага пацієнта фізично неможлива (від'ємна, нульова або завелика).
 */
public class InvalidWeightException extends PatientException {
    private final double invalidWeight;   // значення, яке спричинило виняток

    public InvalidWeightException(String message, double invalidWeight) {
        super(message, "weight");
        this.invalidWeight = invalidWeight;
    }

    public double getInvalidWeight() {
        return invalidWeight;
    }
}
