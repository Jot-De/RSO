package pl.snz.pubweb.user.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.snz.pubweb.user.exception.BadRequestException;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.model.friend.FriendRequestStatus;
import pl.snz.pubweb.user.model.friend.Friendship;
import pl.snz.pubweb.user.model.friend.FriendshipRequest;
import pl.snz.pubweb.user.repo.FriendshipRepository;
import pl.snz.pubweb.user.repo.FriendshipRequestRepository;
import pl.snz.pubweb.user.repo.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendService {
    private final FriendshipRepository friendshipRepository;
    private final FriendshipRequestRepository friendshipRequestRepository;
    private final UserRepository userRepository;

    public boolean areFriends(Long u1, Long u2) {
        return friendshipRepository.findBetween(u1, u2).isPresent();
    }
    public List<Friendship> getForUser(Long userId) {
        return friendshipRepository.getForUser(userId);
    }


    public Friendship makeFriends(User requester, User target) {
         Optional<Friendship> opt = friendshipRepository.findBetween(requester.getId(), target.getId());
         if(opt.isPresent())
             return opt.get();
        Friendship friendship = new Friendship();
        friendship.setUser(requester);
        friendship.setUser2(target);
        friendship.setSince(LocalDate.now());
        return friendshipRepository.save(friendship);
    }


}
