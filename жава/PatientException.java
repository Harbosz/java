/**
 * Базовий (checked) виняток для порушень правил обліку пацієнтів.
 * Від нього успадковуються конкретніші доменні винятки.
 */
public class PatientException extends Exception {
    private final String fieldName;   // поле пацієнта, у якому знайдено помилку

    public PatientException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
