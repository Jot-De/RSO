package pl.snz.pubweb.user.dto.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiError {
    private List<FieldError> fieldErrors;
    private ErrorType errorType;
    private String message;

    public static ApiError applicationError(String message) {return new ApiError(null, ErrorType.APPLICATION, message);}
    public static ApiError validationError(String message, List<FieldError> errors) {return new ApiError(errors, ErrorType.VALDIATION, message);}


}
