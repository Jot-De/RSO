package pl.snz.pubweb.pub.module.pub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.picture.model.Picture;
import pl.snz.pubweb.pub.module.tag.model.Tag;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Builder
@Data
@Entity
@Table(name = "pub")
public class Pub extends IdentifiableEntity<Long> {

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Address address;

    @Column
    private String name;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "pub", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Picture> pictures = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<PubType> types = new HashSet<>();

    @Column
    private LocalDate added;

    @Column(length = 4000)
    private String description;

}

