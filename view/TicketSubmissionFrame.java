package view;

import javax.swing.*;

public class TicketSubmissionFrame extends JFrame {
    private JComboBox<String> issueTypeBox;
    private JTextArea descriptionArea;
    private JButton submitButton;
    private JButton logoutButton;
    private JButton viewTicketsButton; // New button

    public TicketSubmissionFrame() {
        setTitle("Submit Ticket");
        setSize(400, 400); // Increased height for new button
        setLayout(null);

        issueTypeBox = new JComboBox<>(new String[] { "Inquiry", "Complaint", "Question" });
        descriptionArea = new JTextArea();
        submitButton = new JButton("Submit");
        logoutButton = new JButton("Logout");
        viewTicketsButton = new JButton("View My Tickets"); // New button

        issueTypeBox.setBounds(50, 30, 200, 25);
        descriptionArea.setBounds(50, 70, 300, 150);
        submitButton.setBounds(50, 240, 100, 25);
        logoutButton.setBounds(200, 240, 100, 25);
        viewTicketsButton.setBounds(50, 280, 150, 25); // Position below others

        add(new JLabel("Issue Type:")).setBounds(50, 10, 100, 25);
        add(issueTypeBox);
        add(new JLabel("Description:")).setBounds(50, 50, 100, 25);
        add(descriptionArea);
        add(submitButton);
        add(logoutButton);
        add(viewTicketsButton);
    }

   
    // Getters
    public JComboBox<String> getIssueTypeBox() {
        return issueTypeBox;
    }

    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    } // New getter
 // Existing getters...
    public JButton getViewTicketsButton() {
        return viewTicketsButton;
    } // New getter
}