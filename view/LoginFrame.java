package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginFrame() {
        setTitle("Admissions Help Desk - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        usernameField.setBounds(50, 30, 200, 25);
        passwordField.setBounds(50, 70, 200, 25);
        loginButton.setBounds(50, 110, 80, 25);
        registerButton.setBounds(150, 110, 100, 25);

        add(new JLabel("Username:")).setBounds(50, 10, 100, 25);
        add(usernameField);
        add(new JLabel("Password:")).setBounds(50, 50, 100, 25);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        // Add action listeners (will connect to controller later)
    }

    // Getters for fields
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}