package etl.data;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;

import etl.util.CSV;
import etl.util.Data;

public record Cast(
    String FILM_ID,
    String ACTOR_ID,
    String ROLE
) {
    public interface Extracting {

        static etl.info.Cast infoFromData(
            final Cast dataCast,
            final List<etl.info.Film> films,
            final List<etl.info.Actor> actors
        ) {
        	System.out.println("Cast.java");
            final var optionalFilm = films.stream()
                .filter(film -> film.id().equals(dataCast.FILM_ID))
                .findFirst();

            final var optionalActor = actors.stream()
                .filter(actor -> actor.id().equals(dataCast.ACTOR_ID))
                .findFirst();

            return optionalFilm.map(film ->
                optionalActor.map(actor ->
                    new etl.info.Cast(film, actor, dataCast.ROLE)
                ).orElse(null)
            ).orElse(null);
        }

        static final Constructor<Cast> ctor = Data.ctorOf(Cast.class);

        static Cast dataFromCSV(final CSVRecord csv) {
        	System.out.println("Cast.java2");
            return CSV.dataFromCSV(csv, ctor);
        }

        static Stream<etl.info.Cast> infoStreamFromReader(
            final java.io.Reader castReader,
            final List<etl.info.Film> films,
            final List<etl.info.Actor> actors
        ) {
        	System.out.println("Cast.java3");
            return CSV.infoStreamFromReader(castReader, Cast.class,
                Cast.Extracting::dataFromCSV,
                dataCast -> infoFromData(dataCast, films, actors)
            );
        }
    }
}