package pl.snz.pubweb.user.module.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum DisplayLevel{
    ME_ONLY(2),
    FRIENDS(1),
    ALL(0);
    @Getter private int confidenceLevel;

    public static DisplayLevel ofConfidenceLevel(int confidenceLevel) {
        return Arrays.stream(DisplayLevel.values()).
                filter(d -> d.confidenceLevel == confidenceLevel)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
