package pl.snz.pubweb.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.commons.dto.UserAuthInfo;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.InternalServerErrorException;

import java.io.IOException;

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
            throw InternalServerErrorException.ofMessage("general.server.error");
        }
    }

    private UserAuthInfo deserializeSubject(Claims claims) {
        try {
            return objectMapper.readValue(claims.getSubject(), UserAuthInfo.class);
        } catch (IOException e) {
            throw BadRequestException.general("jwt.unparsable");
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
