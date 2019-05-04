package pl.snz.pubweb.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.snz.pubweb.user.util.Mappers;
import pl.snz.pubweb.user.dto.user.*;
import pl.snz.pubweb.user.model.Role;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.model.UserPersonalInformation;
import pl.snz.pubweb.user.model.display.DisplayLevel;
import pl.snz.pubweb.user.model.display.UserDisplaySettings;
import pl.snz.pubweb.user.presentation.builder.DisplayLevelAwareBuilder;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;
import pl.snz.pubweb.user.service.user.FriendService;
import pl.snz.pubweb.user.util.Nulls;
import pl.snz.pubweb.user.web.CurrentRequestUriProvider;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPresentationService {

    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final PermissionPresentationService permissionPresentationService;
    private final FriendService friendService;
    private final CurrentRequestUriProvider currentRequestUriProvider;

    public RegistrationResponse toRegistrationResponse(User user) {
        return RegistrationResponse.builder()
                .displayName(user.getDisplayName())
                .build();
    }

    public GetUserResponse toGetUserResponse(User user) {
        DisplayLevel provided = getDisplayLevelForRequest(user);

        return DisplayLevelAwareBuilder.of(provided, user, GetUserResponse::new)
                .set(DisplayLevel.ALL, User::getId, GetUserResponse::setId)
                .set(DisplayLevel.ALL, User::getDisplayName, GetUserResponse::setDisplayName)
                .set(DisplayLevel.ME_ONLY, User::getEmail, GetUserResponse::setEmail)
                .set(DisplayLevel.ME_ONLY, User::getLogin, GetUserResponse::setLogin)
                .set(user.getUserDisplaySettings().getLowest(), this::personalInfo, GetUserResponse::setPersonalInformation)
                .set(DisplayLevel.ME_ONLY, u -> userDisplaySettingsDto(user.getUserDisplaySettings()), GetUserResponse::setDisplaySettings)
                .set(DisplayLevel.ME_ONLY, this::permissions, GetUserResponse::setAcceptedPermissions)
                .set(DisplayLevel.ME_ONLY, u -> u.getRoles().stream().map(Role::getName).collect(Collectors.toList()), GetUserResponse::setRoles)
                .set(DisplayLevel.ME_ONLY, u -> currentRequestUriProvider.userAvatar(u.getId()), GetUserResponse::setAvatarUri)
                .build();
    }

    private UserPersonalInfoDto personalInfo(User user,DisplayLevel provided) {
        UserDisplaySettings userDisplaySettings = user.getUserDisplaySettings();

        return DisplayLevelAwareBuilder.of(provided, user.getUserPersonalInformation(), UserPersonalInfoDto::new)
                .set(userDisplaySettings.getNameDisplayLevel(), UserPersonalInformation::getFirstName, UserPersonalInfoDto::setFirstName)
                .set(userDisplaySettings.getSurnameDisplayLevel(), UserPersonalInformation::getSurname, UserPersonalInfoDto::setSurname)
                .set(userDisplaySettings.getCityDisplayLevel(), UserPersonalInformation::getCity, UserPersonalInfoDto::setCity)
                .set(userDisplaySettings.getAboutMeDisplayLevel(), UserPersonalInformation::getAboutMe, UserPersonalInfoDto::setAboutMe)
                .build();
    }

    private DisplayLevel getDisplayLevelForRequest(User requestedUserData) {
        final long requestedUserId = requestedUserData.getId();
        final long principalId = requestSecurityContextProvider.getPrincipal().getId();
        if(requestedUserId == principalId)
            return DisplayLevel.ME_ONLY;
        else if(areFriends(requestedUserId, principalId))
            return DisplayLevel.FRIENDS;
        else
            return DisplayLevel.ALL;
    }

    private UDisplaySettings userDisplaySettingsDto(UserDisplaySettings settings) {
        return UDisplaySettings.builder()
                .cityDisplayLevel(settings.getCityDisplayLevel())
                .nameDisplayLevel(settings.getNameDisplayLevel())
                .surnameDisplayLevel(settings.getSurnameDisplayLevel())
                .aboutMeDisplayLevel(settings.getAboutMeDisplayLevel())
                .build();
    }

    private List<AcceptedPermission> permissions(User user) {
        return Mappers.list(permissionPresentationService::acceptedPermission).apply(user.getAcceptedPermissions());
    }

    private boolean areFriends(long requestedUserId, long principalId) {
        return friendService.areFriends(requestedUserId, principalId);
    }



}
