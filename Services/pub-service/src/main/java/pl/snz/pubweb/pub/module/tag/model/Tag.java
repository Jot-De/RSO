package pl.snz.pubweb.pub.module.tag.model;

import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tag")
@Entity
public class Tag extends IdentifiableEntity<Long> {

    @Column(unique = true, length = 50)
    private String name;

    @Column(unique = true, length = 50)
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
