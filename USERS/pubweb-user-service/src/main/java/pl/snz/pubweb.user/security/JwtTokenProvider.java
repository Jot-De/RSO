package pl.snz.pubweb.user.security;

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
import pl.snz.pubweb.user.dto.user.UserAuthInfo;
import pl.snz.pubweb.user.exception.AuthorizationException;
import pl.snz.pubweb.user.exception.BadRequestException;
import pl.snz.pubweb.user.exception.InternalServerErrorException;
import pl.snz.pubweb.user.model.Role;
import pl.snz.pubweb.user.util.Mappers;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private final ObjectMapper objectMapper;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        UserAuthInfo userAuthInfo = UserAuthInfo.builder()
                .userId(userPrincipal.getId())
                .login(userPrincipal.getUsername())
                .roles(Mappers.list(GrantedAuthority::getAuthority, userPrincipal.getAuthorities()))
                .build();

        final String subject = serializeSubject(userAuthInfo);

        return Jwts.builder()
                .setSubject(subject/*Long.toString(userPrincipal.getId())*/)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        final UserAuthInfo userAuthInfo = deserializeSubject(claims);

        return userAuthInfo.getUserId();
    }

    private String serializeSubject(UserAuthInfo userAuthInfo) {
        try {
            return objectMapper.writeValueAsString(userAuthInfo);
        } catch (JsonProcessingException e) {
            throw InternalServerErrorException.ofMessage("internal.json.error");
        }
    }

    private UserAuthInfo deserializeSubject(Claims claims) {
        try {
            return objectMapper.readValue(claims.getSubject(), UserAuthInfo.class);
        } catch (IOException e) {
            throw InternalServerErrorException.ofMessage("jwt.unparsable");
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
