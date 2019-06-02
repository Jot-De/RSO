package pl.snz.pubweb.commons.util;

import pl.snz.pubweb.commons.util.function.Pair;

import java.util.List;
import java.util.stream.Stream;

public class Lists {

    public static <T> Stream<Pair<T,T>> toAdjacentPairs(List<T> elements) {
        if(elements.size() < 2)
            return Stream.empty();
        final Stream.Builder<Pair<T,T>> builder = Stream.builder();

        for(int i = 0; i < elements.size() -1; i++) {
            builder.add(Pair.of(elements.get(i), elements.get(i+1)));
        }

        return builder.build();
    }


}
