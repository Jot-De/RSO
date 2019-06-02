package pl.snz.pubweb.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.errors.exception.AuthorizationException;
import pl.snz.pubweb.user.module.friend.FriendService;
import pl.snz.pubweb.user.module.role.Roles;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityService {
    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final FriendService friendshipService;

    public void requireSelf(long userId) throws AuthorizationException {
        if(!isSelf(principalId(), userId))
            throw AuthorizationException.ownContextRequired();
    }

    private long principalId(){
        return requestSecurityContextProvider.getPrincipal().getId().longValue();
    }

    private boolean isSelf(long principalId ,long userId) {
        return principalId == userId;
    }

    public void requireFriend(Long userId) throws AuthorizationException {
        final long principalId = principalId();
        if(!(isSelf(principalId, userId) && !areFriends(principalId, userId)))
            throw AuthorizationException.friendContextRequired();
    }

    public void requireSelfOdAdmin(Long userId) {
        if(!isUserAdmin())
            requireSelf(userId);
    }

    private boolean isUserAdmin() {
        final Collection<? extends GrantedAuthority> authorities = requestSecurityContextProvider.getPrincipal().getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(ga -> ga.equals(Roles.ADMIN));
    }

    private boolean areFriends(long principalId, long userId) {
        return friendshipService.areFriends(principalId, userId);
    }

}
