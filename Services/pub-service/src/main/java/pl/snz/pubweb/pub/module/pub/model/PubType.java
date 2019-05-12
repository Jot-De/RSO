package pl.snz.pubweb.pub.module.pub.model;

import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "type")
@Entity
public class PubType extends IdentifiableEntity {

    @Column
    private String name;

    @Column
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
