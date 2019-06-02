package pl.snz.pubweb.commons.util.function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.function.BiPredicate;
import java.util.function.Function;

@ToString
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(staticName = "of")
public class Pair<L, R> {
    private final L left;
    private final R right;

    public boolean matches(BiPredicate<L, R> predicate) {
        return predicate.test(left, right);
    }

    public <X,Y> Pair<X,Y> map(Function<L,X> lMap, Function<R,Y> rMap) {
        return Pair.of(lMap.apply(left), rMap.apply(right));
    }
}
