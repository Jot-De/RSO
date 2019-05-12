package pl.snz.pubweb.pub.module.common.data;

import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "address")
@Entity
public class Address extends IdentifiableEntity {

    @Column
    private String city;

    @Column(length = 100)
    private String street;

    @Column(length = 10)
    private String number;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
