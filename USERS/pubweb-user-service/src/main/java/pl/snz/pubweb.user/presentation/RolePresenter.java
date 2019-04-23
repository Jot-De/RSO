package pl.snz.pubweb.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.role.RoleInfo;
import pl.snz.pubweb.user.model.Role;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePresenter {

    public RoleInfo toRoleInfo(Role role) {
        return new RoleInfo(role.getId(), role.getName());
    }

}
