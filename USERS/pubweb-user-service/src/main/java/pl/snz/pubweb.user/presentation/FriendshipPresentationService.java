package pl.snz.pubweb.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.user.friend.FriendshipRequestDto;
import pl.snz.pubweb.user.dto.user.friend.UserFriendshipDto;
import pl.snz.pubweb.user.model.friend.Friendship;
import pl.snz.pubweb.user.model.friend.FriendshipRequest;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendshipPresentationService {

    public UserFriendshipDto friendshipDto(Friendship friendship, Long requesterId) {
        return UserFriendshipDto.builder()
                .since(friendship.getSince())
                .user(requesterId)
                .friend(friendship.getUser().getId().equals(requesterId) ? friendship.getUser2().getId() : friendship.getUser().getId())
                .build();
    }

    public FriendshipRequestDto requestDto(FriendshipRequest friendshipRequest) {
        return FriendshipRequestDto.builder()
                .id(friendshipRequest.getId())
                .requesterId(friendshipRequest.getRequester().getId())
                .targetId(friendshipRequest.getTarget().getId())
                .received(friendshipRequest.getReceived())
                .message(friendshipRequest.getMessage())
                .build();
    }


}
