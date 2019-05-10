package pl.snz.pubweb.pub.module.request.model;

import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.common.data.IdentifiableEntity;
import pl.snz.pubweb.pub.module.pub.model.Pub;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "pub_registration_request")
@Entity
public class PubRegistrationRequest extends IdentifiableEntity {

    @Column
    private Long userId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id")
    private Address address;

    @Column
    private String name;

    @Column
    private LocalDate added;

    @Column
    private LocalDate processed;

    @OneToOne
    @JoinColumn(nullable = true, name = "pub_id")
    private Pub pub;

    @Column
    @Enumerated(EnumType.STRING)
    private PubRegistrationStatus status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getAdded() {
        return added;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }

    public PubRegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(PubRegistrationStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getProcessed() {
        return processed;
    }

    public void setProcessed(LocalDate processed) {
        this.processed = processed;
    }

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }
}
