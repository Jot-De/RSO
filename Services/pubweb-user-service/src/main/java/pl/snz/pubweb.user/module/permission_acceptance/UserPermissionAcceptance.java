package pl.snz.pubweb.user.module.permission_acceptance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.user.module.permission.model.Permission;
import pl.snz.pubweb.user.module.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "user_permission_acceptance")
@Entity
public class UserPermissionAcceptance extends IdentifiableEntity<Long> implements Serializable {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "permission_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;

    @Column(name = "valid_until")
    private LocalDate validUntil;
}
