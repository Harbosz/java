public final class BookAlgorithms {

    private BookAlgorithms() {
    }

    public static void selectionSortByPrice(Book[] books) {
        for (int i = 0; i < books.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < books.length; j++) {
                if (books[j].getPrice() < books[minIndex].getPrice()) {
                    minIndex = j;
                }
            }
            swap(books, i, minIndex);
        }
    }

    public static void bubbleSortByYear(Book[] books) {
        for (int pass = 0; pass < books.length - 1; pass++) {
            boolean swapped = false;
            for (int j = 0; j < books.length - 1 - pass; j++) {
                if (books[j].getYear() > books[j + 1].getYear()) {
                    swap(books, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static int linearSearch(Book[] books, Book pattern) {
        for (int i = 0; i < books.length; i++) {
            if (books[i].equals(pattern)) {
                return i;
            }
        }
        return -1;
    }

    public static int countCheaperThan(Book[] books, double limit) {
        int count = 0;
        for (Book book : books) {
            if (book.getPrice() < limit) {
                count++;
            }
        }
        return count;
    }

    public static int countPublishedBefore(Book[] books, int year) {
        int count = 0;
        for (Book book : books) {
            if (book.getYear() < year) {
                count++;
            }
        }
        return count;
    }

    private static void swap(Book[] books, int first, int second) {
        Book temporary = books[first];
        books[first] = books[second];
        books[second] = temporary;
    }
}