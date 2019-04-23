package pl.snz.pubweb.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.user.util.Mappers;
import pl.snz.pubweb.user.dto.permission.PermissionSummary;
import pl.snz.pubweb.user.dto.user.AcceptedPermission;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.model.permission.Permission;
import pl.snz.pubweb.user.model.permission.UserPermissionAcceptance;
import pl.snz.pubweb.user.presentation.PermissionPresentationService;
import pl.snz.pubweb.user.repo.PermissionAcceptanceRepository;
import pl.snz.pubweb.user.repo.PermissionRepository;
import pl.snz.pubweb.user.repo.UserRepository;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionController {

    private final PermissionRepository permissionRepository;
    private final PermissionAcceptanceRepository permissionAcceptanceRepository;
    private final PermissionPresentationService permissionPresentationService;
    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final UserRepository userRepository;

    @GetMapping
    public Page<PermissionSummary> getAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return permissionRepository.findAll(PageRequest.of(page, size)).map(permissionPresentationService::toSummary);
    }

    @GetMapping("{id}")
    public PermissionSummary getOne(@PathVariable  Long id) {
        return permissionRepository.findById(id)
                .map(permissionPresentationService::toSummary)
                .orElseThrow(NotFoundException.ofMessage("permission.not.found", "id", id));
    }

    @PostMapping("/{id}/accept")
    public List<AcceptedPermission> confirmPermission(@PathVariable Long id) {
        final Long userId = requestSecurityContextProvider.getPrincipal().getId();

        final Optional<UserPermissionAcceptance> opt = permissionAcceptanceRepository.findByUserAndPerm(userId, id);
        if(!opt.isPresent()) {
            Permission perm = permissionRepository.findById(id).orElseThrow(NotFoundException.ofMessage("permission.not.found", "id", id));
            User user = userRepository.findById(userId).orElseThrow(NotFoundException.userById(userId));
            permissionAcceptanceRepository.save(new UserPermissionAcceptance(user, perm, LocalDate.now().plusYears(10)));
        }
        return Mappers.list(permissionPresentationService::acceptedPermission).apply(permissionAcceptanceRepository.findByUser(userId));

    }

    @PostMapping("{id}/revoke")
    public List<AcceptedPermission> revokePermission(@PathVariable Long id) {
        final Long userId = requestSecurityContextProvider.getPrincipal().getId();
        permissionAcceptanceRepository.findByUserAndPerm(userId, id).ifPresent(permissionAcceptanceRepository::delete);
        return Mappers.list(permissionPresentationService::acceptedPermission).apply(permissionAcceptanceRepository.findByUser(userId));
    }
}
