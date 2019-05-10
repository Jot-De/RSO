package pl.snz.pubweb.commons.errors.exception;

public class InternalServerErrorException extends RuntimeException {
    private String message;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
