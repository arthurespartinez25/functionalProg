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
    	System.out.println("Data");
        return Arrays
            .stream(clazz.getRecordComponents())
            .map(RecordComponent::getAccessor)
            .toList();
    }

    static <T extends Record> Constructor<T> ctorOf(Class<T> clazz) {
    	System.out.println(clazz);
    	System.out.println("Data2");
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
    	System.out.println("Data3");
        return accessors
            .stream()
            .<Object>map(method -> {
                try {
                    return method.invoke(data);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            })
            .toArray();
    }

    static <T> Optional<T> parse(final String data, final Function<String, T> mapper) {
    	System.out.println("Data4");
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

    static class Sequencer {
        private long seq = 0L;
        public Long next() { 
        	System.out.println("Data5");
        	return ++seq; }
    }
}
