package pl.snz.pubweb.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorizationException extends RuntimeException {
    private final String messageKey;

    public static AuthorizationException ownContextRequired() {
        return new AuthorizationException("auth.own.context.required");
    }

    public static AuthorizationException friendContextRequired() {
        return new AuthorizationException("auth.friend.context.required");
    }

    public static AuthorizationException adminContextRequired() {
        return new AuthorizationException("auth.admin.context.required");
    }

    public static AuthorizationException permissionRequired(String key) {
        return new AuthorizationException("permission.required");
    }
}
