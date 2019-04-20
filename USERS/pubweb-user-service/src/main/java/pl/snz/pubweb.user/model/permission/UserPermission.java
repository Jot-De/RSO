package pl.snz.pubweb.user.model.permission;

import lombok.Data;
import pl.snz.pubweb.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "user_permission")
@Entity
public class UserPermission implements Serializable {

    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Id
    @JoinColumn(name = "permission_id")
    @ManyToOne
    private Permission permission;

    @Column(name = "valid_until")
    private LocalDate validUntil;
}
