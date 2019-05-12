package pl.snz.pubweb.review.module.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.review.module.history.model.ReviewHistory;

public interface ReviewHistoryRepository extends JpaRepository<ReviewHistory, Long>, JpaSpecificationExecutor<ReviewHistory> {}