package pl.snz.pubweb.user.module.friend_request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.friend.model.FriendRequestStatus;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequest;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.friend.FriendService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static pl.snz.pubweb.user.module.friend.model.FriendRequestStatus.PENDING;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendRequestService {

    private final FriendshipRequestRepository friendshipRequestRepository;
    private final UserRepository userRepository;
    private final FriendService friendshipService;

    public FriendshipRequest getOne(long id) {
        return friendshipRequestRepository.findById(id).orElseThrow(NotFoundException.ofMessage("friend.request.not.found", "id", id));
    }

    public List<FriendshipRequest> getAllPendingRequests(long userId, PageRequest pageRequest) {
        return friendshipRequestRepository.getAllPending(userId, pageRequest);
    }

    public List<FriendshipRequest> getAllSentRequests(long userId, PageRequest pageRequest) {
        return friendshipRequestRepository.getAllSent(userId, pageRequest);
    }

    public List<FriendshipRequest> getAllReceivedRequests(long userId, PageRequest pageRequest) {
        return friendshipRequestRepository.getAllReceived(userId, pageRequest);
    }

    public FriendshipRequest sendRequest(Long requester,String message, Long targetUser) {
        final Optional<FriendshipRequest> opt = friendshipRequestRepository.findByUsers(requester, targetUser);
        if (opt.isPresent())
            return opt.get();
        if(requester.equals(targetUser))
            throw BadRequestException.general("friend.request.cannot.sent.do.oneself");

        User requesterEntity = userRepository.findById(requester).orElseThrow(NotFoundException.userById(requester));
        User targetUserEntity = userRepository.findById(targetUser).orElseThrow(NotFoundException.userById(targetUser));

        if(friendshipService.areFriends(requesterEntity.getId(), targetUserEntity.getId()))
            throw BadRequestException.general("friend.request.user.already.friends");

        FriendshipRequest request = new FriendshipRequest(requesterEntity, targetUserEntity, PENDING , LocalDateTime.now(), message);
        return friendshipRequestRepository.save(request);
    }

    @Transactional
    public FriendshipRequest confirm(FriendshipRequest request) {
        if(request.getFriendRequestStatus().equals(FriendRequestStatus.CANCELLED))
            throw BadRequestException.general("friend.request.cannot.accept.cancelled");
        else if(request.getFriendRequestStatus().equals(FriendRequestStatus.ACCEPTED))
            return request;
        else {
            friendshipService.makeFriends(request.getRequester(), request.getTarget());
            request.setFriendRequestStatus(FriendRequestStatus.ACCEPTED);
            return friendshipRequestRepository.save(request);
        }
    }

    public FriendshipRequest cancel(FriendshipRequest request) {
        if(request.getFriendRequestStatus().equals(FriendRequestStatus.ACCEPTED))
            throw BadRequestException.general("friend.request.cannot.cancel.accepted");
        else if(request.getFriendRequestStatus().equals(FriendRequestStatus.CANCELLED))
            return request;
        else {
            request.setFriendRequestStatus(FriendRequestStatus.CANCELLED);
            return friendshipRequestRepository.save(request);
        }
    }

    public void delete(FriendshipRequest request) {
        friendshipRequestRepository.delete(request);
    }
}
