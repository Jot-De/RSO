package pl.snz.pubweb.user.util;

import java.util.function.Function;

public class Nulls {
    public static <T,R> R npeToNull(Function<T,R> retrieval, T obj) {
        try {
            return retrieval.apply(obj);
        } catch (NullPointerException npe) {
            return null;
        }
    }

}
