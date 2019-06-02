package pl.snz.pubweb.user.module.friend_request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.AuthorizationException;
import pl.snz.pubweb.user.module.friend.FriendshipPresentationService;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequest;
import pl.snz.pubweb.user.module.friend_request.model.FriendshipRequestInfo;
import pl.snz.pubweb.user.module.friend_request.model.SendFriendshipRequest;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendsRequestController {

   private final RequestSecurityContextProvider requestSecurityContextProvider;
   private final FriendshipPresentationService friendshipPresentationService;
   private final FriendRequestService friendRequestService;

    @Transactional
    @GetMapping("/requests")
    public List<FriendshipRequestInfo> getAllRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendRequestService.getAllPendingRequests(requestSecurityContextProvider.principalId(), PageRequest.of(page, size)).stream()
                .map(friendshipPresentationService::requestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/requests/sent")
    public List<FriendshipRequestInfo> getSentRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendRequestService.getAllSentRequests(requestSecurityContextProvider.principalId(), PageRequest.of(page, size)).stream()
                .map(friendshipPresentationService::requestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/requests/received")
    public List<FriendshipRequestInfo> getReceivedRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendRequestService.getAllReceivedRequests(requestSecurityContextProvider.principalId(), PageRequest.of(page, size)).stream()
                .map(friendshipPresentationService::requestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/requests/{id}")
    public FriendshipRequestInfo getOneRequest(@PathVariable long id) {
        final FriendshipRequest request = friendRequestService.getOne(id);
        final long principalId = requestSecurityContextProvider.principalId();
        if(principalId != request.getTarget().getId().longValue() && principalId != request.getRequester().getId().longValue())
            throw new AuthorizationException("friend.request.not.accessible");
        return friendshipPresentationService.requestDto(request);
    }

    @Transactional
    @PostMapping("/requests")
    public FriendshipRequestInfo sendRequest(@Valid @RequestBody SendFriendshipRequest sendFriendshipRequest) {
        return friendshipPresentationService.requestDto(
                friendRequestService.sendRequest(
                        requestSecurityContextProvider.principalId(),
                        sendFriendshipRequest.getMessage(),
                        sendFriendshipRequest.getTargetUser())
        );
    }

    @Transactional
    @PostMapping("/requests/{id}/confirm")
    public FriendshipRequestInfo confirmRequest(@PathVariable long id) {
        final FriendshipRequest request = friendRequestService.getOne(id);
        ensureUserIsRequestTarget(request);
        return friendshipPresentationService.requestDto(friendRequestService.confirm(request));
    }

    @Transactional
    @PostMapping("/requests/{id}/cancel")
    public FriendshipRequestInfo denyRequest(@PathVariable long id) {
        final FriendshipRequest request = friendRequestService.getOne(id);
        ensureUserIsRequestTarget(request);
        return friendshipPresentationService.requestDto(friendRequestService.cancel(request));
    }

    private void ensureUserIsRequestTarget(FriendshipRequest request) {
        final long principalId = requestSecurityContextProvider.principalId();
        if (principalId != request.getTarget().getId().longValue())
            throw new AuthorizationException("friend.request.user.not.target");
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable long id) {
        final FriendshipRequest request = friendRequestService.getOne(id);
        ensureUserIsRequestTarget(request);
        friendRequestService.delete(request);
        return ResponseEntity.ok().build();
    }

}
