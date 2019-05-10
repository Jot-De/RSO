package pl.snz.pubweb.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.snz.pubweb.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
