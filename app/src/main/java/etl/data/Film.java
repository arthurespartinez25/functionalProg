package etl.data;

import java.lang.reflect.Constructor;
import java.time.Year;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;

import etl.util.CSV;
import etl.util.Data;

public record Film(
    String ID,
    String NAME,
    String RELEASE_YEAR
) {
    public interface Extracting {

        static etl.info.Film infoFromData(final Film dataFilm) {
            if (dataFilm == null) return null;

            return Data.<Year>parse(dataFilm.RELEASE_YEAR, Year::parse)
                .map(release ->
                    new etl.info.Film(
                        dataFilm.ID,
                        dataFilm.NAME,
                        release
                    )
                ).orElse(null);
        }

        static final Constructor<Film> ctor = Data.ctorOf(Film.class);

        static Film dataFromCSV(final CSVRecord csv) {
            return CSV.dataFromCSV(csv, ctor);
        }

        static Stream<Film> dataStreamFromReader(final java.io.Reader reader) {
            return CSV.dataStreamFromReader(reader, Film.class,
                Film.Extracting::dataFromCSV
            );
        }

        static Stream<etl.info.Film> infoStreamFromReader(final java.io.Reader reader) {

            // Go to CSV.java
            return CSV.infoStreamFromReader(reader, Film.class,
                Film.Extracting::dataFromCSV,
                Film.Extracting::infoFromData
            );
        }
    }
}
