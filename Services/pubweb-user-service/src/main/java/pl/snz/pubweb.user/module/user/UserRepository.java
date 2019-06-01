package pl.snz.pubweb.user.module.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.user.model.User_;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

import static pl.snz.pubweb.commons.data.JpaPredicates.likeIgnoreCase;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByLoginOrEmail(String login, String email);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    boolean existsByDisplayName(String displayName);
    default Page<User> search(@Nullable String name, Pageable p) {
        Specification<User> spec = buildSearchSpec(name);
        return this.findAll(spec, p);
    }

    default Specification<User> buildSearchSpec(@Nullable String name) {
        return (r,q,cb) -> {
            Predicate result = JpaPredicates.truth(cb);
            result = name == null ? result : likeIgnoreCase(cb, r.get(User_.displayName), name);
//            result = firstName == null ? result : likeIgnoreCase(cb, r.get(User_.userPersonalInformation).get(UserPersonalInformation_.firstName), firstName);
//            result = surname == null ? result : likeIgnoreCase(cb, r.get(User_.userPersonalInformation).get(UserPersonalInformation_.surname), surname);
            return result;
        };
    }
}
