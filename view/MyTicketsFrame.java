package view;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.List;
import model.Ticket;

public class MyTicketsFrame extends JFrame {
    public MyTicketsFrame(List<Ticket> tickets) {
        setTitle("My Tickets");
        setSize(900, 400);
        setLayout(null);

        String[] columns = { "Ticket ID", "Issue Type", "Status", "Description", "Admin Response" };
        Object[][] data = new Object[tickets.size()][5];

        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            String response = t.getAdminResponse() != null ? t.getAdminResponse() : "No response yet";
            data[i] = new Object[] { 
                t.getTicketId(), 
                t.getIssueType(), 
                t.getStatus(), 
                t.getDescription(), 
                response 
            };
        }

        JTable ticketTable = new JTable(data, columns);
        ticketTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Prevent automatic shrinking

        // Adjust column widths manually
        TableColumnModel columnModel = ticketTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);   // Ticket ID
        columnModel.getColumn(1).setPreferredWidth(100);  // Issue Type
        columnModel.getColumn(2).setPreferredWidth(100);  // Status
        columnModel.getColumn(3).setPreferredWidth(300);  // Description
        columnModel.getColumn(4).setPreferredWidth(300);  // Admin Response

        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBounds(20, 30, 850, 280);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(380, 320, 100, 30);

        add(scrollPane);
        add(closeButton);

        closeButton.addActionListener(e -> dispose());
        setLocationRelativeTo(null); // Center on screen
    }
}
