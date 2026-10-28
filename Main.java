import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Scanner sc;

    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        sc = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("=== Електронний облік пацієнтів (ЛР №3) ===");

        try {
            int n = readCount();
            Patient[] patients = new Patient[n];

            for (int i = 0; i < patients.length; i++) {
                System.out.println("\n--- Пацієнт " + (i + 1) + " ---");
                try {
                    patients[i] = readPatient();
                } catch (InvalidBirthYearException e) {
                    System.out.println("Помилка: " + e.getMessage()
                            + ". Ви ввели: " + e.getInvalidYear() + ". Спробуйте ще раз.");
                    i--;
                } catch (InvalidWeightException e) {
                    System.out.println("Помилка: " + e.getMessage()
                            + ". Ви ввели: " + e.getInvalidWeight() + " кг. Спробуйте ще раз.");
                    i--;
                } catch (PatientException e) {  
                    System.out.println("Помилка в полі «" + e.getFieldName() + "»: "
                            + e.getMessage() + ". Спробуйте ще раз.");
                    i--;
                } catch (InputMismatchException e) {
                    System.out.println("Помилка: рік народження має бути цілим числом. Спробуйте ще раз.");
                    sc.nextLine();  
                    i--;
                } catch (NumberFormatException e) {
                    System.out.println("Помилка: вагу треба ввести числом (наприклад, 72,5). Спробуйте ще раз.");
                    i--;
                }
            }

            System.out.println("\nСписок пацієнтів:");
            for (Patient p : patients) {
                System.out.println("  " + p);
            }

            printAverageAgeOlderThan(patients);

            showPatientByNumber(patients);

            demoExceptionHierarchy();

        } finally {
            sc.close();
            System.out.println("\nРоботу з обліком пацієнтів завершено. Сканер закрито.");
        }
    }

    static int readCount() {
        while (true) {
            System.out.print("Кількість пацієнтів: ");
            try {
                int n = sc.nextInt();
                sc.nextLine();
                if (n > 0) return n;
                System.out.println("Кількість має бути більшою за нуль.");
            } catch (InputMismatchException e) {
                System.out.println("Помилка: потрібно ввести ціле число, а не текст.");
                sc.nextLine();
            }
        }
    }

    static Patient readPatient() throws PatientException {
        System.out.print("Номер медичної картки: ");
        String card = sc.nextLine().trim();
        System.out.print("ПІБ: ");
        String name = sc.nextLine().trim();
        System.out.print("Рік народження: ");
        int year = sc.nextInt();       
        sc.nextLine();
        System.out.print("Вага (кг): ");
        double weight = Double.parseDouble(sc.nextLine().trim().replace(',', '.')); 
        System.out.print("Діагноз: ");
        String diagnosis = sc.nextLine().trim();

        return registerPatient(card, name, year, weight, diagnosis);
    }

 
    static Patient registerPatient(String card, String name, int year, double weight, String diagnosis)
            throws PatientException {
        try {
            return new Patient(card, name, year, weight, diagnosis);
        } catch (PatientException e) {
            System.out.println("[ЖУРНАЛ] Реєстрацію відхилено: "
                    + e.getClass().getSimpleName() + " (поле «" + e.getFieldName() + "»)");
            throw e;
        }
    }

    static void printAverageAgeOlderThan(Patient[] patients) {
        int currentYear = Year.now().getValue();
        int limit = readIntSafe("\nСередній вік пацієнтів, старших за (років): ");

        int sum = 0;
        int count = 0;
        for (Patient p : patients) {
            int age = p.getAge(currentYear);
            if (age > limit) {
                sum += age;
                count++;
            }
        }

        try {
            int average = sum / count;  
            System.out.println("Таких пацієнтів: " + count + ", середній вік: " + average + " р.");
        } catch (ArithmeticException e) {
            System.out.println("Неможливо обчислити середній вік: немає пацієнтів, старших за "
                    + limit + " р. (ділення на нуль).");
        } finally {
            System.out.println("Аналіз за віком завершено.");
        }
    }

    static void showPatientByNumber(Patient[] patients) {
        int number = readIntSafe("\nНомер пацієнта у списку для перегляду (1–" + patients.length + "): ");
        try {
            System.out.println("Картка пацієнта: " + patients[number - 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Пацієнта з номером " + number + " немає. У списку лише "
                    + patients.length + " пацієнт(ів).");
        }
    }

    static void demoExceptionHierarchy() {
        System.out.println("\n--- Перевірка тестових записів (ієрархія винятків) ---");

        Object[][] testData = {
                {"К-900", "Тест Ігор",   1850, 70.0, "ГРВІ"},      
                {"К-901", "Тест Марія",  1990, -5.0, "Бронхіт"},  
                {"",      "Тест Без Картки", 1995, 60.0, "Огляд"},
                {"К-903", "Тест Норма",  2000, 68.0, "Огляд"}   
        };

        for (Object[] row : testData) {
            try {
                Patient p = new Patient((String) row[0], (String) row[1],
                        (int) row[2], (double) row[3], (String) row[4]);
                System.out.println("OK:      " + p);
            } catch (PatientException e) {
                System.out.println("Відхилено [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
            }
        }
    }

    static int readIntSafe(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Помилка: потрібно ввести ціле число.");
                sc.nextLine();
            }
        }
    }
}
