package pl.snz.pubweb.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.exception.AuthorizationException;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;
import pl.snz.pubweb.user.service.user.FriendService;

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

    private boolean areFriends(long principalId, long userId) {
        return friendshipService.areFriends(principalId, userId);
    }

    private void requireAdmin() {
        //TODO
    }

    public boolean isUserAdmin() {
        return true; //TODO
    }

}
