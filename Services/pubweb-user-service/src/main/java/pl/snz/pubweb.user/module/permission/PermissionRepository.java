package pl.snz.pubweb.user.module.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.user.module.permission.model.Permission;
import pl.snz.pubweb.user.module.permission.model.Permission_;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    default Permission findByKeyOrThrow(String key) {
        return this.findOne((r,q,cb) -> cb.equal(r.get(Permission_.permissionKey), key))
                .orElseThrow(NotFoundException.ofMessage("permission.not.found", "key", key));
    }
}
