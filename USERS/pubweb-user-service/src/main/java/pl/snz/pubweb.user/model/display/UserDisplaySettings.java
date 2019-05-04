package pl.snz.pubweb.user.model.display;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.model.IdentifiableEntity;

import javax.persistence.*;
import java.util.stream.Stream;


@Entity
@Table(name = "user_display_settings")
@Data  @NoArgsConstructor @AllArgsConstructor
public class UserDisplaySettings extends IdentifiableEntity<Long> {

    @Column(name = "name_display_level")
    @Enumerated(EnumType.STRING)
    private DisplayLevel nameDisplayLevel;

    @Column(name = "surname_display_level")
    @Enumerated(EnumType.STRING)
    private DisplayLevel surnameDisplayLevel;

    @Column(name = "city_display_level")
    @Enumerated(EnumType.STRING)
    private DisplayLevel cityDisplayLevel;

    @Column(name = "about_me_display_level")
    @Enumerated(EnumType.STRING)
    private DisplayLevel aboutMeDisplayLevel;

    public static UserDisplaySettings allPrivate() {
        return new UserDisplaySettings(DisplayLevel.ME_ONLY, DisplayLevel.ME_ONLY, DisplayLevel.ME_ONLY, DisplayLevel.ME_ONLY);
    }

    /** Usefull when we have to determine whether user has access to any of properties */
    public DisplayLevel getLowest() {
        return Stream.of(nameDisplayLevel, surnameDisplayLevel, cityDisplayLevel)
                .map(DisplayLevel::getConfidenceLevel)
                .reduce(Math::min)
                .map(DisplayLevel::ofConfidenceLevel)
                .orElseThrow(RuntimeException::new);
    }



}
