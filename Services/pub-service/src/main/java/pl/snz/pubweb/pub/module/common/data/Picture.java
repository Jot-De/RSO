package pl.snz.pubweb.pub.module.common.data;

import lombok.Data;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.pub.model.Pub;

import javax.persistence.*;

@Data
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

}
