package pl.snz.pubweb.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByLoginOrEmail(String login, String email);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    boolean existsByDisplayName(String displayName);
}
