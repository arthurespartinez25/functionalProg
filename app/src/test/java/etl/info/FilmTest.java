package etl.info;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FilmTest {

    @Test
    @DisplayName("film[3] is \"i am sam\"")
    void film3isIamSam() {
    	System.out.println("FilmTest");
        final var bytes = etl.data.FilmTest.data.getBytes(StandardCharsets.UTF_8);
        final var in = new ByteArrayInputStream(bytes);
        try (
            final var reader = new InputStreamReader(in);
        ) {
            final var films = etl.data.Film.Extracting
                .infoStreamFromReader(reader)
                .toList();
            assertEquals(17, films.size());
            // 7e510c52,i am sam,2001
            final var film4 = films.get(3);
            assertEquals("7e510c52", film4.id());
            assertEquals("i am sam", film4.name());
            assertEquals(Year.of(2001), film4.release());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }

    }
}
