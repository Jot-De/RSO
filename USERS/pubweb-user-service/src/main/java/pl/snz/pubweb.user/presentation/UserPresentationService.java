package pl.snz.pubweb.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.user.GetUserResponse;
import pl.snz.pubweb.user.dto.user.RegistrationResponse;
import pl.snz.pubweb.user.dto.user.UserDisplaySettingsDto;
import pl.snz.pubweb.user.dto.user.UserPersonalInfoDto;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.model.UserPersonalInformation;
import pl.snz.pubweb.user.model.display.DisplayLevel;
import pl.snz.pubweb.user.model.display.UserDisplaySettings;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPresentationService {

    private final RequestSecurityContextProvider requestSecurityContextProvider;

    public RegistrationResponse toRegistrationResponse(User user) {
        return RegistrationResponse.builder()
                .displayName(user.getDisplayName())
                .build();
    }

    public GetUserResponse toGetUserResponse(User user) {
        DisplayLevel dl = getDisplayLevelForRequest(user);
        return GetUserResponse.builder()
                .displayName(user.getDisplayName())
//                .aboutMe(user.getAboutMe())
                .email(nullIfInsufficentDisplayPrivileges(dl, DisplayLevel.ME_ONLY, () -> user.getEmail()))
                .login(nullIfInsufficentDisplayPrivileges(dl, DisplayLevel.ME_ONLY, () -> user.getLogin()))
                .displaySettings(nullIfInsufficentDisplayPrivileges(dl, DisplayLevel.ME_ONLY, () -> userDisplaySettingsDto(user.getUserDisplaySettings())))
                .personalInformation(personalInfo(dl, user.getUserDisplaySettings(), user.getUserPersonalInformation()))
                .build();

    }

    private UserPersonalInfoDto personalInfo(DisplayLevel provided, UserDisplaySettings userDisplaySettings, UserPersonalInformation userPersonalInformation) {
        return UserPersonalInfoDto.builder()
                .city(nullIfInsufficentDisplayPrivileges(provided, userDisplaySettings.getCityDisplayLevel(), () -> userPersonalInformation.getCity()))
                .firstName(nullIfInsufficentDisplayPrivileges(provided, userDisplaySettings.getNameDisplayLevel(), () -> userPersonalInformation.getFirstName()))
                .surname(nullIfInsufficentDisplayPrivileges(provided, userDisplaySettings.getSurnameDisplayLevel(), () -> userPersonalInformation.getSurname()))
                .build();
    }

    private <R> R nullIfInsufficentDisplayPrivileges(DisplayLevel provided, DisplayLevel required, Supplier<R> value) {
        return provided.getConfidenceLevel() >= required.getConfidenceLevel() ?  value.get() : null;
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

    private UserDisplaySettingsDto userDisplaySettingsDto(UserDisplaySettings settings) {
        return UserDisplaySettingsDto.builder()
                .cityDisplayLevel(settings.getCityDisplayLevel())
                .nameDisplayLevel(settings.getNameDisplayLevel())
                .surnameDisplayLevel(settings.getSurnameDisplayLevel())
                .build();
    }

    private boolean areFriends(long requestedUserId, long principalId) {
        return false; //TODO
    }

}
