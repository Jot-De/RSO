package pl.snz.pubweb.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomJwtUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        throw new RuntimeException();
//        return userRepository.findByLoginOrEmail(usernameOrEmail, usernameOrEmail)
//                .map(UserPrincipal::create)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
    }

    public UserDetails loadUserById(Long id) {
        throw new RuntimeException();
//        return userRepository.findById(id).map(UserPrincipal::create).orElseThrow(NotFoundException.ofMessage("user", "id", id));
    }
}