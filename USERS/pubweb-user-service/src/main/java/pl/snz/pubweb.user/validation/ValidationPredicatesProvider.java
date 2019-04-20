package pl.snz.pubweb.user.validation;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ValidationPredicatesProvider {
    private final List<Character> lowerCase;
    private final List<Character> upperCase;
    private final List<Character> specialChars;
    private final List<Character> numbers;

    public ValidationPredicatesProvider() {
        lowerCase = Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','w','y', 'x', 'z');
        upperCase = lowerCase.stream().map(Character::toUpperCase).collect(Collectors.toList());
        specialChars = Arrays.asList('!', '@', '#', '$', '%', '^', '&', '&', '*');
        numbers = Arrays.asList('1','2','3','4','5','6','7','8','9','0');
    }

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

    public boolean allCharsWithingNumbersOrUpperOrLowerCase(String s) {
        return s.chars().mapToObj(c ->(char)c).allMatch(t -> or(t, Character::isDigit, Character::isLetter));
    }

    private <T> boolean or(T t, Predicate<T>... predicates) {
        for (Predicate<T> predicate : predicates) {
            if(predicate.test(t))
                return true;
        }
        return false;
    }

    private boolean anyChar(String s, Function<Character, Boolean> check) {
        for(char c : s.toCharArray()) {
            if (check.apply(c))
                return true;
        }
        return false;
    }







}
