package pl.snz.pubweb.user.module.permission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.permission.PermissionKeys;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.user.model.UserPersonalInformation;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionRevocationHandler {

    private final UserRepository userRepository;

    public void handleRevoke(UserPermissionAcceptance accpeptance) {
        String permKey = accpeptance.getPermission().getPermissionKey();
        if(PermissionKeys.INFORMATION_PROCESSING.equals(permKey)) {
            handleInformationProcessingRevoke(accpeptance);
        }
    }

    public void handleInformationProcessingRevoke(UserPermissionAcceptance acceptance) {
        User user = acceptance.getUser();
        log.info("Handling information processing revocation for user {} user personal data will be deleted", user.getId());
        user.setUserPersonalInformation(UserPersonalInformation.none());
        userRepository.save(user);
    }

}
