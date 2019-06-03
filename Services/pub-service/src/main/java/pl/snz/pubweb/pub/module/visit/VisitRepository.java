package pl.snz.pubweb.pub.module.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.pub.module.visit.model.Visit;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit,Long>, JpaSpecificationExecutor<Visit> {
    Optional<Visit> findByUserIdAndPubId(Long userId, Long pubId);
    List<Visit> findByUserId(Long userId);
}