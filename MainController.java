package controller; // Change to 'package admissions;' if using flat structure

import dao.*;
import model.*;
import view.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MainController {
    private Connection conn;
    private UserDAO userDAO;
    private TicketDAO ticketDAO;
    private LoginFrame loginFrame;
    private User currentUser;

    public MainController() {
        try {
            conn = DBConnection.getConnection();
            userDAO = new UserDAO(conn);
            ticketDAO = new TicketDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showLoginFrame();
    }

    private void showLoginFrame() {
        loginFrame = new LoginFrame();
        setupLoginListeners();
        loginFrame.setVisible(true);
    }

    private void setupLoginListeners() {
        loginFrame.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginFrame.getUsernameField().getText();
                String password = new String(loginFrame.getPasswordField().getPassword());
                try {
                    currentUser = userDAO.authenticate(username, password);
                    if (currentUser != null) {
                        loginFrame.dispose();
                        if ("admin".equals(currentUser.getRole())) {
                            showAdminDashboard();
                        } else {
                            showApplicantDashboard();
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loginFrame.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Username:");
                String password = JOptionPane.showInputDialog("Password:");
                String role = "applicant";
                String name = JOptionPane.showInputDialog("Name:");
                String email = JOptionPane.showInputDialog("Email:");
                User newUser = new User(0, username, password, role, name, email);
                try {
                    userDAO.register(newUser);
                    JOptionPane.showMessageDialog(loginFrame, "Registered successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void showApplicantDashboard() {
        TicketSubmissionFrame tsFrame = new TicketSubmissionFrame();
        tsFrame.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String issueType = (String) tsFrame.getIssueTypeBox().getSelectedItem();
                String description = tsFrame.getDescriptionArea().getText();
                Ticket ticket = new Ticket(0, currentUser.getId(), issueType, description, "Submitted", null, null,
                        null);
                try {
                    int ticketId = ticketDAO.createTicket(ticket);
                    JOptionPane.showMessageDialog(tsFrame, "Ticket submitted! ID: " + ticketId);
                    tsFrame.dispose();
                    showApplicantDashboard(); // Refresh frame after submit
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        tsFrame.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tsFrame.dispose(); // Corrected: This disposes the frame properly
                currentUser = null;
                showLoginFrame();
            }
        });
        tsFrame.getViewTicketsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Ticket> myTickets = ticketDAO.getTicketsByUser(currentUser.getId());
                    MyTicketsFrame mtFrame = new MyTicketsFrame(myTickets);
                    mtFrame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        tsFrame.setVisible(true);
    }

    private void showAdminDashboard() {
        try {
            List<Ticket> tickets = ticketDAO.getAllTickets();
            AdminDashboardFrame adFrame = new AdminDashboardFrame(tickets);
            adFrame.getRespondButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = adFrame.getTicketTable().getSelectedRow();
                    if (selectedRow >= 0) {
                        int ticketId = (int) adFrame.getTicketTable().getValueAt(selectedRow, 0);
                        String status = JOptionPane.showInputDialog("New Status:");
                        String response = JOptionPane.showInputDialog("Response:");
                        try {
                            ticketDAO.updateTicket(ticketId, status, response);
                            JOptionPane.showMessageDialog(adFrame, "Updated!");
                            adFrame.dispose();
                            showAdminDashboard(); // Refresh
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            adFrame.getLogoutButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    adFrame.dispose();
                    currentUser = null;
                    showLoginFrame();
                }
            });
            adFrame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainController();
    }
}