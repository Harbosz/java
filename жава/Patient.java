import java.time.Year;
import java.util.Objects;

/**
 * Пацієнт в електронному обліку медичного закладу.
 * Конструктор перевіряє доменні правила і кидає власні винятки.
 */
public class Patient {
    public static final int MIN_BIRTH_YEAR = 1900;
    public static final double MIN_WEIGHT = 0.5;    // кг, новонароджений
    public static final double MAX_WEIGHT = 400.0;  // кг

    private final String cardNumber;   // номер медичної картки
    private final String fullName;     // ПІБ
    private final int birthYear;       // рік народження
    private final double weight;       // вага, кг
    private final String diagnosis;    // поточний діагноз

    public Patient(String cardNumber, String fullName, int birthYear, double weight, String diagnosis)
            throws PatientException {

        if (cardNumber == null || cardNumber.isBlank()) {
            throw new PatientException("Номер медичної картки не може бути порожнім", "cardNumber");
        }

        int currentYear = Year.now().getValue();
        if (birthYear < MIN_BIRTH_YEAR || birthYear > currentYear) {
            throw new InvalidBirthYearException(
                    "Рік народження має бути від " + MIN_BIRTH_YEAR + " до " + currentYear,
                    birthYear, MIN_BIRTH_YEAR, currentYear);
        }

        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new InvalidWeightException(
                    "Вага має бути від " + MIN_WEIGHT + " до " + MAX_WEIGHT + " кг", weight);
        }

        this.cardNumber = cardNumber;
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.weight = weight;
        this.diagnosis = diagnosis;
    }

    public String getCardNumber() { return cardNumber; }
    public String getFullName()   { return fullName; }
    public int getBirthYear()     { return birthYear; }
    public double getWeight()     { return weight; }
    public String getDiagnosis()  { return diagnosis; }

    public int getAge(int currentYear) {
        return currentYear - birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient other = (Patient) o;
        return birthYear == other.birthYear
                && Objects.equals(cardNumber, other.cardNumber)
                && Objects.equals(fullName, other.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, fullName, birthYear);
    }

    @Override
    public String toString() {
        return String.format("Картка №%s | %s | %d р.н. | %.1f кг | діагноз: %s",
                cardNumber, fullName, birthYear, weight, diagnosis);
    }
}
