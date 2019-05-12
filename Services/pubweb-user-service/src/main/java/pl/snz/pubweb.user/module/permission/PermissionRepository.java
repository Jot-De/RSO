package pl.snz.pubweb.user.module.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.snz.pubweb.user.module.permission.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
