package pl.snz.pubweb.commons.util;

import java.util.function.Predicate;

public class Predicates {

    public static <T> Predicate<T> not(Predicate<T> p) {
        return (t) -> !p.test(t);
    }
}
