package pl.snz.pubweb.pub.module.common.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor @AllArgsConstructor
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
