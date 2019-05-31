package pl.snz.pubweb.commons.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

public class JpaPredicates {
    public static <T> Predicate truth(CriteriaBuilder cb) {
        return cb.equal(cb.literal(1), 1);
    }
    public static <T> Predicate likeIgnoreCase(CriteriaBuilder cb, Path<String> path, String val) {
        return cb.like(cb.upper(path), "%" + val.toUpperCase() + "%" );
    }
}
