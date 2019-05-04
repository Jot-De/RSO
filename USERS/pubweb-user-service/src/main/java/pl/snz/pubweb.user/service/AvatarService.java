package pl.snz.pubweb.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.model.Avatar;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.repo.AvatarRepository;
import pl.snz.pubweb.user.repo.UserRepository;

import java.util.Base64;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    public final Avatar getForUser(Long userId) {
        return avatarRepository.findByUserId(userId).orElseThrow(NotFoundException.ofMessage("avatar.not.found", "userId", userId));
    }

    public final Avatar addForUser(Long userId, String base64Avatar) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException.userById(userId));
        final byte[] bytes = Base64.getDecoder().decode(base64Avatar);
        Avatar avatar = avatarRepository.findByUserId(userId).orElseGet(Avatar::new);
        avatar.setBytes(bytes);
        avatar.setUser(user);
        return avatarRepository.save(avatar);
    }

    public final void deleteForUser(Long userId) {
        avatarRepository.findByUserId(userId).ifPresent(avatarRepository::delete);
    }
}
