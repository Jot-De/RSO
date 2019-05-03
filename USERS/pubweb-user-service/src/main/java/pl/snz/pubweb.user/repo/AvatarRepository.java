package pl.snz.pubweb.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.snz.pubweb.user.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
