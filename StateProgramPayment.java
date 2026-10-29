public class StateProgramPayment implements PaymentStrategy {
    @Override
    public double calculateCost(Patient patient, double basePrice) {
        return 0.0;
    }

    @Override
    public String getName() {
        return "Програма НСЗУ";
    }

    @Override
    public String formatReceipt(Patient patient, double basePrice) {
        return PaymentStrategy.super.formatReceipt(patient, basePrice)
                + String.format("  (вартість %.2f грн покриває НСЗУ)", basePrice);
    }
}
