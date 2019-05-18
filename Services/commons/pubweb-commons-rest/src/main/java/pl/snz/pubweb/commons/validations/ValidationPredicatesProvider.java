package pl.snz.pubweb.commons.validations;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ValidationPredicatesProvider {
    private final List<Character> lowerCase = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'w', 'y', 'x', 'z');
    private final List<Character> upperCase = lowerCase.stream().map(Character::toUpperCase).collect(Collectors.toList());
    private final List<Character> specialChars = Arrays.asList('!', '@', '#', '$', '%', '^', '&', '&', '*');
    private final List<Character> numbers = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
    private final List<Character> base64Chars = Arrays.asList(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    );

    public boolean containsLowerCase(String s) {
        return anyChar(s, lowerCase::contains);
    }
    public boolean containsUpperCase(String s) {
        return anyChar(s, upperCase::contains);
    }
    public boolean containsSpecialChar(String s) {
        return anyChar(s, specialChars::contains);
    }
    public boolean constainsNumber(String s) {
        return anyChar(s, numbers::contains);
    }

    public <T> Predicate<T> nullablePredicate(Predicate<T> original) {
        return x -> x == null ? true : original.test(x);
    }

    public boolean allCharsWithingNumbersOrUpperOrLowerCase(String s) {
        return s.chars().mapToObj(c -> (char) c).allMatch(t -> or(t, Character::isDigit, Character::isLetter));
    }

    public boolean isBase64(String s) {
        return allChars(s, base64Chars::contains);
    }

    private <T> boolean or(T t, Predicate<T>... predicates) {
        for (Predicate<T> predicate : predicates) {
            if (predicate.test(t))
                return true;
        }
        return false;
    }

    private boolean allChars(String s, Function<Character,Boolean> check) {
        for(char c: s.toCharArray())
            if(!check.apply(c))
                return false;
            return true;
    }

    private boolean anyChar(String s, Function<Character, Boolean> check) {
        for (char c : s.toCharArray()) {
            if (check.apply(c))
                return true;
        }
        return false;
    }

    public boolean isUUID(String s) {
        try {
            UUID uuid = UUID.fromString(s);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

}
