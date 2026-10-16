/**
 * Рік народження пацієнта поза допустимим діапазоном.
 */
public class InvalidBirthYearException extends PatientException {
    private final int invalidYear;   // значення, яке спричинило виняток
    private final int minYear;
    private final int maxYear;

    public InvalidBirthYearException(String message, int invalidYear, int minYear, int maxYear) {
        super(message, "birthYear");
        this.invalidYear = invalidYear;
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public int getInvalidYear() { return invalidYear; }
    public int getMinYear()     { return minYear; }
    public int getMaxYear()     { return maxYear; }
}
