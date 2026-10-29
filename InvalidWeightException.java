public class InvalidWeightException extends PatientException {
    private final double invalidWeight;

    public InvalidWeightException(String message, double invalidWeight) {
        super(message, "weight");
        this.invalidWeight = invalidWeight;
    }

    public double getInvalidWeight() {
        return invalidWeight;
    }
}
