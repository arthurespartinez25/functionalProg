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
        	System.out.println("Film.java");
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
        	System.out.println("Film2.java");
            return CSV.dataFromCSV(csv, ctor);
        }

        static Stream<Film> dataStreamFromReader(final java.io.Reader reader) {
        	System.out.println("Film3.java");
            return CSV.dataStreamFromReader(reader, Film.class,
                Film.Extracting::dataFromCSV
            );
        }

        static Stream<etl.info.Film> infoStreamFromReader(final java.io.Reader reader) {
        	System.out.println("Film4.java");
            return CSV.infoStreamFromReader(reader, Film.class,
                Film.Extracting::dataFromCSV,
                Film.Extracting::infoFromData
            );
        }
    }
}
