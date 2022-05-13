package etl.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import etl.util.Data;
import etl.util.DataPrinter;
import etl.util.DataPrinter.Report;

public interface CastByFilm {

    enum RecordType {
        HEADER(1), ITEM(2);
        private int val;
        int value() { return val;}
        RecordType(int value) { val = value; }
    }

    record Header(
        String RECORD_TYPE,
        String FILM_SEQUENCE,
        String FILM_NAME,
        String FILM_RELEASE_YEAR
    ) {}

    record Item(
        String RECORD_TYPE,
        String FILM_SEQUENCE,
        String CAST_SEQUENCE,
        String CAST_ROLE,
        String ACTOR_NAME,
        String ACTOR_AGE_AT_RELEASE
    ) {}

    public interface Loading {

        static final List<Method> headerAccessors = Data.accessorsOf(Header.class);
        static final List<Method> itemAccessors = Data.accessorsOf(Item.class);
    
        static Object[] headerValues(final Header dataHeader) {
        	System.out.println("CastByFilm.java");
            return Data.objectsFromData(dataHeader, headerAccessors);
        }

        static Object[] itemValues(final Item dataHeader) {
            return Data.objectsFromData(dataHeader, itemAccessors);
        }

        static Header dataHeaderFromInfo(
            final Long filmSequence,
            final etl.info.Film film
        ) {
        	System.out.println("CastByFilm2.java");
            return new Header(
                String.format("%1d", RecordType.HEADER.value()),
                String.format("%02d", filmSequence),
                film.name(),
                film.release().toString()
            );
        }

        static Item dataItemFromInfo(
            final Long filmSequence,
            final Long castSequence,
            final etl.info.Cast cast
        ) {
        	System.out.println("CastByFilm3.java");
            final Integer ageAtRelease
                = cast.film().release().getValue()
                - cast.actor().born().getYear();

            if (ageAtRelease < 0) {
                return null;
            } else {
                return new Item(
                    String.format("%1d", RecordType.ITEM.value()),
                    String.format("%02d", filmSequence),
                    String.format("%1d", castSequence),
                    cast.role(),
                    cast.actor().name(),
                    ageAtRelease.toString()
                );
            }
        }

        static Report streamWriterCastByFilm(
            final InputStream inFilm,
            final InputStream inActor,
            final InputStream inCast,
            final OutputStreamWriter outWriter
        )
            throws Exception
        {
        	System.out.println("CastByFilm4.java");
            try (
                final var readerFilm = new InputStreamReader(inFilm);
                final var readerActor = new InputStreamReader(inActor);
                final var readerCast = new InputStreamReader(inCast);
                final var printer = new DataPrinter(outWriter);
            ) {
                final var filmSequencer = new Data.Sequencer();
                final var films = etl.data.Film.Extracting
                    .infoStreamFromReader(readerFilm).toList();
                final var actors = etl.data.Actor.Extracting
                    .infoStreamFromReader(readerActor).toList();

                etl.data.Cast.Extracting
                    .infoStreamFromReader(readerCast, films, actors)
                    .collect(Collectors.groupingBy(
                        etl.info.Cast::film
                        ,() -> new TreeMap<etl.info.Film, List<etl.info.Cast>>(
                            Comparator
                            .comparing(etl.info.Film::release)
                            .thenComparing(etl.info.Film::name)
                        ),
                        Collectors.toList()
                    ))
                    .forEach((film, casts) -> {
                        final var filmSeq = filmSequencer.next();
                        printer.accept(
                            Loading.dataHeaderFromInfo(filmSeq, film),
                            Loading::headerValues
                        );

                        final var castSequencer = new Data.Sequencer();
                        casts.stream()
                            .sorted(Comparator
                                .comparing(etl.info.Cast::actor, Comparator
                                    .comparing(etl.info.Actor::born)
                                    .reversed()
                                )
                                .thenComparing(etl.info.Cast::actor, Comparator
                                    .comparing(etl.info.Actor::name)
                                )
                            )
                            .forEach(cast ->
                                printer.accept(
                                    Loading.dataItemFromInfo(
                                        filmSeq, castSequencer.next(), cast
                                    ),
                                    Loading::itemValues
                                )
                            );
                    });
                return printer.getReport();
            }
        }
    }
}

