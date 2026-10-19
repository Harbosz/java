import java.time.LocalDate;

/**
 * Оплата через страховку. Реалізує ДВА інтерфейси:
 * PaymentStrategy (як рахувати вартість) і Expirable (поліс має термін дії).
 */
public class InsurancePayment implements PaymentStrategy, Expirable {

    private final String insurer;          // страхова компанія
    private final double coverage;         // частка, яку покриває страховка: 0.8 = 80%
    private final LocalDate expiryDate;    // поліс дійсний до цієї дати

    public InsurancePayment(String insurer, double coverage, LocalDate expiryDate) {
        this.insurer = insurer;
        this.coverage = coverage;
        this.expiryDate = expiryDate;
    }

    @Override
    public double calculateCost(Patient patient, double basePrice) {
        if (isExpired()) {
            return basePrice;   // поліс прострочений — страховка не платить
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
