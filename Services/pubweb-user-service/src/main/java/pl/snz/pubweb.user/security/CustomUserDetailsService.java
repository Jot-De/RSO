package pl.snz.pubweb.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.repo.UserRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository.findByLoginOrEmail(usernameOrEmail, usernameOrEmail)
                .map(UserPrincipal::create)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        return userRepository.findById(id).map(UserPrincipal::create).orElseThrow(NotFoundException.ofMessage("user", "id", id));
    }
}