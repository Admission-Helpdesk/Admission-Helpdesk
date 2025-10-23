package dao;

import model.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private Connection conn;

    public TicketDAO(Connection conn) {
        this.conn = conn;
    }

    public int createTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (user_id, issue_type, description) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, ticket.getUserId());
        stmt.setString(2, ticket.getIssueType());
        stmt.setString(3, ticket.getDescription());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())
            return rs.getInt(1);
        return -1;
    }

    public List<Ticket> getTicketsByUser(int userId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tickets.add(new Ticket(rs.getInt("ticket_id"), rs.getInt("user_id"), rs.getString("issue_type"),
                    rs.getString("description"), rs.getString("status"), rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at"), rs.getString("admin_response")));
        }
        return tickets;
    }

    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            tickets.add(new Ticket(rs.getInt("ticket_id"), rs.getInt("user_id"), rs.getString("issue_type"),
                    rs.getString("description"), rs.getString("status"), rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at"), rs.getString("admin_response")));
        }
        return tickets;
    }

    public void updateTicket(int ticketId, String status, String response) throws SQLException {
        String sql = "UPDATE tickets SET status = ?, admin_response = ? WHERE ticket_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, status);
        stmt.setString(2, response);
        stmt.setInt(3, ticketId);
        stmt.executeUpdate();
    }
}