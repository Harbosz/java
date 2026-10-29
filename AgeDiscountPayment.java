import java.time.Year;

public abstract class AgeDiscountPayment implements PaymentStrategy {
    private final String name;

    protected AgeDiscountPayment(String name) {
        this.name = name;
    }

    @Override
    public final double calculateCost(Patient patient, double basePrice) {
        int age = patient.getAge(Year.now().getValue());
        if (isEligible(age)) {
            return basePrice * (1 - discountRate());
        }
        return basePrice;
    }

    @Override
    public String getName() {
        return name + " (−" + Math.round(discountRate() * 100) + "%)";
    }

    protected abstract boolean isEligible(int age);

    protected abstract double discountRate();
}
