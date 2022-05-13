package etl.data;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;

import etl.util.CSV;
import etl.util.Data;

public record Actor(
    String ID,
    String NAME,
    String BORN_YEAR,
    String BORN_MONTH,
    String BORN_DAY,
    String DIED_YEAR
) {
    public Optional<String> optional_DIED_YEAR() {
        return Optional.ofNullable(DIED_YEAR);
    }

    public interface Extracting {

        static etl.info.Actor infoFromData(final Actor dataActor) {
        	System.out.println("Actor.java");
            if (dataActor == null) return null;

            try {
                final var born = LocalDate.of(
                    Data.<Integer>parse(dataActor.BORN_YEAR, Integer::parseInt).get(),
                    Data.<Integer>parse(dataActor.BORN_MONTH, Integer::parseInt).get(),
                    Data.<Integer>parse(dataActor.BORN_DAY, Integer::parseInt).get()
                );
                return dataActor.optional_DIED_YEAR()
                    .map(Year::parse)
                    .map(died ->
                        new etl.info.Actor(
                            dataActor.ID, dataActor.NAME, born, died
                        )
                    )
                    .orElse(
                        new etl.info.Actor(
                            dataActor.ID, dataActor.NAME, born, null
                        )
                    );
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        static final Constructor<Actor> ctor = Data.ctorOf(Actor.class);

        static Actor dataFromCSV(final CSVRecord csv) {
        	System.out.println("Actor.java2");
            return CSV.dataFromCSV(csv, ctor);
        }

        static Stream<Actor> dataStreamFromReader(final java.io.Reader reader) {
        	System.out.println("Actor.java3");
            return CSV.dataStreamFromReader(reader, Actor.class,
                Actor.Extracting::dataFromCSV
            );
        }

        static Stream<etl.info.Actor> infoStreamFromReader(final java.io.Reader reader) {
        	System.out.println("Actor.java4");
            return CSV.infoStreamFromReader(reader, Actor.class,
                Actor.Extracting::dataFromCSV,
                Actor.Extracting::infoFromData
            );
        }
    }
}