package etl.info;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

public record Actor(
    String id,
    String name,
    LocalDate born,
    Year died
) {
    public Optional<Year> optionalDied() {
    	System.out.println("died");
        return Optional.ofNullable(died);
    }
}
