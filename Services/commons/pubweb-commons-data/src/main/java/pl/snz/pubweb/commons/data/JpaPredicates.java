package pl.snz.pubweb.commons.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class JpaPredicates {
    public static <T> Predicate truth(CriteriaBuilder cb) {
        return cb.equal(cb.literal(1), 1);
    }
}
