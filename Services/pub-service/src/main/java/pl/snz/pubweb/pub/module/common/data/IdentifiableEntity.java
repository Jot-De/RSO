package pl.snz.pubweb.pub.module.common.data;


import javax.persistence.*;

@MappedSuperclass
public class IdentifiableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
