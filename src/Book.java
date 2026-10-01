import java.util.Objects;

public class Book {

    private String title;
    private String author;
    private int year;
    private double price;

    public Book(String title, String author, int year, double price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%-24s | %-18s | %4d | %8.2f грн", title, author, year, price);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Book other = (Book) object;
        return year == other.year
                && Double.compare(price, other.price) == 0
                && title.equals(other.title)
                && author.equals(other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, price);
    }
}