package data.musta.it.apiit.com.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by musta on 23-Dec-17.
 */

public class Collections {
    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }
}
