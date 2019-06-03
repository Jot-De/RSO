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
import java.time.LocalDate;
import java.util.List;

import static pl.snz.pubweb.commons.data.JpaPredicates.likeIgnoreCase;

@Component
public class PubSearchSpecifications {

    public Specification<Pub> getSearchSpec(@Nullable String name,
                                            @Nullable String city,
                                            @Nullable LocalDate addedAfter,
                                            @Nullable LocalDate addedBefore,
                                            @Nullable List<Long> tags) {
        return (r,q,cb) -> {
          Predicate result = JpaPredicates.truth(cb);
          result = name == null ? result : cb.and(result, likeIgnoreCase(cb, r.get(Pub_.name), name));
          result = city == null ? result : cb.and(result, likeIgnoreCase(cb, r.get(Pub_.address).get(Address_.city), city));
          result = addedAfter == null ? result : cb.and(result, cb.greaterThanOrEqualTo(r.get(Pub_.added), addedAfter));
          result = addedBefore == null ? result : cb.and(result, cb.lessThanOrEqualTo(r.get(Pub_.added), addedBefore));

          if(tags != null && !tags.isEmpty()) {
              final SetJoin<Pub, Tag> tagJoin = r.join(Pub_.tags);
              result = cb.and(tagJoin.get(Tag_.id).in(tags));
          }
          return result;
        };
    }


}
