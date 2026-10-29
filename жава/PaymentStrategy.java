
public interface PaymentStrategy {

    double calculateCost(Patient patient, double basePrice);
    String getName();
    default String formatReceipt(Patient patient, double basePrice) {
        double cost = calculateCost(patient, basePrice);
        return String.format("%-28s | %-16s | ціна %7.2f грн | до сплати %7.2f грн",
                getName(), patient.getFullName(), basePrice, cost);
    }
}
