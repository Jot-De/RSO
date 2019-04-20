package pl.snz.pubweb.user.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "role")
public class Role extends IdentifiableEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "updatable", updatable = false)
    private boolean updatable;

}
