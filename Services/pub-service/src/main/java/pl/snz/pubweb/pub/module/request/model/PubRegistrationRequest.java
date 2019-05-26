package pl.snz.pubweb.pub.module.request.model;

import lombok.Getter;
import lombok.Setter;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.picture.model.Picture;
import pl.snz.pubweb.pub.module.pub.model.Pub;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pub_registration_request")
public class PubRegistrationRequest extends IdentifiableEntity<Long> {

    @Column
    private Long userId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
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

}
