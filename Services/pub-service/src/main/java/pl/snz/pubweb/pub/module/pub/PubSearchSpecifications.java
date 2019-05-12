package pl.snz.pubweb.pub.module.pub;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.pub.module.common.data.Address_;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.pub.model.Pub_;
import pl.snz.pubweb.pub.module.tag.model.Tag;
import pl.snz.pubweb.pub.module.tag.model.Tag_;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.List;

@Component
public class PubSearchSpecifications {

    public Specification<Pub> getSearchSpec(@Nullable String name, @Nullable String city, @Nullable List<Long> tags) {
        return (r,q,cb) -> {
          Predicate result = JpaPredicates.truth(cb);
          result = name == null ? result : cb.and(cb.equal(r.get(Pub_.name), name));
          result = city == null ? result : cb.and(cb.equal(r.get(Pub_.address).get(Address_.city), city));

          if(tags != null && !tags.isEmpty()) {
              final SetJoin<Pub, Tag> tagJoin = r.join(Pub_.tags);
              result = cb.and(tagJoin.get(Tag_.id).in(tags));
          }
          return result;
        };
    }


}
