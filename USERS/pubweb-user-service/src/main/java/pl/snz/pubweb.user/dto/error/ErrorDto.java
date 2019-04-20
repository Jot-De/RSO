package pl.snz.pubweb.user.dto.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDto {
    private List<FieldError> fieldErrors;
    private ErrorType errorType;
    private String message;

    public static ErrorDto applicationError(String message) {return new ErrorDto(null, ErrorType.APPLICATION, message);}
    public static ErrorDto validationError(String message, List<FieldError> errors) {return new ErrorDto(errors, ErrorType.VALDIATION, message);}


}
