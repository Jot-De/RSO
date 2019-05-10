package pl.snz.pubweb.user.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }

    public static InternalServerErrorException ofMessage(String message) {
        return new InternalServerErrorException(message);
    }
}
