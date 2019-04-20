package pl.snz.pubweb.user.model.display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DisplayLevel{
    ME_ONLY(2),
    FRIENDS(1),
    ALL(0);
    @Getter private int confidenceLevel;
}
