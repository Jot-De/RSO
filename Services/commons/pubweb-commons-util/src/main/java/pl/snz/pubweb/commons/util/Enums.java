package pl.snz.pubweb.commons.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class Enums {

    public static <E extends Enum<E>, R> Optional<E> enumFromFieldValue(Class<E> clazz, Function<E, R> getter, R value) {
        return Arrays.stream(clazz.getEnumConstants()).filter((e) -> getter.apply(e).equals(value)).findFirst();
    }
}
