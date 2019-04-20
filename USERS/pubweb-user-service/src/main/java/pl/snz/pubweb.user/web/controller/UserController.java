package pl.snz.pubweb.user.web.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.snz.pubweb.user.dto.user.*;
import pl.snz.pubweb.user.dto.user.friend.UserFriendshipDto;
import pl.snz.pubweb.user.exception.BadRequestException;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.mapper.UserMapper;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.presentation.FriendshipPresentationService;
import pl.snz.pubweb.user.presentation.UserPresentationService;
import pl.snz.pubweb.user.repo.UserRepository;
import pl.snz.pubweb.user.service.SecurityService;
import pl.snz.pubweb.user.service.user.FriendService;
import pl.snz.pubweb.user.service.user.UserSoftDeleteService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserRepository userRepository;
    private final UserPresentationService userPresentationService;
    private final UserMapper userMapper;
    private final SecurityService securityService;
    private final FriendService friendshipService;
    private final FriendshipPresentationService friendshipPresentationService;
    private final UserSoftDeleteService userSoftDeleteService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByLogin(signUpRequest.getLogin()))
            throw BadRequestException.field("login", "login.in.use");
        if(userRepository.existsByEmail(signUpRequest.getEmail()))
            throw BadRequestException.field("email", "email.in.use");
        if(userRepository.existsByDisplayName(signUpRequest.getDisplayName()))
            throw BadRequestException.field("displayName", "display.name.in.use");

        User user = userMapper.fromSignUpRequest(signUpRequest);
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
    public List<GetUserResponse> getUsers(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        return userRepository.findAll(PageRequest.of(page, size)).stream()
                .map(userPresentationService::toGetUserResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @NonNull Long id) {
            userSoftDeleteService.performSoftDelete(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/personalInformation")
    public GetUserResponse addUserPersonalInformation(@PathVariable Long id, @RequestBody @Valid UserPersonalInfoDto userPersonalInfoDto) {
        securityService.requireSelf(id);
        final User user = userRepository.findById(id).orElseThrow(NotFoundException.userById(id));
        user.getUserPersonalInformation().setFirstName(userPersonalInfoDto.getFirstName());
        user.getUserPersonalInformation().setSurname(userPersonalInfoDto.getSurname());
        user.getUserPersonalInformation().setCity(userPersonalInfoDto.getCity());
        return userPresentationService.toGetUserResponse(userRepository.save(user));
    }

    @PutMapping("{id}/displaySettings")
    public GetUserResponse updateDisplaySettings(@PathVariable Long id, @RequestBody @Valid UserDisplaySettingsDto userDisplaySettingsDto) {
        securityService.requireSelf(id);
        final User user = userRepository.findById(id).orElseThrow(NotFoundException.userById(id));
        user.getUserDisplaySettings().setNameDisplayLevel(userDisplaySettingsDto.getNameDisplayLevel());
        user.getUserDisplaySettings().setSurnameDisplayLevel(userDisplaySettingsDto.getSurnameDisplayLevel());
        user.getUserDisplaySettings().setCityDisplayLevel(userDisplaySettingsDto.getCityDisplayLevel());
        return userPresentationService.toGetUserResponse(userRepository.save(user));
    }

    @GetMapping("{id}/friends")
    public List<UserFriendshipDto> getUserFriends(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendshipService.getForUser(id).stream().map(f -> friendshipPresentationService.friendshipDto(f, id)).collect(Collectors.toList());
    }



}
