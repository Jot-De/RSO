package pl.snz.pubweb.user.module.avatar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
