/**
 * Спосіб оплати прийому пацієнта.
 * Різні пацієнти платять по-різному: повністю, за програмою НСЗУ,
 * зі знижкою за віком або через страховку.
 */
public interface PaymentStrategy {

    /** Скільки пацієнт має заплатити за прийом з базовою ціною basePrice. */
    double calculateCost(Patient patient, double basePrice);

    /** Назва способу оплати для чеку. */
    String getName();

    /**
     * Default-метод: формує рядок чеку.
     * Усі реалізації отримують його готовим, але можуть перевизначити.
     */
    default String formatReceipt(Patient patient, double basePrice) {
        double cost = calculateCost(patient, basePrice);
        return String.format("%-28s | %-16s | ціна %7.2f грн | до сплати %7.2f грн",
                getName(), patient.getFullName(), basePrice, cost);
    }
}
