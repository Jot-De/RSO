package pl.snz.pubweb.pub.module.pub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.pub.model.Pub;

public interface PubRepository extends JpaRepository<Pub, Long>, JpaSpecificationExecutor<Pub> {

    default Pub findOrThrow(Long id) {
        return this.findById(id).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
    }
}
