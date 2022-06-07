/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package etl;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        String actorPath = "app/actor.csv";
        String filmPath = "app/film.csv";
        String castPath = "app/cast.csv";
        String line = "";
        StringBuilder actors= new StringBuilder();
        StringBuilder films= new StringBuilder();
        StringBuilder casts= new StringBuilder();

        final var cs = StandardCharsets.UTF_8;

        try (BufferedReader actorReader = new BufferedReader(new FileReader(actorPath));
             BufferedReader filmReader = new BufferedReader(new FileReader(filmPath));
             BufferedReader castReader = new BufferedReader(new FileReader(castPath));
             PrintWriter resultWriter = new PrintWriter("result.csv")){

            while((line= actorReader.readLine()) != null){
                actors.append(line + "\n");
            }

            while((line= filmReader.readLine()) != null){
                films.append(line + "\n");
            }

            while((line= castReader.readLine()) != null){
                casts.append(line + "\n");
            }

            final var bytesFilm = films.toString().getBytes(cs);
            final var inFilm = new ByteArrayInputStream(bytesFilm);
            final var bytesActor = actors.toString().getBytes(cs);
            final var inActor = new ByteArrayInputStream(bytesActor);
            final var bytesCast = casts.toString().getBytes(cs);
            final var inCast = new ByteArrayInputStream(bytesCast);

            final var outCastByFilm = new ByteArrayOutputStream();
            final var outWriter = new OutputStreamWriter(outCastByFilm, cs);

            final var report
                    = etl.data.CastByFilm.Loading.streamWriterCastByFilm(
                    inFilm, inActor, inCast, outWriter
            );

            resultWriter.write(String.valueOf(outCastByFilm));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
