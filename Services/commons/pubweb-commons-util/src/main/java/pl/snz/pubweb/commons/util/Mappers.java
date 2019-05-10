package pl.snz.pubweb.commons.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mappers {

    public static <T,R> Function <Collection<T>, List<R>> list(Function<T,R> mapper) {
        return (ts) -> ts == null ? null : ts.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T,R> Function <Collection<T>, Set<R>> set(Function<T,R> mapper) {
        return (ts) -> ts == null ? null : ts.stream().map(mapper).collect(Collectors.toSet());
    }

    public static <T,R> List<R> list(Function<T,R> mapper, Collection<T> coll) {
        return list(mapper).apply(coll);
    }

    public static <T,R> Set<R> set(Function<T,R> mapper, Collection<T> coll) {
        return set(mapper).apply(coll);
    }


}
