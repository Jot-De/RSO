package pl.snz.pubweb.user.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Getter
@Service
public class KeyProvider {
    private final static String pubKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAt9FtviuVSKfALXmHKXWExlTKeY42lqMvkOQcXWuE9H7ZwU5UuTUPp37OtkW+ZiYrP6BaaP3pQfNDcNfjm+pWUtNWJPCI4TY2anvvpz724n+VUoXaEFdeY+nOAOI4U07a4W+/um3QOE/NMNNaQh2Nx3N3BJG9eaOYc8k2L7THHABZiJ0Xzuz0UQFC4bExKDmv9S6foRYqU4OnqVuC1ohoDPchOnaQEB+S0dyk3pm52ee+c8jd3PkEextN6ALaGlyKLl47wk41RlEoUnOTWVI6G26TfV0GGy9BYeH03mk3uDd4s6bkK0QMJci8cx398TKQ3w4N0zrG3+n+gUhGzp9YXgA9iiNsBsqkwxpwjiTBRhHiww2w904OrJjJsKs5VvFI7g04xkMu4jqWrmowIiyUpNNJ+mqs29pM/AIRIvrQYILqC3/MDSgDnPY1qVNhBMezJonZUJCyur8crIQq/+L+sG1X3LImPPZT2ow/6R3yoOsPx8uHJJJtDlevIMwZuUFX2lFVNsCeDJsYx6fs80kMrl28Q0rN4hJOvwJdn8//9L9EAtZ0aStCkWpfwC9OUtYZ7gwOkPBDccPcu8K+HKU8sg5ksGptL1lFO0K1qmX3fcawKZNewQFp/AlFHD39AH3fgrN6HSJO3NZWYNtQa3XcJU5z+ERrJzQx39t1j007AAcCAwEAAQ==";

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyProvider(@Value("${private.key.path}") String privateKeyPath) {
        try {
            this.publicKey = createPublicKey();
            this.privateKey = getPrivateKey(privateKeyPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey createPublicKey() throws Exception {
        final byte[] key = Base64.getDecoder().decode(pubKey);
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);

        return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
    }

    private PrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(loadAndDecode(privateKeyPath));

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private byte[] loadAndDecode(String filePath) throws Exception {
        final Path path = ResourceUtils.getFile(filePath).toPath();
        final byte[] bytes = Files.readAllBytes(path);
        return Base64.getDecoder().decode(bytes);
    }

}
