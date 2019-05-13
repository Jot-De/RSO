package pl.snz.pubweb.commons.data;

import javax.persistence.*;

@MappedSuperclass
public class IdentifiableEntity<I> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected I id;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }
}
