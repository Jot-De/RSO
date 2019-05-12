package pl.snz.pubweb.user.module.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.commons.util.Predicates;
import pl.snz.pubweb.user.module.role.dto.RoleInfo;
import pl.snz.pubweb.user.module.role.dto.RolesAssignmentRequest;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.security.AdminApi;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleRepository roleRepository;
    private final RolePresentationService rolePresentationService;
    private final UserRepository userRepository;

    @AdminApi
    @GetMapping("/roles")
    public List<RoleInfo> readRoles() {
        return Mappers.list(rolePresentationService::toRoleInfo).apply(roleRepository.findAll());
    }

    @AdminApi
    @PostMapping("/users/{userId}/roles")
    public List<RoleInfo> assignRolesToUser(@PathVariable Long userId, @RequestBody RolesAssignmentRequest rolesAssignmentRequest) {
        List<Role> roles =
                Mappers.list(
                        id -> roleRepository.findById(id).orElseThrow(NotFoundException.ofMessage("role.not.found", "id", id)),
                        rolesAssignmentRequest.getRoles()
                );
        roles.stream().filter(Predicates.not(Role::isAssignable)).findAny().ifPresent((role) -> {throw BadRequestException.field("id", "role.not.assignable");});
        final User user = userRepository.findById(userId).orElseThrow(NotFoundException.userById(userId));

        return Mappers.list(rolePresentationService::toRoleInfo, userRepository.save(user).getRoles());
    }




}
