package pl.snz.pubweb.pub.module.common.data;

import lombok.Data;
import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "address")
@Entity
public class Address extends IdentifiableEntity<Long> {

    @Column
    private String city;

    @Column(length = 100)
    private String street;

    @Column(length = 10)
    private String number;

}
