package pl.snz.pubweb.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.user.friend.FriendshipRequestInfo;
import pl.snz.pubweb.user.dto.user.friend.FriendshipInfo;
import pl.snz.pubweb.user.model.friend.Friendship;
import pl.snz.pubweb.user.model.friend.FriendshipRequest;

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
