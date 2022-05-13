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
    	System.out.println(clazz);
        final var componentNames = Arrays
            .stream(clazz.getRecordComponents())
            .map(RecordComponent::getName)
            .toArray(String[]::new);
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
            return parser.getRecords().stream()
                .map(mappingDataFromCSV::apply)
                .map(mappingInfoFromData::apply);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Stream.<T>empty();
        }
    }
}
