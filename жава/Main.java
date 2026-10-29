import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        System.out.println("=== Електронний облік пацієнтів (ЛР №4): оплата прийому ===");

        Patient ivan, olena, maksym;
        try {
            ivan   = new Patient("К-102", "Петренко Іван", 1985, 82.5, "Гіпертонія");
            olena  = new Patient("К-055", "Коваль Олена", 1958, 64.0, "Діабет 2 типу");
            maksym = new Patient("К-311", "Бондар Максим", 2015, 32.0, "ГРВІ");
        } catch (PatientException e) {
            System.out.println("Помилка створення пацієнта: " + e.getMessage());
            return;
        }
        double price = 600.0;   // базова вартість прийому, грн

        // ---------- Рівень 1: масив типу інтерфейсу + поліморфний виклик ----------
        System.out.println("\n--- Рівень 1. Один пацієнт, різні способи оплати ---");
        PaymentStrategy[] strategies = {
                new FullPayment(),
                new StateProgramPayment(),
                new PensionerDiscount(),
                new ChildDiscount(),
                new InsurancePayment("Альфа", 0.8, LocalDate.now().plusYears(1)),
                new InsurancePayment("Бета", 0.8, LocalDate.now().minusMonths(3))
        };
        for (PaymentStrategy s : strategies) {
            System.out.println(s.formatReceipt(olena, price));   // для кожного об'єкта — своя реалізація
        }

        // ---------- Рівень 2: абстрактний клас — одна логіка, різні правила ----------
        System.out.println("\n--- Рівень 2. Пільги за віком для різних пацієнтів ---");
        Patient[] patients = {ivan, olena, maksym};
        AgeDiscountPayment[] discounts = {new PensionerDiscount(), new ChildDiscount()};
        for (AgeDiscountPayment d : discounts) {
            for (Patient p : patients) {
                System.out.println(d.formatReceipt(p, price));
            }
        }

        // ---------- Рівень 2: клас із двома інтерфейсами ----------
        System.out.println("\n--- Рівень 2. Кілька інтерфейсів: перевірка полісів ---");
        for (PaymentStrategy s : strategies) {
            if (s instanceof Expirable doc) {
                System.out.println(s.getName() + ": поліс до " + doc.getExpiryDate()
                        + (doc.isExpired() ? " — ПРОСТРОЧЕНИЙ" : " — дійсний"));
            }
        }

        // ---------- Рівень 3: Strategy — зміна поведінки під час виконання ----------
        System.out.println("\n--- Рівень 3. Strategy: один прийом, стратегію змінюємо на ходу ---");
        Appointment visit = new Appointment(ivan, "кардіолог Шевчук О.П.", 900.0,
                new InsurancePayment("Бета", 0.8, LocalDate.now().minusMonths(3)));
        System.out.println("1) Пацієнт показав поліс:");
        visit.printReceipt();

        System.out.println("2) Поліс прострочений — реєстратор пропонує направлення від сімейного лікаря:");
        visit.setPaymentStrategy(new StateProgramPayment());
        visit.printReceipt();

        System.out.println("3) Направлення немає — оплата повністю:");
        visit.setPaymentStrategy(new FullPayment());
        visit.printReceipt();
        System.out.printf("Підсумок до сплати: %.2f грн%n", visit.getCost());
    }
}
