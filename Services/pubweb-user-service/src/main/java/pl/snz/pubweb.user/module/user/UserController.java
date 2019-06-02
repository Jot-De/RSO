package pl.snz.pubweb.user.module.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.snz.pubweb.commons.dto.Base64PictureDto;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.user.container.aop.RequiresPermission;
import pl.snz.pubweb.user.module.avatar.Avatar;
import pl.snz.pubweb.user.module.avatar.AvatarManagement;
import pl.snz.pubweb.user.module.avatar.AvatarService;
import pl.snz.pubweb.user.module.friend.FriendService;
import pl.snz.pubweb.user.module.friend.FriendshipPresentationService;
import pl.snz.pubweb.user.module.friend.model.FriendshipInfo;
import pl.snz.pubweb.user.module.permission.PermissionKeys;
import pl.snz.pubweb.user.module.permission.PermissionRepository;
import pl.snz.pubweb.user.module.permission.model.Permission;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;
import pl.snz.pubweb.user.module.user.dto.*;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.security.SecurityService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements AvatarManagement {

    private final UserRepository userRepository;
    private final UserPresentationService userPresentationService;
    private final UserMapper userMapper;
    private final SecurityService securityService;
    private final FriendService friendshipService;
    private final FriendshipPresentationService friendshipPresentationService;
    private final AvatarService avatarService;
    private final PermissionRepository permissionRepository;

    @PostMapping
    public ResponseEntity<RegistrationResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getLogin()))
            throw BadRequestException.field("login", "login.in.use");
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            throw BadRequestException.field("email", "email.in.use");
        if (userRepository.existsByDisplayName(signUpRequest.getDisplayName()))
            throw BadRequestException.field("displayName", "display.name.in.use");

        User user = userMapper.fromSignUpRequest(signUpRequest);
        user = userRepository.save(user);

        if (signUpRequest.getInformationProcessingAcceptance()) {
            final Permission perm = permissionRepository.findByKeyOrThrow(PermissionKeys.INFORMATION_PROCESSING);
            user.getAcceptedPermissions().add(new UserPermissionAcceptance(user, perm, LocalDate.now().plusYears(10)));
        }

        if (signUpRequest.getProfilingAcceptance()) {
            final Permission perm = permissionRepository.findByKeyOrThrow(PermissionKeys.PROFILING_ACCEPTANCE);
            user.getAcceptedPermissions().add(new UserPermissionAcceptance(user, perm, LocalDate.now().plusYears(10)));
        }

        user = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(userPresentationService.toRegistrationResponse(user));
    }

    @GetMapping("{id}")
    public GetUserResponse getUserInfo(@PathVariable @NotNull Long id) {
        return Optional.of(id).flatMap(userRepository::findById).map(userPresentationService::toGetUserResponse).orElseThrow(NotFoundException.userById(id));
    }

    @GetMapping
    public Page<GetUserResponse> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "20") int size,
                                          @RequestParam(name = "name", required = false) String name) {
        return userRepository.search(name, PageRequest.of(page, size))
                .map(userPresentationService::toGetUserResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @NonNull Long id) {
        securityService.requireSelfOdAdmin(id);
        userRepository.findById(id).ifPresent(userRepository::delete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("{id}/personalInformation")
    @RequiresPermission(permissionKeys = PermissionKeys.INFORMATION_PROCESSING)
    public GetUserResponse addUserPersonalInformation(@PathVariable Long id, @RequestBody @Valid UserPersonalInfoDto userPersonalInfoDto) {
        securityService.requireSelf(id);
        final User user = userRepository.findById(id).orElseThrow(NotFoundException.userById(id));
        user.getUserPersonalInformation().setFirstName(userPersonalInfoDto.getFirstName());
        user.getUserPersonalInformation().setSurname(userPersonalInfoDto.getSurname());
        user.getUserPersonalInformation().setCity(userPersonalInfoDto.getCity());
        user.getUserPersonalInformation().setAboutMe(userPersonalInfoDto.getAboutMe());
        return userPresentationService.toGetUserResponse(userRepository.save(user));
    }

    @Transactional
    @PutMapping("{id}/displaySettings")
    public GetUserResponse updateDisplaySettings(@PathVariable Long id, @RequestBody @Valid UDisplaySettings uDisplaySettings) {
        securityService.requireSelf(id);
        final User user = userRepository.findById(id).orElseThrow(NotFoundException.userById(id));
        user.getUserDisplaySettings().setNameDisplayLevel(uDisplaySettings.getNameDisplayLevel());
        user.getUserDisplaySettings().setSurnameDisplayLevel(uDisplaySettings.getSurnameDisplayLevel());
        user.getUserDisplaySettings().setCityDisplayLevel(uDisplaySettings.getCityDisplayLevel());
        user.getUserDisplaySettings().setAboutMeDisplayLevel(uDisplaySettings.getAboutMeDisplayLevel());
        return userPresentationService.toGetUserResponse(userRepository.save(user));
    }

    @GetMapping("{id}/friends")
    public List<FriendshipInfo> getUserFriends(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendshipService.getForUser(id).stream().map(f -> friendshipPresentationService.friendshipDto(f, id)).collect(Collectors.toList());
    }


    @GetMapping("{userId}/avatar")
    public Base64PictureDto getAvatarForUser(@PathVariable Long userId) {
        Avatar avatar = avatarService.getForUser(userId);
        return Base64PictureDto.builder()
                .id(avatar.getId())
                .base64Picture(Base64.getMimeEncoder().encodeToString(avatar.getBytes()))
                .dataFormat(avatar.getDataFormat())
                .build();
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST}, path = "{userId}/avatar")
    public ResponseEntity<?> addAvatarForUser(@PathVariable Long userId, @RequestBody Base64PictureDto dto) {
        securityService.requireSelf(userId);
        avatarService.addForUser(dto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}/avatar")
    public ResponseEntity<?> deleteAvatar(@PathVariable Long userId) {
        securityService.requireSelf(userId);
        avatarService.deleteForUser(userId);
        return ResponseEntity.noContent().build();
    }
}
