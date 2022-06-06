package etl.util;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
// import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

public interface CSV {

    static CSVFormat getReaderFormat(final Class<? extends Record> clazz) {
    	System.out.println("CSV");
        final var componentNames = Arrays
            .stream(clazz.getRecordComponents())
            .map(RecordComponent::getName)
            .toArray(String[]::new);
//        clazz.getRecordComponents()
//          java.lang.String ID
//          java.lang.String NAME
//          java.lang.String BORN_YEAR
//          java.lang.String BORN_MONTH
//          java.lang.String BORN_DAY
//          java.lang.String DIED_YEAR

//         getName()
//          ID
//          NAME
//          BORN_YEAR
//          BORN_MONTH
//          BORN_DAY
//          DIED_YEAR

        return CSVFormat.Builder
            .create()
            .setHeader(componentNames)
            .setIgnoreEmptyLines(false)
            .setIgnoreSurroundingSpaces(true)
            .build();
    }

    static CSVFormat getWriterFormat() {
    	System.out.println("CSV2");
        return CSVFormat.Builder
            .create()
            .setTrim(true)
            .setRecordSeparator('\n')
            .setQuoteMode(QuoteMode.MINIMAL)
            .setAutoFlush(true)
            .build();
    }

    static <T extends Record> T dataFromCSV(final CSVRecord csv, final Constructor<T> ctor) {
    	System.out.println("CSV3");
        var objects = csv.stream()
            .<String>map(value -> value.length() == 0 ? null : value)
            .toArray(Object[]::new);
        try {
//            ctor = public etl.data.Actor(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)
            return ctor.newInstance(objects);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static <S extends Record> Stream<S> dataStreamFromReader(
        final Reader reader,
        final Class<S> dataClass,
        final Function<CSVRecord, S> mappingDataFromCSV
    ) {
    	System.out.println("CSV4");
        try (
            final var parser = CSVParser.parse(reader, getReaderFormat(dataClass))
        ) {
            return parser.getRecords().stream()
                .map(mappingDataFromCSV::apply);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Stream.<S>empty();
        }
    }

    static <S extends Record, T extends Record> Stream<T> infoStreamFromReader(
        final Reader reader,
        final Class<S> dataClass,
        final Function<CSVRecord, S> mappingDataFromCSV,
        final Function<S, T> mappingInfoFromData
    ) {
        System.out.println("CSV5");
        try (
            final var parser = CSVParser.parse(reader, getReaderFormat(dataClass))
        ) {
            //ACTOR
//            CSVRecord [comment='null', recordNumber=1, values=[7c0f5849, Tom Cruise, 1962, 07, 03, ]
//            mappingDataFromCSV = Actor[ID=7c0f5849, NAME=Tom Cruise, BORN_YEAR=1962, BORN_MONTH=07, BORN_DAY=03, DIED_YEAR=null]);
//            mappingInfoFromData = Actor[id=7c0f5849, name=Tom Cruise, born=1962-07-03, died=null];

            //CAST passed arguments

//            mappingDataFromCSV = CSVRecord [comment='null', recordNumber=1, values=[8021e3b6, 7c0f5849, Roy Mill]
//            mappingInfoFromData = Cast[FILM_ID=8021e3b6, ACTOR_ID=7c0f5849, ROLE=Roy Miller]

            // Pipeline = java.util.stream.ReferencePipeline$3@55cb6996;

            return parser.getRecords().stream()
                .map(mappingDataFromCSV::apply)
                .map(mappingInfoFromData::apply);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Stream.<T>empty();
        }
    }
}
