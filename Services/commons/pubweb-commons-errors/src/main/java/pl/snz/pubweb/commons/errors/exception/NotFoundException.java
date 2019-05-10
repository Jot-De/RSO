package pl.snz.pubweb.commons.errors.exception;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class NotFoundException extends RuntimeException {

    private final String by;
    private final Object value;

    public NotFoundException(String message, String by, Object providedValue) {
        super(message);
        this.by = by;
        this.value = providedValue;
    }

    public static Supplier<NotFoundException> ofMessage(String message, String by, Object providedValue) {
        return () -> new NotFoundException(message, by, providedValue);
    }

    public static Supplier<NotFoundException> userById(Long id) {
        return () -> new NotFoundException("user.not.found", "id", id);
    }

}
