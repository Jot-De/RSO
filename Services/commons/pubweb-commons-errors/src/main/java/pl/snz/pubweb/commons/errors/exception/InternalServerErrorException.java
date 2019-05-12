package pl.snz.pubweb.commons.errors.exception;


public class InternalServerErrorException extends RuntimeException {
    private InternalServerErrorException(String message) {
        super(message);
    }

    public static InternalServerErrorException ofMessage(String message) {
        return new InternalServerErrorException(message);
    }
}
