package pl.snz.pubweb.pub.module.request.model;

import lombok.Getter;
import lombok.Setter;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.picture.model.Picture;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.tag.model.Tag;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pub_registration_request")
public class PubRegistrationRequest extends IdentifiableEntity<Long> {

    @Column
    private Long userId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column
    private String name;

    @Column(length = 4000)
    private String description;

    @Column
    private LocalDate added;

    @Column
    private LocalDate processed;

    @OneToOne
    @JoinColumn(name = "pub_id")
    private Pub pub;

    @Column
    @Enumerated(EnumType.STRING)
    private PubRegistrationStatus status;

    @OneToOne(mappedBy = "request", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Picture picture;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

}