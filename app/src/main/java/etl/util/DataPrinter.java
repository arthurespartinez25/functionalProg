package etl.util;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

import org.apache.commons.csv.CSVPrinter;

public class DataPrinter implements AutoCloseable
{
    private long totalLines = 0L;
    private long invalidLines = 0L;

    public static record Report(
        long totalLines,
        long invalidLines
    ) {}

    public Report getReport() {
    	System.out.println("DataPrinter");
        return new Report(totalLines, invalidLines);
    }

    private final CSVPrinter printer;

    public DataPrinter(final Writer writer)
    throws IOException
    {
    	System.out.println("DataPrinter2");
        printer = new CSVPrinter(writer, CSV.getWriterFormat());
    }

    @Override
    public void close() throws Exception {
    	System.out.println("DataPrinter3");
        printer.close();
    }
    
    public <T extends Record> void accept(final T data, final Function<T, Object[]> mapper) {
    	System.out.println("DataPrinter4");
        totalLines++;

        if (data == null) {
            invalidLines++;
        } else {
            try {
                if (totalLines != 1) printer.println();
                for (var value : mapper.apply(data)) {
                    printer.print(value);
                }
            } catch (IOException ex) {
                invalidLines++;
                ex.printStackTrace();
            }
        }
    }
}