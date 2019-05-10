package pl.snz.pubweb.pub.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mappers {

    public static <T,R> List<R> toList(Collection<T> ts, Function<T,R> mapper) {
        return ts == null ? null : ts.stream().map(mapper).collect(Collectors.toList());
    }

}
