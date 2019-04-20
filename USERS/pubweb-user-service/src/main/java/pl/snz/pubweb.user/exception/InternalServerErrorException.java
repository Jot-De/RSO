package pl.snz.pubweb.user.exception;

public class InternalServerErrorException extends RuntimeException {
    private String message;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
