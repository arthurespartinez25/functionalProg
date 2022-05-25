package etl.info;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ActorTest {

    @Test
    @DisplayName("Actor[3] Cameron Diaz, Actor[15] Robin William")
    void testActor3Actor15() {
    	System.out.println("ActorTest");
        // list converted to bytes
        final var bytes = etl.data.ActorTest.data.getBytes(StandardCharsets.UTF_8);
        final var in = new ByteArrayInputStream(bytes);
        try (
                // interpreter of bytes
            final var reader = new InputStreamReader(in);
        ) {
            final var actors = etl.data.Actor.Extracting
                .infoStreamFromReader(reader)
                .toList();
            assertEquals(20, actors.size());

            // 606db772,Cameron Diaz,1972,08,30,
            final var actor4 = actors.get(3);
            assertEquals("606db772", actor4.id());
            assertEquals("Cameron Diaz", actor4.name());
            assertEquals(LocalDate.of(1972,8,30), actor4.born());
            assertEquals(null, actor4.died());

            // e82f1fa0,Robin Williams,1951,07,21,2014
            final var actor16 = actors.get(15);
            assertEquals("e82f1fa0", actor16.id());
            assertEquals("Robin Williams", actor16.name());
            assertEquals(LocalDate.of(1951,7,21), actor16.born());
            assertEquals(Year.of(2014), actor16.died());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
