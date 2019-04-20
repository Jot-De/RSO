package pl.snz.pubweb.user.model;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class IdentifiableEntity<I> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected I id;

}
