package pl.snz.pubweb.security;

public class JwtSecurityException extends RuntimeException {

    private JwtSecurityException(String message) {
        super(message);
    }

    public static JwtSecurityException of(String message) {
        return new JwtSecurityException(message);
    }
}
