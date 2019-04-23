package pl.snz.pubweb.user.development;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.model.Role;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.model.UserPersonalInformation;
import pl.snz.pubweb.user.model.display.UserDisplaySettings;
import pl.snz.pubweb.user.repo.RoleRepository;
import pl.snz.pubweb.user.repo.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DevelopmentConfigurer {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void configureDevEnvinronment() {
        Role adminRole = prepareAdminRole();
        prepareAdminUser(adminRole);

    }

    private void prepareAdminUser(Role adminRole) {
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
        admin.setDisplayName("Administrator systemu");
        admin.setAboutMe("Jestem adminem, nie mam innych zainteresowań");
        admin.setEmail("admin@gmail.com");
        admin.setRoles(Collections.singleton(adminRole));
        admin.setUserPersonalInformation(UserPersonalInformation.none());
        admin.setUserDisplaySettings(UserDisplaySettings.allPrivate());
        admin.setAcceptedPermissions(Collections.emptySet());
        userRepository.save(admin);
    }

    private Role prepareAdminRole() {
        Role role = new Role();
        role.setName("ADMIN");
        role.setUpdatable(false);
        return roleRepository.save(role);
    }

}
