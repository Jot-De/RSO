package pl.snz.pubweb.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static UserPersonalInformation none() {
        return new UserPersonalInformation(null, null, null);
    }
}
