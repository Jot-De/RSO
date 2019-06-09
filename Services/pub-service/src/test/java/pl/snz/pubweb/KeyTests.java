package pl.snz.pubweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import pl.snz.pubweb.commons.dto.UserAuthInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

public class KeyTests {

    @Before
    public void generateKeys() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(4096);
        final KeyPair keyPair = generator.generateKeyPair();
        saveKey(keyPair.getPrivate(), ResourceUtils.getFile("priv.key"));
        saveKey(keyPair.getPublic(), ResourceUtils.getFile("pub.key"));
    }

    @Test
    public void doSomeTesting() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        PrivateKey privKey =  loadPrivateKey("priv.key");
        PublicKey pubKey =  loadPublicKey("pub.key");

        UserAuthInfo userAuthInfo = UserAuthInfo.builder()
                .userId(1l)
                .login("login")
                .roles(Collections.singletonList("xddd"))
                .permissions(Arrays.asList("per1", "per2", "per3"))
                .build();

        String subject = new ObjectMapper().writeValueAsString(userAuthInfo);
        final String jwt = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 640232132l))
                .signWith(SignatureAlgorithm.RS512, privKey)
                .compact();

        final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(pubKey)
                .parseClaimsJws(jwt);

        System.out.println(claimsJws.getBody().getSubject());
    }

    private void saveKey(Key key, File file) throws IOException {
        Files.write(file.toPath(), Base64.getEncoder().encodeToString(key.getEncoded()).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private PrivateKey loadPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        final Path path = ResourceUtils.getFile(filename).toPath();
        final byte[] bytes = Files.readAllBytes(path);
        final byte[] key = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private PublicKey loadPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        final Path path = ResourceUtils.getFile(filename).toPath();
        final byte[] bytes = Files.readAllBytes(path);
        final byte[] key = Base64.getDecoder().decode(bytes);
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);

        return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
    }
}
