import java.util.Scanner;

public class Main {

    private static final double CHEAPPRICELIMIT = 500.0;
    private static final int OLDBOOKYEAR = 1950;
    private static final int MIN_YEAR = 1;
    private static final int MAX_YEAR = 2100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = readInt(scanner, "Кількість книг: ", 1, Integer.MAX_VALUE);
        Book[] books = new Book[size];

        for (int i = 0; i < books.length; i++) {
            System.out.println("Книга " + (i + 1) + " з " + size);
            String title = readLine(scanner, "  назва: ");
            String author = readLine(scanner, "  автор: ");
            int year = readInt(scanner, "  рік видання: ", MINYEAR, MAXYEAR);
            double price = readDouble(scanner, "  ціна: ");
            books[i] = new Book(title, author, year, price);
        }

        showArray("Введений масив", books);

        System.out.println();
        System.out.println("Усього елементів масиву: " + books.length);
        System.out.printf("Книг дешевших за %.2f грн: %d%n",
                CHEAPPRICELIMIT, BookAlgorithms.countCheaperThan(books, CHEAPPRICELIMIT));
        System.out.printf("Книг, виданих до %d року: %d%n",
                OLDBOOKYEAR, BookAlgorithms.countPublishedBefore(books, OLDBOOKYEAR));

        BookAlgorithms.selectionSortByPrice(books);
        showArray("Після сортування за ціною (selection sort)", books);

        BookAlgorithms.bubbleSortByYear(books);
        showArray("Після сортування за роком (bubble sort)", books);

        searchByPattern(scanner, books);

        scanner.close();
    }

    private static void searchByPattern(Scanner scanner, Book[] books) {
        System.out.println();
        System.out.println("Пошук об'єкта за повним збігом полів");
        String title = readLine(scanner, "  назва: ");
        String author = readLine(scanner, "  автор: ");
        int year = readInt(scanner, "  рік видання: ", MIN_YEAR,