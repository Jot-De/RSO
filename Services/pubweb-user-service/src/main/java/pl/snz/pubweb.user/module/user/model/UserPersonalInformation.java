package pl.snz.pubweb.user.module.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "user_personal_information")
@Entity
public class UserPersonalInformation extends IdentifiableEntity<Long> {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "city")
    private String city;
    @Column(nullable = true, length = 4000)
    private String aboutMe;

    public static UserPersonalInformation none() {
        return new UserPersonalInformation(null, null, null, null);
    }
}
