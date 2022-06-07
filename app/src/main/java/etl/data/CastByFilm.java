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
import org.w3c.dom.ls.LSOutput;

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

//        headerAccessors
//
//        [public java.lang.String etl.data.CastByFilm$Header.RECORD_TYPE(), public java.lang.String etl.data.CastByFilm$Header.FILM_SEQUENCE(),
//        public java.lang.String etl.data.CastByFilm$Header.FILM_NAME(), public java.lang.String etl.data.CastByFilm$Header.FILM_RELEASE_YEAR()]
//
//        itemAccessors
//
//        [public java.lang.String etl.data.CastByFilm$Item.RECORD_TYPE(), public java.lang.String etl.data.CastByFilm$Item.FILM_SEQUENCE(),
//        public java.lang.String etl.data.CastByFilm$Item.CAST_SEQUENCE(), public java.lang.String etl.data.CastByFilm$Item.CAST_ROLE(), public java.lang.String etl.data.CastByFilm$Item.ACTOR_NAME(), public java.lang.String etl.data.CastByFilm$Item.ACTOR_AGE_AT_RELEASE()]
    
        static Object[] headerValues(final Header dataHeader) {
//            dataHeader = Header[RECORD_TYPE=1, FILM_SEQUENCE=01, FILM_NAME=The Deer Hunter, FILM_RELEASE_YEAR=1978
            return Data.objectsFromData(dataHeader, headerAccessors);
        }

        static Object[] itemValues(final Item dataHeader) {
//            dataHeader = Item[RECORD_TYPE=2, FILM_SEQUENCE=01, CAST_SEQUENCE=2, CAST_ROLE=Michael Vronsky, ACTOR_NAME=Robert De Niro, ACTOR_AGE_AT_RELEASE=35
            return Data.objectsFromData(dataHeader, itemAccessors);
        }

        static Header dataHeaderFromInfo(
            final Long filmSequence,
            final etl.info.Film film
        ) {

//            return = Header[RECORD_TYPE=1, FILM_SEQUENCE=01, FILM_NAME=The Deer Hunter, FILM_RELEASE_YEAR=1978

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
            final Integer ageAtRelease
                = cast.film().release().getValue()
                - cast.actor().born().getYear();

            if (ageAtRelease < 0) {
                return null;
            } else {
//                return = Item[RECORD_TYPE=2, FILM_SEQUENCE=01, CAST_SEQUENCE=2, CAST_ROLE=Michael Vronsky, ACTOR_NAME=Robert De Niro, ACTOR_AGE_AT_RELEASE=35]

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
//            java.io.ByteArrayInputStream@536f2a7e
//            java.io.ByteArrayInputStream@72bc6553
//            java.io.ByteArrayInputStream@66982506
//            java.io.OutputStreamWriter@70cf32e3
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

//                infoStreamFromReader return
//                Cast[film=Film[id=8021e3b6, name=Knight and Day, release=2010], actor=Actor[id=7c0f5849, name=Tom Cruise, born=1962-07-03, died=null], role=Roy Miller]

//                TreeMap<Key, Value>
//                forEach = Film[id=65d03714, name=Good Morning, Vietnam, release=1987]
//                [Cast[film=Film[id=65d03714, name=Good Morning, Vietnam, release=1987], actor=Actor[id=e82f1fa0, name=Robin Williams, born=1951-07-21, died=2014], role=Adrian Cronauer]]

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
//                        filmSeq = casts/Film
                        printer.accept(
                            Loading.dataHeaderFromInfo(filmSeq, film),
                            Loading::headerValues
                        );

                        final var castSequencer = new Data.Sequencer();

//                        forEach = Cast[film=Film[id=fd85472f, name=The Deer Hunter, release=1978], actor=Actor[id=5332551c, name=Meryl Streep, born=1949-06-22, died=null], role=Linda]
//                        Cast[film=Film[id=fd85472f, name=The Deer Hunter, release=1978], actor=Actor[id=a8474cc0, name=Robert De Niro, born=1943-08-17, died=null], role=Michael Vronsky]
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

