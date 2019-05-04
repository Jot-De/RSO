package pl.snz.pubweb.user.web.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.snz.pubweb.user.dto.error.ApiError;
import pl.snz.pubweb.user.dto.error.FieldError;
import pl.snz.pubweb.user.exception.AuthorizationException;
import pl.snz.pubweb.user.exception.BadRequestException;
import pl.snz.pubweb.user.exception.InternalServerErrorException;
import pl.snz.pubweb.user.exception.NotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@PropertySource("exception.properties")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppExceptionHandler {

    private final Environment env;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handle(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(ApiError.applicationError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleNotFound(NotFoundException exception) {
        return new ResponseEntity<>(ApiError.applicationError(translateExceptionMessage(exception.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handle(ConstraintViolationException constraintValidationException) {
        return ResponseEntity.badRequest().body(ApiError.validationError(constraintValidationException.getMessage(), mapToErrors(constraintValidationException)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handle(MethodArgumentNotValidException ex) {
        List<FieldError> errors = new ArrayList<>();
        for (org.springframework.validation.FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new FieldError(error.getField(), getMessage(error)));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new FieldError(error.getObjectName(), getMessage(error)));
        }
        return ResponseEntity.badRequest().body(ApiError.validationError(ex.getLocalizedMessage(), errors));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        List<FieldError> errors = ex.getErrors().stream().map(this::toFieldError).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(ApiError.validationError(translateExceptionMessage(ex.getMessageKey()), errors));
    }


    @ExceptionHandler
    public ResponseEntity<ApiError> handleServerError(InternalServerErrorException ex) {
        return new ResponseEntity<>(ApiError.applicationError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleAuthorizationException(AuthorizationException ex) {
        return new ResponseEntity<>(ApiError.applicationError(translateExceptionMessage(ex.getMessageKey())), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBindException(BindException ex) {
        final List<FieldError> errors = ex.getAllErrors().stream().map(error -> new FieldError(error.getObjectName(), getMessage(error))).collect(Collectors.toList());
        return new ResponseEntity<>(ApiError.validationError(ex.getLocalizedMessage(), errors), HttpStatus.BAD_REQUEST);
    }

    private String translateExceptionMessage(String messageKey) {
        return env.getProperty(messageKey, messageKey);
    }

    private String getMessage(org.springframework.validation.FieldError fieldError) {
        return env.getProperty(fieldError.getCode(), fieldError.getDefaultMessage());
    }

    private String getMessage(ObjectError objectError) {
        return env.getProperty(objectError.getCode(), objectError.getDefaultMessage());
    }

    private List<FieldError> mapToErrors(ConstraintViolationException exception) {
       return exception.getConstraintViolations().stream().map(this::mapToError).collect(Collectors.toList());
    }

    private FieldError mapToError(ConstraintViolation<?> violation) {
        return new FieldError(violation.getPropertyPath().toString(), violation.getMessage());
    }

    private FieldError toFieldError(BadRequestException.RequestFieldError requestFieldError) {
        return new FieldError(requestFieldError.field(), translateExceptionMessage(requestFieldError.messageKey()));
    }

}
