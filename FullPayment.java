public class FullPayment implements PaymentStrategy {
    @Override
    public double calculateCost(Patient patient, double basePrice) {
        return basePrice;
    }

    @Override
    public String getName() {
        return "Повна оплата";
    }
}
