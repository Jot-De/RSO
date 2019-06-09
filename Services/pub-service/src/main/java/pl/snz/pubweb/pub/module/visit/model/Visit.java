package pl.snz.pubweb.pub.module.visit.model;

import lombok.Data;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.pub.model.Pub;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "visit")
public class Visit extends IdentifiableEntity<Long> {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = true)
    private LocalDate visited;

    @JoinColumn(name = "pub_id")
    @ManyToOne
    private Pub pub;

    @Column
    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visit visit = (Visit) o;

        return id != null ? id.equals(visit.id) : visit.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
