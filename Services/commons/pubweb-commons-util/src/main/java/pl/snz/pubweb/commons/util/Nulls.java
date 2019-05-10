package pl.snz.pubweb.commons.util;

import java.util.function.Function;

public class Nulls {
    public static <T,R> R npeToNull(Function<T,R> retrieval, T obj) {
        try {
           return obj == null ? null  : retrieval.apply(obj);
        } catch (NullPointerException npe) {
            return null;
        }
    }

}
