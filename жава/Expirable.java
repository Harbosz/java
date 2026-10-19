import java.time.LocalDate;

/**
 * Документ із терміном дії (страховий поліс, направлення, рецепт).
 * Окрема поведінка, не пов'язана з оплатою.
 */
public interface Expirable {

    LocalDate getExpiryDate();

    default boolean isExpired() {
        return LocalDate.now().isAfter(getExpiryDate());
    }
}
