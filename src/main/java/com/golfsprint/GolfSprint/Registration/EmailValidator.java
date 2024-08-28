package com.golfsprint.GolfSprint.Registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    /*private static final String PHONE_NUMBER_REGEX =
            "\\+\\d{1,3}+\\s\\d{3}\\s\\d{3}\\s\\d{2}\\s\\d{2}"; */
    private static final String PHONE_NUMBER_REGEX =
            "\\+\\d{1,3}\\d{10}";
    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    private final Pattern numberPattern= Pattern.compile(PHONE_NUMBER_REGEX);

    @Override
    public boolean test(String email) {
        if (email == null || email.trim().isEmpty() || email.length() > 40) {
            return false; // Return false for null or empty email
        }
        return pattern.matcher(email).matches();
    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcherNum = numberPattern.matcher(phoneNumber);
        return matcherNum.matches();
    }
}
