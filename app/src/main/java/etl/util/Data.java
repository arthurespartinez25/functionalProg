package etl.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Data {

    static List<Method> accessorsOf(Class<?> clazz) {

//        getAccessor = public java.lang.String etl.data.CastByFilm$Header.RECORD_TYPE()
//          public java.lang.String etl.data.CastByFilm$Header.FILM_SEQUENCE()
//          public java.lang.String etl.data.CastByFilm$Header.FILM_NAME()
//          public java.lang.String etl.data.CastByFilm$Header.FILM_RELEASE_YEAR()

        return Arrays
            .stream(clazz.getRecordComponents())
            .map(RecordComponent::getAccessor)
            .toList();
    }

    static <T extends Record> Constructor<T> ctorOf(Class<T> clazz) {
        final Class<?>[] paramTypes = Arrays
            .stream(clazz.getRecordComponents())
            .map(RecordComponent::getType)
            .toArray(Class<?>[]::new);
        try {
            return clazz.getDeclaredConstructor(paramTypes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static <T extends Record> Object[] objectsFromData(T data, List<Method> accessors) {
        return accessors
            .stream()
            .<Object>map(method -> {
                try {
//                    method:(mapping)
//                    public java.lang.String etl.data.CastByFilm$Header.RECORD_TYPE()
//                    public java.lang.String etl.data.CastByFilm$Header.RECORD_TYPE()
//                    public java.lang.String etl.data.CastByFilm$Header.FILM_SEQUENCE()
//                    public java.lang.String etl.data.CastByFilm$Header.FILM_NAME()
//                    public java.lang.String etl.data.CastByFilm$Header.FILM_RELEASE_YEAR()

//                    data:
//                    Header[RECORD_TYPE=1, FILM_SEQUENCE=01, FILM_NAME=The Deer Hunter, FILM_RELEASE_YEAR=1978]

//                    return = 01, 01, The Deer Hunterhere, 1978
//                    return = 2 01 1 Lindahere Meryl Streeph 29
                    System.out.println(method.invoke(data));
                    return method.invoke(data);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            })
            .toArray();
    }

    static <T> Optional<T> parse(final String data, final Function<String, T> mapper) {
        // data = date;
        // mapper = parseInt
        if (data == null || data.length() == 0) {
            return Optional.empty();
        } else {
            try {
                return Optional.ofNullable(mapper.apply(data));
            } catch (Exception ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
        }
    }
//    static class = A static inner class is a nested class which is a static member of the outer class.
//    It can be accessed without instantiating the outer class, using other static members
    static class Sequencer {
        private long seq = 0L;
        public Long next() {
        	return ++seq; }
    }
}
