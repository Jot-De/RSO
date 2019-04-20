package pl.snz.pubweb.user.model.display;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.model.IdentifiableEntity;

import javax.persistence.*;


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

    public static UserDisplaySettings defaul() {
        return new UserDisplaySettings(DisplayLevel.ME_ONLY, DisplayLevel.ME_ONLY, DisplayLevel.ME_ONLY);
    }

}
