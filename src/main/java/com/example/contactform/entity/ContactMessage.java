package com.example.contactform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String message;

    private boolean spam;

    private LocalDateTime createdAt;

    // Constructors
    public ContactMessage() {
        this.createdAt = LocalDateTime.now();
    }

    public ContactMessage(String name, String email, String message, boolean spam) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.spam = spam;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isSpam() { return spam; }
    public void setSpam(boolean spam) { this.spam = spam; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
