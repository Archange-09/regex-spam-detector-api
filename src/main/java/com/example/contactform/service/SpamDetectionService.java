package com.example.contactform.service;

import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SpamDetectionService {

    private static final Pattern REPEATED_SYMBOLS = Pattern.compile("([!@#$%^&*]){4,}");
    private static final Pattern ALL_CAPS = Pattern.compile("^(?=(?:.*[A-Z]){10,})[A-Z0-9\\s!?,.]+$");
    private static final Pattern REPEATED_WORDS = Pattern.compile(
            "([\\p{L}\\p{N}\\p{So}]+(?:\\s+[\\p{L}\\p{N}\\p{So}]+)*)(\\s+\\1){2,}",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
    );

    // Simple regex for validating a name: letters, spaces, hyphens, apostrophes
    private static final Pattern VALID_NAME = Pattern.compile("^[A-Za-z\\s'-]{1,50}$");

    // Simple regex for validating email (basic check)
    private static final Pattern VALID_EMAIL = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$");

    public boolean isSpam(String message) {
        if (message == null || message.isBlank()) return false;
        return hasMultipleUrls(message)
                || REPEATED_SYMBOLS.matcher(message).find()
                || ALL_CAPS.matcher(message).find()
                || REPEATED_WORDS.matcher(message).find();
    }

    public boolean isNameSpam(String name) {
        if (name == null || name.isBlank()) return true; // empty names are invalid
        // return true if name contains invalid characters
        return !VALID_NAME.matcher(name).matches()
                || REPEATED_SYMBOLS.matcher(name).find()
                || ALL_CAPS.matcher(name).find();
    }

    public boolean isEmailSpam(String email) {
        if (email == null || email.isBlank()) return true; // empty emails are invalid
        // return true if email is invalid format or has spammy patterns
        return !VALID_EMAIL.matcher(email).matches();
    }

    private boolean hasMultipleUrls(String message) {
        Pattern urlPattern = Pattern.compile("https?://\\S+");
        Matcher matcher = urlPattern.matcher(message);
        return matcher.find() && matcher.find(); // immediate exit after 2nd URL
    }
}

