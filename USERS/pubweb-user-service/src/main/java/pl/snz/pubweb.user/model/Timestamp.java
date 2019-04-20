package pl.snz.pubweb.user.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Embeddable
public class Timestamp {

    @Column(name = "created_at")
    private Instant created;

    @Column(name = "updated_at")
    private Instant updated;

    @PrePersist
    public void prePersist() {
        this.created = updated = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = Instant.now();
    }
}
