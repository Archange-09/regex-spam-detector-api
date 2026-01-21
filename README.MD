# Regex Spam Detector API / Contact Form REST API with Regex Validation

A robust RESTful API built with **Spring Boot** designed to intercept, analyze, and filter spam in contact form submissions using high-performance Regular Expressions (Regex).

---

## Table of Contents
* [Features](#-features)
* [Architecture](#-architecture)
* [Tech Stack](#-tech-stack)
* [Database Schema](#-database-schema)
* [Installation & Setup](#-installation--setup)
* [API Endpoints](#-api-endpoints)

---

##  Features
* **Pattern-Based Detection:** Identifies spam using regex logic across Name, Email, and Message fields.
* **Persistent Storage:** Saves all submissions to a MySQL database with a "spam" flag for moderation.
* **DTO Pattern:** Uses Data Transfer Objects to ensure secure and clean data handling between the client and server.
* **Automated Testing:** Includes Unit and Integration tests to ensure 100% reliability of the spam filtering logic.

---

##  Architecture
The project follows the standard **Spring Boot Layered Architecture**:
1. **Controller:** Handles incoming REST requests.
2. **DTO:** Validates and transfers data from the request body.
3. **Service:** Contains the core Business Logic (Regex filtering).
4. **Repository:** Manages data persistence via Spring Data JPA.
5. **Entity:** Maps the Java objects to the MySQL database tables.

---

## Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Data:** Spring Data JPA / Hibernate
* **Database:** MySQL
* **Testing:** JUnit 5 / AssertJ
* **Build Tool:** Maven

---

## Database Schema
The API automatically manages the `contact_messages` table:

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | BIGINT | Primary Key (Auto-increment) |
| `name` | VARCHAR(255) | User's full name |
| `email` | VARCHAR(255) | User's email address |
| `message` | TEXT | The content of the submission |
| `spam` | TINYINT(1) | `1` if spam detected, `0` if clean |
| `created_at` | TIMESTAMP | Auto-generated submission time |

---

##  Installation & Setup

### 1. Clone the repository
```bash
git clone [https://github.com/YOUR_USERNAME/regex-spam-detector.git](https://github.com/YOUR_USERNAME/regex-spam-detector.git)
cd regex-spam-detector
```


## Testing the API
1. Verification (GET)
Before testing the logic, verify the API is running correctly.

Endpoint: http://localhost:8080/api/contact/test

Method: GET

Should return "API is Running"

2. Postman Usage (POST)
Use Postman to send data to the spam detector.

Endpoint: http://localhost:8080/api/contact

Method: POST

Headers: * Key: Content-Type

Value: application/json

Sample JSON Body (Non-Spam):

```JSON

{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "message": "Hi, I have a question."
}
```
Sample JSON Body (Spam):

```JSON

{
  "name": "Spam Bot",
  "email": "bad-actor@spam.net",
  "message": "WINNER! You won 1,000,000! Click here for FREE MONEY: http://scam.com"
}
```
Automated Testing
The project includes standard JUnit 5 tests to ensure the regex logic doesn't break when you add new rules.

Running Tests
You can run the tests directly from your IDE (IntelliJ recommended) 
