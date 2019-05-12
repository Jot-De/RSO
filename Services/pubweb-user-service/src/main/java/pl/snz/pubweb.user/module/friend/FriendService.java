package pl.snz.pubweb.user.module.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.friend.model.Friendship;
import pl.snz.pubweb.user.module.friend.FriendshipRepository;
import pl.snz.pubweb.user.module.friend_request.FriendshipRequestRepository;
import pl.snz.pubweb.user.module.user.UserRepository;

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
