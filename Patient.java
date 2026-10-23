import java.util.Objects;

public class Patient {
    private String cardNumber;   
    private String fullName;   
    private int birthYear;      
    private double weight;       
    private String diagnosis;   

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
