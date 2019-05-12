package pl.snz.pubweb.user.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.errors.exception.InternalServerErrorException;

import java.util.Optional;

@Service
public class RequestSecurityContextProvider {

    public UserPrincipal getPrincipal() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .filter(p -> UserPrincipal.class.isAssignableFrom(p.getClass()))
                .map(UserPrincipal.class::cast)
                .orElseThrow(() -> InternalServerErrorException.ofMessage("unexpected.security.error"));
    }

    public long principalId() {
        return getPrincipal().getId();
    }
}
