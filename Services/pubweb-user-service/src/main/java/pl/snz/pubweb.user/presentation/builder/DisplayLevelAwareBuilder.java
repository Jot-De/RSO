package pl.snz.pubweb.user.presentation.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pl.snz.pubweb.user.model.display.DisplayLevel;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/** Stores provided display level internally,
 *  sets given response fields only if provided display level is sufficient */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DisplayLevelAwareBuilder<T,R> {
    private final DisplayLevel provided;
    private final T mappedObject;
    private final R response;

    public static <T,R> DisplayLevelAwareBuilder<T,R> of(DisplayLevel provided, T mappedObject, Supplier<R> supplier ) {
        return new DisplayLevelAwareBuilder<>(provided, mappedObject, supplier.get());
    }

    public <V> DisplayLevelAwareBuilder<T,R> set(DisplayLevel required, Function<T, V> mapper, BiConsumer<R, V> setter) {
        if (provided.getConfidenceLevel() >= required.getConfidenceLevel())
            setter.accept(response, mapper.apply(mappedObject));
        return this;
    }

    public <V> DisplayLevelAwareBuilder<T,R> set(DisplayLevel required, BiFunction<T, DisplayLevel, V> mapper, BiConsumer<R, V> setter) {
        if (provided.getConfidenceLevel() >= required.getConfidenceLevel())
            setter.accept(response, mapper.apply(mappedObject, provided));
        return this;
    }

    public R build() {
        return response;
    }
}
