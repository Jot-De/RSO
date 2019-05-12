package pl.snz.pubweb.commons.errors.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static BadRequestExceptionBuilder builder(String messageKey) {
        return BadRequestExceptionBuilder.ofMessageKey(messageKey);
    }

    @RequiredArgsConstructor(staticName = "ofMessageKey")
    public static class BadRequestExceptionBuilder {
        private final String messageKey;
        private final List<RequestFieldError> errors = new ArrayList<>();

        public BadRequestExceptionBuilder error(String field, String messageKey) {
            errors.add(RequestFieldError.of(field, messageKey));
            return this;
        }

        public BadRequestException build() {
            return new BadRequestException(messageKey, errors);
        }

    }


    @Accessors(fluent = true) @Getter
    @RequiredArgsConstructor(staticName = "of")
    public static class RequestFieldError {
        private final String field;
        private final String messageKey;
    }
}
