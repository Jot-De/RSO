package pl.snz.pubweb.pub.module.picture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;

import javax.persistence.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "picture")
public class Picture extends IdentifiableEntity<Long> {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 100000)
    private byte[] bytes;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String format;

    @ManyToOne
    @JoinColumn(name = "pub_id")
    private Pub pub;

    @OneToOne
    @JoinColumn(name = "request_id")
    private PubRegistrationRequest request;

}
