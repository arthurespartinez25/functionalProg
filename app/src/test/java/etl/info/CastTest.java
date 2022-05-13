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

        final var cs = StandardCharsets.UTF_8;

        final var bytesFilm = etl.data.FilmTest.data.getBytes(cs);
        final var inFilm = new ByteArrayInputStream(bytesFilm);
        final var bytesActor = etl.data.ActorTest.data.getBytes(cs);
        final var inActor = new ByteArrayInputStream(bytesActor);
        final var bytesCast = etl.data.CastTest.data.getBytes(cs);
        final var inCast = new ByteArrayInputStream(bytesCast);
        try (
            final var readerFilm = new InputStreamReader(inFilm);
            final var readerActor = new InputStreamReader(inActor);
            final var readerCast = new InputStreamReader(inCast);
        ) {
        	System.out.println("CastTest"+"34");
            final var films = etl.data.Film.Extracting
                .infoStreamFromReader(readerFilm).toList();
            System.out.println("CastTest"+"37");
            final var actors = etl.data.Actor.Extracting
                .infoStreamFromReader(readerActor).toList();
            final var casts = etl.data.Cast.Extracting
                .infoStreamFromReader(readerCast, films, actors).toList();
            System.out.println(bytesFilm);
            System.out.println(inFilm);
            System.out.println(bytesActor);
            System.out.println(inActor);
            System.out.println(bytesCast);
            System.out.println(inCast);
            System.out.println(readerFilm);
            System.out.println(readerActor);
            System.out.println(readerCast);
            System.out.println(films);
            System.out.println(actors);
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
        	System.out.println("CastTest"+"74");
            ex.printStackTrace();
            fail();
        }
    }
}
