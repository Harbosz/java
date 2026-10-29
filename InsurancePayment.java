import java.time.LocalDate;

public class InsurancePayment implements PaymentStrategy, Expirable {
    private final String insurer;
    private final double coverage;
    private final LocalDate expiryDate;

    public InsurancePayment(String insurer, double coverage, LocalDate expiryDate) {
        this.insurer = insurer;
        this.coverage = coverage;
        this.expiryDate = expiryDate;
    }

    @Override
    public double calculateCost(Patient patient, double basePrice) {
        if (isExpired()) {
            return basePrice;
        }
        return basePrice * (1 - coverage);
    }

    @Override
    public String getName() {
        return "Страховка " + insurer + " (" + Math.round(coverage * 100) + "%)";
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
