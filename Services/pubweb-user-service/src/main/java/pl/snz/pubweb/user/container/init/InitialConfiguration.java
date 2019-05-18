package pl.snz.pubweb.user.container.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.user.module.role.Role;
import pl.snz.pubweb.user.module.role.RoleRepository;
import pl.snz.pubweb.user.module.role.Roles;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.user.model.User;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialConfiguration {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public void run() {
        Role adminRole = roleRepository.findByName(Roles.ADMIN)
                .orElseGet(() -> createDefaultRole(Roles.ADMIN, false));

        if(!userRepository.findByLoginOrEmail("admin", null).isPresent()) {
            log.info("Creating admin user");
            createAdminAccount(adminRole);
        }

    }

    private Role createDefaultRole(String name, boolean assignable) {
        log.info("Creating role: " + name);
        Role role = new Role();
        role.setName(name);
        role.setAssignable(assignable);
        role.setUpdatable(false);
        return roleRepository.save(role);
    }

    private void createAdminAccount(Role adminRole) {
        User admin = new User();
        admin.setEmail("admin@wielkaPolska.pl");
        admin.setDisplayName("admin");
        admin.setLogin("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }
}
