import java.time.LocalDate;
import java.time.Year;

public final class PaymentStrategyFactory {
    private PaymentStrategyFactory() { }

    public static PaymentStrategy create(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Код оплати не задано");
        }
        switch (code.trim().toUpperCase()) {
            case "FULL":      return new FullPayment();
            case "NSZU":      return new StateProgramPayment();
            case "PENSIONER": return new PensionerDiscount();
            case "CHILD":     return new ChildDiscount();
            default:
                throw new IllegalArgumentException("Невідомий код оплати: " + code
                        + ". Допустимі: FULL, NSZU, PENSIONER, CHILD");
        }
    }

    public static PaymentStrategy insurance(String insurer, double coverage, LocalDate expiryDate) {
        return new InsurancePayment(insurer, coverage, expiryDate);
    }

    public static PaymentStrategy alfaInsurance(AlfaInsuranceApi api, String policyNumber) {
        return new AlfaInsuranceAdapter(api, policyNumber);
    }

    public static PaymentStrategy recommendFor(Patient patient) {
        int age = patient.getAge(Year.now().getValue());
        if (age >= 60) return create("PENSIONER");
        if (age < 18)  return create("CHILD");
        return create("FULL");
    }
}
