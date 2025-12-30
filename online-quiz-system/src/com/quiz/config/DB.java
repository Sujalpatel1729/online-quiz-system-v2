package com.quiz.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    // Change these values to match your MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/quizdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";        // your MySQL username
    private static final String PASS = "12345678"; // your MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}