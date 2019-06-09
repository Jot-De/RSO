package pl.snz.pubweb.user.module.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.friend.FriendshipRepository;
import pl.snz.pubweb.user.module.friend.model.Friendship;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    @Override
    public void delete(User user) {
        final List<Friendship> forUser = friendshipRepository.getForUser(user.getId());
        friendshipRepository.deleteAll(forUser);
        userRepository.delete(user);
    }
}
