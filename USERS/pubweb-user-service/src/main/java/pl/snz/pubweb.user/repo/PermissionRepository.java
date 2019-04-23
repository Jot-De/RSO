package pl.snz.pubweb.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.snz.pubweb.user.model.permission.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
