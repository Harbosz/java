/**
 * Прийом пацієнта — контекстний клас паттерна Strategy.
 * Сам не знає, як рахувати оплату, а делегує це стратегії,
 * яку можна замінити будь-коли через сетер.
 */
public class Appointment {

    private final Patient patient;
    private final String doctor;
    private final double basePrice;
    private PaymentStrategy paymentStrategy;

    public Appointment(Patient patient, String doctor, double basePrice, PaymentStrategy paymentStrategy) {
        this.patient = patient;
        this.doctor = doctor;
        this.basePrice = basePrice;
        this.paymentStrategy = paymentStrategy;
    }

    /** Зміна стратегії під час виконання програми, без створення нового прийому. */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /** Делегування: розрахунок виконує поточна стратегія. */
    public double getCost() {
        return paymentStrategy.calculateCost(patient, basePrice);
    }

    public void printReceipt() {
        System.out.println("  Прийом у: " + doctor);
        System.out.println("  " + paymentStrategy.formatReceipt(patient, basePrice));
    }
}
