package view; // Change to 'package admissions;' if using flat structure

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.List;
import model.Ticket; // Adjust import if needed

public class AdminDashboardFrame extends JFrame {
    private JTable ticketTable;
    private JButton respondButton;
    private JButton logoutButton;

    public AdminDashboardFrame(List<Ticket> tickets) {
        setTitle("Admin Dashboard");
        setSize(900, 500); // Increased width for better readability
        setLayout(null);

        String[] columns = { "Ticket ID", "User ID", "Issue Type", "Status", "Description" };
        Object[][] data = new Object[tickets.size()][5];
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            data[i] = new Object[] {
                t.getTicketId(),
                t.getUserId(),
                t.getIssueType(),
                t.getStatus(),
                t.getDescription()
            };
        }

        ticketTable = new JTable(data, columns);
        ticketTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto column shrinking

        // Adjust column widths
        TableColumnModel columnModel = ticketTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);   // Ticket ID
        columnModel.getColumn(1).setPreferredWidth(100);  // User ID
        columnModel.getColumn(2).setPreferredWidth(120);  // Issue Type
        columnModel.getColumn(3).setPreferredWidth(120);  // Status
        columnModel.getColumn(4).setPreferredWidth(400);  // Description

        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBounds(20, 20, 850, 350);

        respondButton = new JButton("Respond to Selected");
        respondButton.setBounds(200, 400, 180, 30);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(420, 400, 100, 30);

        add(scrollPane);
        add(respondButton);
        add(logoutButton);

        setLocationRelativeTo(null); // Center on screen
    }

    // Getters
    public JTable getTicketTable() {
        return ticketTable;
    }

    public JButton getRespondButton() {
        return respondButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }
}
