package pl.snz.pubweb.user.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.user.dto.role.RoleInfo;
import pl.snz.pubweb.user.dto.role.RolesAssignmentRequest;
import pl.snz.pubweb.user.exception.BadRequestException;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.model.Role;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.presentation.RolePresenter;
import pl.snz.pubweb.user.repo.RoleRepository;
import pl.snz.pubweb.user.repo.UserRepository;
import pl.snz.pubweb.user.util.Mappers;
import pl.snz.pubweb.user.util.Predicates;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleRepository roleRepository;
    private final RolePresenter rolePresenter;
    private final UserRepository userRepository;

    @GetMapping("/roles")
    public List<RoleInfo> readRoles() {
        return Mappers.list(rolePresenter::toRoleInfo).apply(roleRepository.findAll());
    }

    @PostMapping("/users/{userId}/roles")
    public List<RoleInfo> assignRolesToUser(@PathVariable Long userId, @RequestBody RolesAssignmentRequest rolesAssignmentRequest) {
        List<Role> roles =
                Mappers.list(
                        id -> roleRepository.findById(id).orElseThrow(NotFoundException.ofMessage("role.not.found", "id", id)),
                        rolesAssignmentRequest.getRoles()
                );
        roles.stream().filter(Predicates.not(Role::isAssignable)).findAny().ifPresent((role) -> {throw BadRequestException.field("id", "role.not.assignable");});
        final User user = userRepository.findById(userId).orElseThrow(NotFoundException.userById(userId));

        return Mappers.list(rolePresenter::toRoleInfo, userRepository.save(user).getRoles());
    }




}
