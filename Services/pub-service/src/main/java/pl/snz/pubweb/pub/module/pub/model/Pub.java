package pl.snz.pubweb.pub.module.pub.model;


import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.common.data.Picture;
import pl.snz.pubweb.pub.module.tag.model.Tag;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Table(name = "pub")
@Entity
public class Pub extends IdentifiableEntity<Long> {

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Address address;

    @Column
    private String name;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "pub", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Picture> pictures;

    @ManyToMany
    private Set<PubType> types = new HashSet<>();

    @Column
    private LocalDate added;

    @Column(length = 4000)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public LocalDate getAdded() {
        return added;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<PubType> getTypes() {
        return types;
    }

    public void setTypes(Set<PubType> types) {
        this.types = types;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

