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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifiableEntity<?> that = (IdentifiableEntity<?>) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
