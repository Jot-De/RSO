package pl.snz.pubweb.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.errors.exception.AuthorizationException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityService {

    private final RequestSecurityContextProvider contextProvider;

    public void requireSelf(Long requesterId) {
        if(!contextProvider.getPrincipal().getId().equals(requesterId))
            throw AuthorizationException.ownContextRequired();
    }

    public void requireSelfOrAdmin(Long requesterId) {

    }
}
