package pl.snz.pubweb.user.module.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.role.dto.RoleInfo;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePresentationService {

    public RoleInfo toRoleInfo(Role role) {
        return new RoleInfo(role.getId(), role.getName());
    }

}
