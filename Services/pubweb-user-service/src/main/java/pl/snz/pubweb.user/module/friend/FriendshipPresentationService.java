package pl.snz.pubweb.user.module.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequestInfo;
import pl.snz.pubweb.user.module.friend.model.FriendshipInfo;
import pl.snz.pubweb.user.module.friend.model.Friendship;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequest;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendshipPresentationService {

    public FriendshipInfo friendshipDto(Friendship friendship, Long requesterId) {
        return FriendshipInfo.builder()
                .since(friendship.getSince())
                .user(requesterId)
                .friend(friendship.getUser().getId().equals(requesterId) ? friendship.getUser2().getId() : friendship.getUser().getId())
                .build();
    }

    public FriendshipRequestInfo requestDto(FriendshipRequest friendshipRequest) {
        return FriendshipRequestInfo.builder()
                .id(friendshipRequest.getId())
                .requesterId(friendshipRequest.getRequester().getId())
                .targetId(friendshipRequest.getTarget().getId())
                .received(friendshipRequest.getReceived())
                .message(friendshipRequest.getMessage())
                .build();
    }


}
