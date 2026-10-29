import java.time.Year;

/**
 * Абстрактний клас для пільг за віком.
 * Частково реалізує інтерфейс PaymentStrategy: спільна логіка розрахунку
 * знижки написана тут один раз, а підкласи задають лише свої правила —
 * хто має право на пільгу і який її розмір.
 */
public abstract class AgeDiscountPayment implements PaymentStrategy {

    private final String name;

    protected AgeDiscountPayment(String name) {
        this.name = name;
    }

    /** Спільний для всіх пільг алгоритм — final, підкласи його не змінюють. */
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

    /** Чи має пацієнт такого віку право на пільгу. */
    protected abstract boolean isEligible(int age);

    /** Розмір знижки: 0.5 = 50%. */
    protected abstract double discountRate();
}
