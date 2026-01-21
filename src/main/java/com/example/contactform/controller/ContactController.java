package com.example.contactform.controller;

import com.example.contactform.dto.ContactRequest;
import com.example.contactform.entity.ContactMessage;
import com.example.contactform.repository.ContactMessageRepository;
import com.example.contactform.service.SpamDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageRepository repository;
    private final SpamDetectionService spamService;

    public ContactController(ContactMessageRepository repository, SpamDetectionService spamService) {
        this.repository = repository;
        this.spamService = spamService;
    }

    // --- POST endpoint ---
    @PostMapping
    public ResponseEntity<?> submitContact(@RequestBody ContactRequest request) {

        // Basic validation
        if (request.getName() == null || request.getName().isBlank() ||
                request.getEmail() == null || request.getEmail().isBlank() ||
                request.getMessage() == null || request.getMessage().isBlank()) {
            return ResponseEntity.badRequest().body("All fields are required.");
        }

        // Check for spam in all fields
        boolean nameSpam = spamService.isNameSpam(request.getName());
        boolean emailSpam = spamService.isEmailSpam(request.getEmail());
        boolean messageSpam = spamService.isSpam(request.getMessage());

        if (nameSpam || emailSpam || messageSpam) {
            return ResponseEntity.badRequest().body("Your submission looks like spam.");
        }

        // Save to DB
        ContactMessage contactMessage = new ContactMessage(
                request.getName(),
                request.getEmail(),
                request.getMessage(),
                nameSpam || emailSpam || messageSpam
        );
        repository.save(contactMessage);

        return ResponseEntity.ok("Message received. Spam status: " + ((nameSpam || emailSpam || messageSpam) ? "SPAM" : "CLEAN"));
    }


    // --- GET test endpoint ---
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is running!");
    }

}
