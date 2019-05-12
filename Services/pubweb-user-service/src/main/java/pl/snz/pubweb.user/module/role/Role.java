package pl.snz.pubweb.user.module.role;

import lombok.Data;
import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "role")
public class Role extends IdentifiableEntity<Long> {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "updatable", updatable = false)
    private boolean updatable;

    @Column(name = "assignable", updatable = false)
    private boolean assignable;

}
