package pl.snz.pubweb.user.module.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.dto.Base64PictureDto;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.user.model.User;

import java.util.Base64;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    public final Avatar getForUser(Long userId) {
        return avatarRepository.findByUserId(userId).orElseThrow(NotFoundException.ofMessage("avatar.not.found", "userId", userId));
    }

    public final Avatar addForUser(Base64PictureDto dto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException.userById(userId));
        final byte[] bytes = Base64.getMimeDecoder().decode(dto.getBase64Picture());
        Avatar avatar = avatarRepository.findByUserId(userId).orElseGet(Avatar::new);
        avatar.setBytes(bytes);
        avatar.setUser(user);
        avatar.setDataFormat(dto.getDataFormat());
        return avatarRepository.save(avatar);
    }

    public final void deleteForUser(Long userId) {
        avatarRepository.findByUserId(userId).ifPresent(avatarRepository::delete);
    }
}
