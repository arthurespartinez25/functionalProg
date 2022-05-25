package etl.info;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CastByFilmTest {

    @Test
    @DisplayName("all CastByFilm info as expected")
    void testCastByFilmStream() {
    	System.out.println("CastByFilmTest");
        final var cs = StandardCharsets.UTF_8;

        final var bytesFilm = etl.data.FilmTest.data.getBytes(cs);
        final var inFilm = new ByteArrayInputStream(bytesFilm);
        final var bytesActor = etl.data.ActorTest.data.getBytes(cs);
        final var inActor = new ByteArrayInputStream(bytesActor);
        final var bytesCast = etl.data.CastTest.data.getBytes(cs);
        final var inCast = new ByteArrayInputStream(bytesCast);

//        ByteArrayOutputStream = used to write common data into multiple files
//        OutputStreamWriter = used to convert character stream to byte stream,
//              the characters are encoded into byte using a specified charset.

        final var outCastByFilm = new ByteArrayOutputStream();
        final var outWriter = new OutputStreamWriter(outCastByFilm, cs);

        try {
            System.out.println("DataBefore");
            final var report
            = etl.data.CastByFilm.Loading.streamWriterCastByFilm(
                inFilm, inActor, inCast, outWriter
            );

            assertEquals(52L, report.totalLines());
            assertEquals(0L, report.invalidLines());
            assertEquals(
                etl.data.CastByFilmTest.data,
                outCastByFilm.toString(cs)
            );
        } catch (Exception ex) {
            fail();
        }
    }
}
