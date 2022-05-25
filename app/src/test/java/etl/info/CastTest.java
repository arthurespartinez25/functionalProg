package etl.info;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CastTest {

    @Test
    @DisplayName("Dakota Fanning in War of the Worlds")
    void testCastDakotaWar() {
    	System.out.println("CastTest");
        // for Unicode encoding system
        final var cs = StandardCharsets.UTF_8;
        // getBytes = encodes string into bytes
        // ByteArrayInputStream =  used to read byte array as input stream
        final var bytesFilm = etl.data.FilmTest.data.getBytes(cs);
        final var inFilm = new ByteArrayInputStream(bytesFilm);
        final var bytesActor = etl.data.ActorTest.data.getBytes(cs);
        final var inActor = new ByteArrayInputStream(bytesActor);
        final var bytesCast = etl.data.CastTest.data.getBytes(cs);
        final var inCast = new ByteArrayInputStream(bytesCast);
        try (
                //InputStreamReader = reads bytes and decodes them into characters using a UTF_8
            final var readerFilm = new InputStreamReader(inFilm);
            final var readerActor = new InputStreamReader(inActor);
            final var readerCast = new InputStreamReader(inCast);
        ) {
            // Gets the list of films, actors, casts
            // Go to etl.data.Film
            final var films = etl.data.Film.Extracting
                .infoStreamFromReader(readerFilm).toList();
            final var actors = etl.data.Actor.Extracting
                .infoStreamFromReader(readerActor).toList();
            final var casts = etl.data.Cast.Extracting
                .infoStreamFromReader(readerCast, films, actors).toList();
            //checks if casts length is 35
            System.out.println(casts);
            assertEquals(35, casts.size());

            // Cast: 32adaa60,fbcded80,Rachel Ferrier
            // Film: 32adaa60,War of the Worlds,2005
            // Actr: fbcded80,Dakota Fanning,1994,02,23,
            final var filmWar = new etl.info.Film(
                "32adaa60",
                "War of the Worlds",
                Year.of(2005)
            );
            final var actorDakota = new etl.info.Actor(
                "fbcded80",
                "Dakota Fanning",
                LocalDate.of(1994,2,23),
                null
            );
            final var cast4 = casts.get(3);
            assertEquals(filmWar, cast4.film());
            assertEquals(actorDakota, cast4.actor());
            assertEquals("Rachel Ferrier", cast4.role());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
