package pl.snz.pubweb.user.module.common.web;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class CurrentRequestUriProvider {

    public URI userAvatar(Long userId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment("users")
                .pathSegment(userId.toString())
                .pathSegment("avatar")
                .build().toUri();
    }
}
