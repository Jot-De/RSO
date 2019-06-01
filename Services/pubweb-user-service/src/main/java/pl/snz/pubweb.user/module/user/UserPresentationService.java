package pl.snz.pubweb.user.module.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.user.module.permission_acceptance.dto.AcceptedPermission;
import pl.snz.pubweb.user.module.role.Role;
import pl.snz.pubweb.user.module.common.DisplayLevelAwareBuilder;
import pl.snz.pubweb.user.module.common.web.CurrentRequestUriProvider;
import pl.snz.pubweb.user.module.permission.service.PermissionPresentationService;
import pl.snz.pubweb.user.module.user.dto.GetUserResponse;
import pl.snz.pubweb.user.module.user.dto.RegistrationResponse;
import pl.snz.pubweb.user.module.user.dto.UDisplaySettings;
import pl.snz.pubweb.user.module.user.dto.UserPersonalInfoDto;
import pl.snz.pubweb.user.module.user.model.DisplayLevel;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.user.model.UserDisplaySettings;
import pl.snz.pubweb.user.module.user.model.UserPersonalInformation;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;
import pl.snz.pubweb.user.module.friend.FriendService;

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
        DisplayLevel provided = determineRequestIssuerDisplayLevel(user);

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

    private UserPersonalInfoDto personalInfo(User user, DisplayLevel provided) {
        UserDisplaySettings userDisplaySettings = user.getUserDisplaySettings();

        return DisplayLevelAwareBuilder.of(provided, user.getUserPersonalInformation(), UserPersonalInfoDto::new)
                .set(userDisplaySettings.getNameDisplayLevel(), UserPersonalInformation::getFirstName, UserPersonalInfoDto::setFirstName)
                .set(userDisplaySettings.getSurnameDisplayLevel(), UserPersonalInformation::getSurname, UserPersonalInfoDto::setSurname)
                .set(userDisplaySettings.getCityDisplayLevel(), UserPersonalInformation::getCity, UserPersonalInfoDto::setCity)
                .set(userDisplaySettings.getAboutMeDisplayLevel(), UserPersonalInformation::getAboutMe, UserPersonalInfoDto::setAboutMe)
                .build();
    }

    private DisplayLevel determineRequestIssuerDisplayLevel(User requestedUserData) {
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
