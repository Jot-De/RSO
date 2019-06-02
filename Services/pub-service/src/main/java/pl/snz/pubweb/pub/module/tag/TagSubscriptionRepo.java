package pl.snz.pubweb.pub.module.tag;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.pub.module.tag.model.Tag;
import pl.snz.pubweb.pub.module.tag.model.TagSubscription;
import pl.snz.pubweb.pub.module.tag.model.TagSubscription_;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface TagSubscriptionRepo extends JpaRepository<TagSubscription, Long>, JpaSpecificationExecutor<TagSubscription> {

    default List<Tag> findForUser(Long userId) {
        Specification<TagSubscription> spec = (r, q, cb) -> cb.equal(r.get(TagSubscription_.userId), userId);

        return findOne(spec).map(TagSubscription::getSubscriptions).orElseGet(Collections::emptyList);
    }

    default TagSubscription addTag(Long userId, Tag tag) {
        Specification<TagSubscription> spec = (r, q, cb) -> cb.equal(r.get(TagSubscription_.userId), userId);
        final Optional<TagSubscription> opt = findOne(spec);

        TagSubscription sub = opt.isPresent() ? opt.get() : createNew(userId);

        if (!sub.getSubscriptions().contains(tag)) {
            sub.getSubscriptions().add(tag);
        }

        return this.save(sub);

    }

    default TagSubscription removeTag(Long userId, Tag tag) {
        Specification<TagSubscription> spec = (r, q, cb) -> cb.equal(r.get(TagSubscription_.userId), userId);
        final Optional<TagSubscription> opt = findOne(spec);

        TagSubscription sub = opt.isPresent() ? opt.get() : createNew(userId);

        sub.getSubscriptions().removeIf(t -> t.equals(tag));

        return this.save(sub);
    }

    default TagSubscription createNew(Long userId) {
        TagSubscription sub = new TagSubscription();
        sub.setUserId(userId);
        return sub;
    }


}
