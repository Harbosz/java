import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.Scanner;

public class Main {

    private static Scanner sc;

    public static void main(String[] args) {
        // Коректне введення/виведення кирилиці в консолі
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        sc = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("=== Електронний облік пацієнтів ===");

        // ---------- Рівень 1 ----------
        int n = readInt("Кількість пацієнтів: ", 1, 100);
        Patient[] patients = new Patient[n];

        for (int i = 0; i < patients.length; i++) {
            System.out.println("\n--- Пацієнт " + (i + 1) + " ---");
            patients[i] = readPatient();
        }

        System.out.println("\nСписок пацієнтів:");
        printAll(patients);

        int currentYear = Year.now().getValue();
        int ageLimit = readInt("\nПорахувати пацієнтів, старших за (років): ", 0, 150);
        int count = 0;
        for (Patient p : patients) {
            if (p.getAge(currentYear) > ageLimit) {
                count++;
            }
        }
        System.out.println("Пацієнтів, старших за " + ageLimit + " р.: " + count + " з " + patients.length);

        // ---------- Рівень 2 ----------
        System.out.println("\nДо сортування:");
        printAll(patients);

        selectionSortByBirthYear(patients);

        System.out.println("\nПісля сортування (від найстаршого до наймолодшого):");
        printAll(patients);

        // ---------- Рівень 3 ----------
        System.out.println("\n--- Пошук пацієнта (введіть дані зразка) ---");
        Patient sample = readPatient();
        int index = linearSearch(patients, sample);
        if (index >= 0) {
            System.out.println("Знайдено на позиції " + index + ": " + patients[index]);
        } else {
            System.out.println("Пацієнта не знайдено.");
        }
    }

    /** Сортування методом простого вибору за роком народження (за зростанням). */
    static void selectionSortByBirthYear(Patient[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].getBirthYear() < arr[minIdx].getBirthYear()) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Patient tmp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = tmp;
            }
        }
    }

    /** Лінійний пошук за цілим об'єктом (через equals). Повертає індекс або -1. */
    static int linearSearch(Patient[] arr, Patient sample) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(sample)) {
                return i;
            }
        }
        return -1;
    }

    static void printAll(Patient[] arr) {
        for (Patient p : arr) {
            System.out.println("  " + p);
        }
    }

    static Patient readPatient() {
        String card = readText("Номер медичної картки: ");
        String name = readText("ПІБ: ");
        int year = readInt("Рік народження: ", 1900, Year.now().getValue());
        double weight = readDouble("Вага (кг): ", 0.5, 400);
        String diagnosis = readText("Діагноз: ");
        return new Patient(card, name, year, weight, diagnosis);
    }

    static String readText(String prompt) {
        String s;
        do {
            System.out.print(prompt);
            s = sc.nextLine().trim();
        } while (s.isEmpty());
        return s;
    }

    static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) { }
            System.out.println("Введіть ціле число від " + min + " до " + max + ".");
        }
    }

    static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) { }
            System.out.println("Введіть число від " + min + " до " + max + ".");
        }
    }
}
