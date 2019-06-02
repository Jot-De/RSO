package pl.snz.pubweb.pub.module.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.tag.model.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    Optional<Tag> findByName(String name);
    default Tag findOrThrow(Long id) {
        return this.findById(id).orElseThrow(NotFoundException.ofMessage("tag.not.found", "id", id));
    }


}
