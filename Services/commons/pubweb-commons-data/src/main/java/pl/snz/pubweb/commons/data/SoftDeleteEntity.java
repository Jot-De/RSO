package pl.snz.pubweb.commons.data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SoftDeleteEntity extends IdentifiableEntity<Long> {
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
