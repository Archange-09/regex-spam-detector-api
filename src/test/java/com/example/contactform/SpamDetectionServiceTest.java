package com.example.contactform;

import com.example.contactform.service.SpamDetectionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpamDetectionServiceTest {

    @Autowired
    private SpamDetectionService spamService;

    // ------------------ CLEAN MESSAGE TESTS ------------------
    @Test
    void testCleanMessage1() {
        String message = "Hello, I need help with my account.";
        assertFalse(spamService.isSpam(message), "Expected CLEAN message to return false");
    }

    @Test
    void testCleanMessage2() {
        String message = "Can we schedule a meeting tomorrow?";
        assertFalse(spamService.isSpam(message));
    }

    @Test
    void testCleanMessage3() {
        String message = "This is a normal inquiry about pricing.";
        assertFalse(spamService.isSpam(message));
    }

    // ------------------ SPAM MESSAGE TESTS ------------------
    @Test
    void testSpamMultipleUrls() {
        String message = "Check this https://spam.com and also this https://malware.com";
        assertTrue(spamService.isSpam(message), "Expected SPAM for multiple URLs");
    }

    @Test
    void testSpamRepeatedSymbols() {
        String message = "Buy now!!!!!!!!! This is urgent!!!!!";
        assertTrue(spamService.isSpam(message));
    }

    @Test
    void testSpamAllCaps() {
        String message = "THIS IS A SPAM MESSAGE IN ALL CAPS!";
        assertTrue(spamService.isSpam(message));
    }

    @Test
    void testSpamRepeatedWords() {
        String message = "Buy buy buy this product now";
        assertTrue(spamService.isSpam(message));
    }

    // ------------------ EDGE CASES ------------------
    @Test
    void testMixedCaseRepeatedWordsEdge() {
        String message = "Buy BUY buy this product now";
        assertTrue(spamService.isSpam(message));
    }

    @Test
    void testShortAllCapsNotSpam() {
        String message = "HI THERE!";
        assertFalse(spamService.isSpam(message), "Short all-caps should not be spam");
    }

    @Test
    void testEmptyMessage() {
        String message = "";
        assertFalse(spamService.isSpam(message), "Empty message should be considered CLEAN");
    }

    @Test
    void testNullMessage() {
        String message = null;
        assertFalse(spamService.isSpam(message), "Null message should be considered CLEAN");
    }

    // ------------------ CLEVER TRICKS ------------------
    @Test
    void testObfuscatedUrls() {
        String message = "Visit hxxp://spam.com and hxxps://malware.com";
        assertFalse(spamService.isSpam(message), "Obfuscated URLs should not trigger spam");
    }

    @Test
    void testUrlsWithNewline() {
        String message = "Check this https://spam.com\nand also this https://malware.com";
        assertTrue(spamService.isSpam(message), "Multiple URLs separated by newline should trigger spam");
    }

    @Test
    void testMixedCaseRepeatedWordsClever() {
        String message = "Buy BUY Buy buy";
        assertTrue(spamService.isSpam(message), "Mixed-case repeated words should trigger spam");
    }

    @Test
    void testRepeatedSymbolsAcrossWords() {
        String message = "Hello!!! Wow!!!";
        assertFalse(spamService.isSpam(message), "Repeated symbols not consecutive >=4 should not trigger spam");
    }

    @Test
    void testAllCapsWithNumbersAndPunctuation() {
        String message = "THIS IS SPAM! 123!!!";
        assertTrue(spamService.isSpam(message), "All-caps detection should allow punctuation and numbers");
    }

    @Test
    void testLongMessageOneUrl() {
        String message = "This is a long but very legitimate message with many unique words " +
                "sent to ensure that one single link does not trip the filter " +
                "https://example.com";
        assertFalse(spamService.isSpam(message));
    }

    @Test
    void testShortRepeatedWords() {
        String message = "Go go go";
        assertTrue(spamService.isSpam(message), "Minimal repeated words should trigger spam");
    }

    @Test
    void testUnicodeRepeatedWords() {
        String message = "Buy 💰 Buy 💰 Buy 💰";
        assertTrue(spamService.isSpam(message), "Repeated emoji/Unicode words should trigger spam");
    }

    // ------------------ NEW NAME TESTS ------------------
    @Test
    void testValidName() {
        String name = "John Doe";
        assertFalse(spamService.isNameSpam(name), "Normal name should not be spam");
    }

    @Test
    void testNameWithNumbers() {
        String name = "John123";
        assertTrue(spamService.isNameSpam(name), "Name with numbers should be spam");
    }

    @Test
    void testNameWithSymbols() {
        String name = "John@Doe!";
        assertTrue(spamService.isNameSpam(name), "Name with special symbols should be spam");
    }

    @Test
    void testNameAllCapsLong() {
        String name = "JONATHAN ALEXANDER SMITH";
        assertTrue(spamService.isNameSpam(name), "Long all-caps name should be spam");
    }

    @Test
    void testEmptyName() {
        String name = "";
        assertTrue(spamService.isNameSpam(name), "Empty name should be considered spam");
    }

    // ------------------ NEW EMAIL TESTS ------------------
    @Test
    void testValidEmail() {
        String email = "user@example.com";
        assertFalse(spamService.isEmailSpam(email), "Proper email should not be spam");
    }

    @Test
    void testEmailMissingAt() {
        String email = "userexample.com";
        assertTrue(spamService.isEmailSpam(email), "Email missing '@' should be spam");
    }

    @Test
    void testEmailMissingDomain() {
        String email = "user@.com";
        assertTrue(spamService.isEmailSpam(email), "Email missing domain should be spam");
    }

    @Test
    void testEmailWithSpaces() {
        String email = "user @example.com";
        assertTrue(spamService.isEmailSpam(email), "Email with spaces should be spam");
    }

    @Test
    void testEmptyEmail() {
        String email = "";
        assertTrue(spamService.isEmailSpam(email), "Empty email should be considered spam");
    }

    // ------------------ ADDITIONAL MESSAGE EDGE CASES ------------------
    @Test
    void testMessageWithOneUrl() {
        String message = "Check out https://example.com for info!";
        assertFalse(spamService.isSpam(message), "Single URL should not trigger spam");
    }

    @Test
    void testMessageWithMultipleUrlsWithSpaces() {
        String message = "https://spam.com https://malware.com";
        assertTrue(spamService.isSpam(message), "Multiple URLs separated by space should trigger spam");
    }

    @Test
    void testMessageWithUnicodeSymbols() {
        String message = "Hello ✨🌟🎉";
        assertFalse(spamService.isSpam(message), "Message with Unicode symbols only should not be spam");
    }

    @Test
    void testMessageWithRepeatedSymbolsEdge() {
        String message = "Wow!!!!!!! Amazing!!!";
        assertTrue(spamService.isSpam(message), "Repeated symbols >=4 consecutive should trigger spam");
    }
}
