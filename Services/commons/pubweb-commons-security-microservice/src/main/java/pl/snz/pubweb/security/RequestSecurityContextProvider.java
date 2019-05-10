package pl.snz.pubweb.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.security.user.UserPrincipal;

import java.util.Optional;

@Service
public class RequestSecurityContextProvider {

    public UserPrincipal getPrincipal() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .filter(p -> UserPrincipal.class.isAssignableFrom(p.getClass()))
                .map(UserPrincipal.class::cast)
                .orElseThrow(() -> new RuntimeException("unexpected.security.error"));
    }

    public long principalId() {
        return getPrincipal().getId();
    }
}
