package com.quiz.view;

import javax.swing.*;
import java.awt.*;
import com.quiz.model.User;
import com.quiz.config.HashUtil;
import com.quiz.dao.UserDao;
import com.quiz.dao.impl.UserDaoImpl;

public class LoginSignupFrame extends JFrame {
    private JTextField loginEmail = new JTextField(20);
    private JPasswordField loginPass = new JPasswordField(20);
    private JButton loginBtn = new JButton("Login");

    private JTextField signupName = new JTextField(20);
    private JTextField signupEmail = new JTextField(20);
    private JPasswordField signupPass = new JPasswordField(20);
    private JButton signupBtn = new JButton("Sign Up");

    private UserDao userDao = new UserDaoImpl();

    public LoginSignupFrame() {
        setTitle("Online Quiz System");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        JPanel loginPanel = new JPanel(new GridLayout(3,2,8,8));
        loginPanel.add(new JLabel("Email:"));
        loginPanel.add(loginEmail);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(loginPass);
        loginPanel.add(new JLabel());
        loginPanel.add(loginBtn);

        JPanel signupPanel = new JPanel(new GridLayout(4,2,8,8));
        signupPanel.add(new JLabel("Name:"));
        signupPanel.add(signupName);
        signupPanel.add(new JLabel("Email:"));
        signupPanel.add(signupEmail);
        signupPanel.add(new JLabel("Password:"));
        signupPanel.add(signupPass);
        signupPanel.add(new JLabel());
        signupPanel.add(signupBtn);

        tabs.add("Login", loginPanel);
        tabs.add("Signup", signupPanel);

        add(tabs);

        // LOGIN
loginBtn.addActionListener(e -> {
    String email = loginEmail.getText();
    String pass = new String(loginPass.getPassword());
    if(email.trim().isEmpty() || pass.trim().isEmpty()){
        JOptionPane.showMessageDialog(this,"Email and Password can not be Empty");
        return;
    }

    try {
    User u = userDao.findByEmail(email);

    if (u != null && u.getPassword().equals(HashUtil.sha256(pass))) {
        JOptionPane.showMessageDialog(this,
            "Login successful! Welcome " + u.getName());

        if ("ADMIN".equalsIgnoreCase(u.getRole())) {
            new AdminFrame().setVisible(true);
        } else {
            new QuizFrame(u.getUserId(), "Java").setVisible(true);
        }
        dispose();
    } else {
        JOptionPane.showMessageDialog(this,
            "Invalid email or password");
    }

} catch (Exception ex) {
    JOptionPane.showMessageDialog(this,"Database error. Please try again later.");
    }});


        // SIGNUP
        signupBtn.addActionListener(e -> {
            User u = new User();
            u.setName(signupName.getText());
            u.setEmail(signupEmail.getText());
            u.setPassword(new String(signupPass.getPassword()));
            u.setRole("STUDENT");
            if (signupName.getText().trim().isEmpty() ||
            signupEmail.getText().trim().isEmpty() ||
            signupPass.getPassword().length == 0) {

    JOptionPane.showMessageDialog(this,"All fields are required");
    return;
}

            try {
    if (userDao.save(u)) {
        JOptionPane.showMessageDialog(this,
            "Signup successful! You can now login");
        tabs.setSelectedIndex(0); // go back to Login tab
    } else {
        JOptionPane.showMessageDialog(this,
            "Signup failed. Try again.");
    }
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this,
        "Email already exists or database error");
}

    });
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignupFrame().setVisible(true));
    }
}