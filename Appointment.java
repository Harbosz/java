import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private final Patient patient;
    private final String doctor;
    private final double basePrice;
    private PaymentStrategy paymentStrategy;

    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
    private final List<AppointmentObserver> observers = new ArrayList<>();

    public Appointment(Patient patient, String doctor, double basePrice, PaymentStrategy paymentStrategy) {
        this.patient = patient;
        this.doctor = doctor;
        this.basePrice = basePrice;
        this.paymentStrategy = paymentStrategy;
    }

    public void subscribe(AppointmentObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(AppointmentObserver observer) {
        observers.remove(observer);
    }

    public void setStatus(AppointmentStatus newStatus) {
        if (newStatus == status) {
            return;
        }
        AppointmentStatus oldStatus = status;
        status = newStatus;
        notifyObservers(oldStatus, newStatus);
    }

    private void notifyObservers(AppointmentStatus oldStatus, AppointmentStatus newStatus) {
        for (AppointmentObserver observer : new ArrayList<>(observers)) {
            try {
                observer.onStatusChanged(this, oldStatus, newStatus);
            } catch (RuntimeException e) {
                System.out.println("    [ПОМИЛКА] " + observer.getClass().getSimpleName()
                        + " не обробив подію: " + e.getMessage());
            }
        }
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public double getCost() {
        return paymentStrategy.calculateCost(patient, basePrice);
    }

    public void printReceipt() {
        System.out.println("  Прийом у: " + doctor);
        System.out.println("  " + paymentStrategy.formatReceipt(patient, basePrice));
    }

    public Patient getPatient()          { return patient; }
    public String getDoctor()            { return doctor; }
    public AppointmentStatus getStatus() { return status; }
    public String getPaymentName()       { return paymentStrategy.getName(); }
}
