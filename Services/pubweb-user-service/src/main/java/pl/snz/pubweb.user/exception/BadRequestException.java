package pl.snz.pubweb.user.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.snz.pubweb.user.dto.error.FieldError;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter
public class BadRequestException extends RuntimeException  {
    private String messageKey;
    private List<RequestFieldError> errors;

    public static BadRequestException general(String messageKey) {
        return new BadRequestException(messageKey, Collections.emptyList());
    }

    public static BadRequestException field(String field, String messageKey) {
        return new BadRequestException("bad.request.fields.validation", Collections.singletonList(RequestFieldError.of(field, messageKey)));
    }

    public static BadRequestException fields(List<RequestFieldError> errors) {
        return new BadRequestException("bad.request.fields.validation", errors);
    }

    @Accessors(fluent = true) @Getter
    @RequiredArgsConstructor(staticName = "of")
    public static class RequestFieldError {
        private final String field;
        private final String messageKey;
    }
}
