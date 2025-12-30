# 📝 Online Quiz System (Java Swing + MySQL)

## 📖 Overview
The **Online Quiz System** is a GUI-based application built with **Java Swing** and **MySQL**.  
It provides two distinct flows:
- **Student Flow** → Sign up, log in, take quizzes, and view results.
- **Admin Flow** → Log in as admin, manage questions, and view all student results.

The project follows a modular architecture with DAO (Data Access Object) patterns, ensuring clean separation between UI, business logic, and database operations. Passwords are securely stored using SHA-256 hashing.

---

## ⚙️ Features
- 🔑 Role-based login (Student vs Admin)
- 👨‍🎓 Student dashboard → attempt quizzes, store results
- 🛠️ Admin dashboard → add/view questions, view student results
- 🗄️ Database integration with MySQL (`users`, `questions`, `results` tables)
- 🔒 Secure authentication with SHA-256 password hashing
- 🧩 Scalable and maintainable project structure

---

## 📂 Project Structure
online-quiz-system/ │── src/com/quiz/config/      # DB connection, HashUtil │── src/com/quiz/dao/         # DAO interfaces │── src/com/quiz/dao/impl/    # DAO implementations │── src/com/quiz/model/       # User, Question, Result models │── src/com/quiz/view/        # Swing frames (Login, Quiz, Admin) │── lib/                      # MySQL Connector JAR │── bin/                      # Compiled .class files │── README.md                 # Project documentation




---

## 🛠️ Setup Instructions

### 1. Database Setup
Run these SQL scripts to create tables:

```sql
CREATE DATABASE quizdb;

USE quizdb;

CREATE TABLE users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(256),
    role ENUM('STUDENT','ADMIN')
);

CREATE TABLE questions (
    questionId INT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(50),
    question TEXT,
    optionA VARCHAR(200),
    optionB VARCHAR(200),
    optionC VARCHAR(200),
    optionD VARCHAR(200),
    correctOption CHAR(1)
);

CREATE TABLE results (
    resultId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    subject VARCHAR(50),
    score INT,
    FOREIGN KEY (userId) REFERENCES users(userId)
);
