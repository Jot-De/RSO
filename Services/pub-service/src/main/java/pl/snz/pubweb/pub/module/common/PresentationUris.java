package pl.snz.pubweb.pub.module.common;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class PresentationUris {

    public URI pictureUri(Long pictureId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment("pictures")
                .pathSegment(pictureId.toString())
                .build().toUri();
    }
}
