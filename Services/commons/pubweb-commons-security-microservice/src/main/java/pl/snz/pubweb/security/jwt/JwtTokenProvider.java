package pl.snz.pubweb.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.security.JwtSecurityException;
import pl.snz.pubweb.security.subject.UserAuthInfo;
import pl.snz.pubweb.security.user.UserPrincipal;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private final ObjectMapper objectMapper;

    public UserAuthInfo getUserFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return deserializeSubject(claims);
    }

    private String serializeSubject(UserAuthInfo userAuthInfo) {
        try {
            return objectMapper.writeValueAsString(userAuthInfo);
        } catch (JsonProcessingException e) {
            throw JwtSecurityException.of("internal.json.error");
        }
    }

    private UserAuthInfo deserializeSubject(Claims claims) {
        try {
            return objectMapper.readValue(claims.getSubject(), UserAuthInfo.class);
        } catch (IOException e) {
            throw JwtSecurityException.of("jwt.unparsable");
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
