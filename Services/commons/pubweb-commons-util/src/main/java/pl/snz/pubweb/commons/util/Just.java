package pl.snz.pubweb.commons.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Just<T> {

    public static <T> Just<T> of(T val) {
        Objects.requireNonNull(val);
        return new Just<>(val);
    }
    @Getter @Accessors(fluent = true)
    private final T val;

    public <R> Just<R> map(Function<T,R> mapper) {
        return new Just<>(mapper.apply(val));
    }
}
