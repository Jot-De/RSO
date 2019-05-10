package pl.snz.pubweb.pub.module.common.data;

import pl.snz.pubweb.pub.module.pub.model.Pub;

import javax.persistence.*;

@Entity
@Table(name = "picture")
public class Picture extends IdentifiableEntity {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 100000)
    private byte[] bytes;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "pub_id")
    private Pub pub;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

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

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }
}
