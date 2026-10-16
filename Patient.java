import java.util.Objects;

/**
 * Пацієнт в електронному обліку медичного закладу.
 */
public class Patient {
    private String cardNumber;   // номер медичної картки
    private String fullName;     // ПІБ
    private int birthYear;       // рік народження
    private double weight;       // вага, кг
    private String diagnosis;    // поточний діагноз

    public Patient(String cardNumber, String fullName, int birthYear, double weight, String diagnosis) {
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

    /** Вік пацієнта відносно заданого року. */
    public int getAge(int currentYear) {
        return currentYear - birthYear;
    }

    /**
     * Пацієнти рівні, якщо збігаються номер картки, ПІБ і рік народження.
     * Вага та діагноз не враховуються — вони змінюються з часом,
     * а людина залишається тією самою.
     */
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
