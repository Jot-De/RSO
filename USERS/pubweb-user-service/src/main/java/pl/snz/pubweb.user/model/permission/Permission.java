package pl.snz.pubweb.user.model.permission;

import lombok.Data;
import pl.snz.pubweb.user.model.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "permission")
@Entity
public class Permission extends IdentifiableEntity<Long> {

    @Column(name = "permission_key", nullable = false, updatable = false)
    private String permissionKey;

    @Column(nullable = false, updatable = true)
    private String name;

    @Column(length = 4000, nullable = false)
    private String text;

}
