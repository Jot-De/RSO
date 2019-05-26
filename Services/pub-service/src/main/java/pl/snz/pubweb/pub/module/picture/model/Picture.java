package pl.snz.pubweb.pub.module.picture.model;

import lombok.*;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;

import javax.persistence.*;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Picture picture = (Picture) o;

        if (name != null ? !name.equals(picture.name) : picture.name != null) return false;
        return format != null ? format.equals(picture.format) : picture.format == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }
}
