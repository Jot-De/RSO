package pl.snz.pubweb.security.jwt;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtPublicKeyProvider {

    @Getter
    private final PublicKey key;

    public JwtPublicKeyProvider() {
        try {
            this.key = getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getPublicKey() throws Exception {
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(KEY));

        return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
    }

    private static final String KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAt9FtviuVSKfALXmHKXWExlTKeY42lqMvkOQcXWuE9H7ZwU5UuTUPp37OtkW+ZiYrP6BaaP3pQfNDcNfjm+pWUtNWJPCI4TY2anvvpz724n+VUoXaEFdeY+nOAOI4U07a4W+/um3QOE/NMNNaQh2Nx3N3BJG9eaOYc8k2L7THHABZiJ0Xzuz0UQFC4bExKDmv9S6foRYqU4OnqVuC1ohoDPchOnaQEB+S0dyk3pm52ee+c8jd3PkEextN6ALaGlyKLl47wk41RlEoUnOTWVI6G26TfV0GGy9BYeH03mk3uDd4s6bkK0QMJci8cx398TKQ3w4N0zrG3+n+gUhGzp9YXgA9iiNsBsqkwxpwjiTBRhHiww2w904OrJjJsKs5VvFI7g04xkMu4jqWrmowIiyUpNNJ+mqs29pM/AIRIvrQYILqC3/MDSgDnPY1qVNhBMezJonZUJCyur8crIQq/+L+sG1X3LImPPZT2ow/6R3yoOsPx8uHJJJtDlevIMwZuUFX2lFVNsCeDJsYx6fs80kMrl28Q0rN4hJOvwJdn8//9L9EAtZ0aStCkWpfwC9OUtYZ7gwOkPBDccPcu8K+HKU8sg5ksGptL1lFO0K1qmX3fcawKZNewQFp/AlFHD39AH3fgrN6HSJO3NZWYNtQa3XcJU5z+ERrJzQx39t1j007AAcCAwEAAQ==";
}
