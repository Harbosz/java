public class PatientException extends Exception {
    private final String fieldName;

    public PatientException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
