package com.quiz;

import javax.swing.SwingUtilities;
import com.quiz.view.LoginSignupFrame;

public class App {
    public static void main(String[] args) {
        // Start the program with the Login + Signup window
        SwingUtilities.invokeLater(() -> new LoginSignupFrame().setVisible(true));
    }
}