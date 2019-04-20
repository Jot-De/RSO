package pl.snz.pubweb.user.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class FieldError {
    private String field;
    private String message;

    public FieldError withMessage(String message) {
        return new FieldError(field, message);
    }
}
