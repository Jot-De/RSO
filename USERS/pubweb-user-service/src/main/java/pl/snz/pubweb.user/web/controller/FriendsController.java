package pl.snz.pubweb.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.user.dto.user.friend.FriendshipRequestDto;
import pl.snz.pubweb.user.dto.user.friend.SendFriendshipRequest;
import pl.snz.pubweb.user.exception.AuthorizationException;
import pl.snz.pubweb.user.model.friend.FriendshipRequest;
import pl.snz.pubweb.user.presentation.FriendshipPresentationService;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;
import pl.snz.pubweb.user.service.user.FriendRequestService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendsController {

   private final RequestSecurityContextProvider requestSecurityContextProvider;
   private final FriendshipPresentationService friendshipPresentationService;
   private final FriendRequestService friendRequestService;

    @GetMapping("/requests")
    public List<FriendshipRequestDto> getAllRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendRequestService.getAllPendingRequests(requestSecurityContextProvider.principalId(), PageRequest.of(page, size)).stream()
                .map(friendshipPresentationService::requestDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/sent")
    public List<FriendshipRequestDto> getSentRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendRequestService.getAllSentRequests(requestSecurityContextProvider.principalId(), PageRequest.of(page, size)).stream()
                .map(friendshipPresentationService::requestDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/received")
    public List<FriendshipRequestDto> getReceivedRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size) {
        return friendRequestService.getAllReceivedRequests(requestSecurityContextProvider.principalId(), PageRequest.of(page, size)).stream()
                .map(friendshipPresentationService::requestDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/{id}")
    public FriendshipRequestDto getOneRequest(@PathVariable long id) {
        final FriendshipRequest request = friendRequestService.getOne(id);
        final long principalId = requestSecurityContextProvider.principalId();
        if(principalId != request.getTarget().getId().longValue() && principalId != request.getRequester().getId().longValue())
            throw new AuthorizationException("friend.request.not.accessible");
        return friendshipPresentationService.requestDto(request);
    }

    @PostMapping("/requests")
    public FriendshipRequestDto sendRequest(@Valid @RequestBody SendFriendshipRequest sendFriendshipRequest) {
        return friendshipPresentationService.requestDto(
                friendRequestService.sendRequest(
                        requestSecurityContextProvider.principalId(),
                        sendFriendshipRequest.getMessage(),
                        sendFriendshipRequest.getTargetUser())
        );
    }

    @PostMapping("/requests/{id}/confirm")
    public FriendshipRequestDto confirmRequest(@PathVariable long id) {
        final FriendshipRequest request = friendRequestService.getOne(id);
        ensureUserIsRequestTarget(request);
        return friendshipPresentationService.requestDto(friendRequestService.confirm(request));
    }

    @PostMapping("/requests/{id}/cancel")
    public FriendshipRequestDto denyRequest(@PathVariable long id) {
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
