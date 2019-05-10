package pl.snz.pubweb.pub.module.tag;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.pub.module.common.data.JpaPredicates;
import pl.snz.pubweb.pub.module.tag.model.Tag;
import pl.snz.pubweb.pub.module.tag.model.Tag_;

import javax.persistence.criteria.Predicate;

@Component
public class TagSearchSpecs {

    public Specification<Tag> searchSpec(@Nullable String name) {
        return (r,q,cb) -> {
            final Predicate result = JpaPredicates.truth(cb);
            return name == null ? result : cb.and(result, cb.like(r.get(Tag_.name), name));
        };
    }
}
