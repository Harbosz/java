import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.out.println("=== Електронний облік пацієнтів (ЛР №5): паттерни Factory, Observer, Adapter ===");

        Patient ivan, olena, maksym;
        try {
            ivan   = new Patient("К-102", "Петренко Іван", 1985, 82.5, "Гіпертонія");
            olena  = new Patient("К-055", "Коваль Олена", 1958, 64.0, "Діабет 2 типу");
            maksym = new Patient("К-311", "Бондар Максим", 2015, 32.0, "ГРВІ");
        } catch (PatientException e) {
            System.out.println("Помилка створення пацієнта: " + e.getMessage());
            return;
        }
        double price = 600.0;

        System.out.println("\n--- Регресійна перевірка: ті самі суми, що в ЛР №4 ---");
        regressionCheck(olena, price);

        System.out.println("\n--- Рівень 1. Factory: реєстратор вибирає код оплати ---");
        String[] codes = {"FULL", "NSZU", "pensioner", "CHILD", "VIP"};
        for (String code : codes) {
            try {
                PaymentStrategy s = PaymentStrategyFactory.create(code);
                System.out.println(s.formatReceipt(olena, price));
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        System.out.println("\n--- Рівень 1. Factory: автоматичний вибір пільги за віком ---");
        for (Patient p : new Patient[]{ivan, olena, maksym}) {
            System.out.println(PaymentStrategyFactory.recommendFor(p).formatReceipt(p, price));
        }

        System.out.println("\n--- Рівень 2. Observer: зміна статусу прийому сповіщає 3 підписників ---");
        AuditLog log = new AuditLog();
        PatientNotifier sms = new PatientNotifier();
        BillingDepartment billing = new BillingDepartment();

        Appointment visit1 = new Appointment(olena, "ендокринолог Мельник Т.В.", price,
                PaymentStrategyFactory.recommendFor(olena));
        visit1.subscribe(log);
        visit1.subscribe(sms);
        visit1.subscribe(billing);

        System.out.println("> Олена зайшла до кабінету:");
        visit1.setStatus(AppointmentStatus.IN_PROGRESS);
        System.out.println("> Прийом завершено:");
        visit1.setStatus(AppointmentStatus.COMPLETED);

        Appointment visit2 = new Appointment(maksym, "педіатр Ткаченко Л.М.", price,
                PaymentStrategyFactory.recommendFor(maksym));
        visit2.subscribe(log);
        visit2.subscribe(sms);
        visit2.subscribe(billing);

        System.out.println("> Батьки Максима відмовились від SMS, потім прийом скасували:");
        visit2.unsubscribe(sms);
        visit2.setStatus(AppointmentStatus.CANCELLED);

        System.out.printf("Записів у журналі: %d, виручка: %.2f грн%n", log.size(), billing.getTotalIncome());

        System.out.println("\n--- Рівень 3. Adapter: зовнішнє API страхової «Альфа» як PaymentStrategy ---");
        AlfaInsuranceApi alfaApi = new AlfaInsuranceApi();
        PaymentStrategy[] alfaPolicies = {
                PaymentStrategyFactory.alfaInsurance(alfaApi, "AL-1001"),
                PaymentStrategyFactory.alfaInsurance(alfaApi, "AL-2002")
        };
        for (PaymentStrategy s : alfaPolicies) {
            System.out.println(s.formatReceipt(ivan, 900.0));
            if (s instanceof Expirable doc) {
                System.out.println("    поліс до " + doc.getExpiryDate()
                        + (doc.isExpired() ? " — ПРОСТРОЧЕНИЙ" : " — дійсний"));
            }
        }

        System.out.println("> Адаптер працює в Appointment так само, як «рідні» стратегії:");
        Appointment visit3 = new Appointment(ivan, "кардіолог Шевчук О.П.", 900.0, alfaPolicies[0]);
        visit3.subscribe(log);
        visit3.subscribe(billing);
        visit3.setStatus(AppointmentStatus.COMPLETED);
        System.out.printf("Записів у журналі: %d, виручка: %.2f грн%n", log.size(), billing.getTotalIncome());
    }

    private static void regressionCheck(Patient patient, double price) {
        String[] codes = {"FULL", "NSZU", "PENSIONER", "CHILD"};
        double[] expected = {600.0, 0.0, 300.0, 600.0};
        boolean allOk = true;
        for (int i = 0; i < codes.length; i++) {
            double actual = PaymentStrategyFactory.create(codes[i]).calculateCost(patient, price);
            boolean ok = Math.abs(actual - expected[i]) < 0.001;
            allOk &= ok;
            System.out.printf("%-10s очікувано %7.2f, отримано %7.2f — %s%n",
                    codes[i], expected[i], actual, ok ? "OK" : "ПОМИЛКА");
        }
        double insured = PaymentStrategyFactory.insurance("Альфа", 0.8, LocalDate.now().plusYears(1))
                .calculateCost(patient, price);
        boolean ok = Math.abs(insured - 120.0) < 0.001;
        allOk &= ok;
        System.out.printf("%-10s очікувано %7.2f, отримано %7.2f — %s%n", "INSURANCE", 120.0, insured, ok ? "OK" : "ПОМИЛКА");
        System.out.println(allOk ? "Поведінка ЛР №4 збережена." : "УВАГА: поведінка змінилась!");
    }
}
