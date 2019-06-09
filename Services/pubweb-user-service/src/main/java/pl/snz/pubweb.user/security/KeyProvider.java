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

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyProvider(@Value("${public.key.path}") String publicKeyPath, @Value("${private.key.path}") String privateKeyPath) {
        try {
            this.publicKey = getPublicKey(publicKeyPath);
            this.privateKey = getPrivateKey(privateKeyPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getPublicKey(String publicKeyPath) throws Exception {
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(loadAndDecode(publicKeyPath));

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
