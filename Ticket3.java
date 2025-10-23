package model;

import java.sql.Timestamp;

public class Ticket {
    private int ticketId, userId;
    private String issueType, description, status, adminResponse;
    private Timestamp createdAt, updatedAt;

    // Constructor
    public Ticket(int ticketId, int userId, String issueType, String description, String status, Timestamp createdAt,
            Timestamp updatedAt, String adminResponse) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.issueType = issueType;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.adminResponse = adminResponse;
    }

    // Getters
    public int getTicketId() {
        return ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getAdminResponse() {
        return adminResponse;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Setters (optional, but useful for updates)
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}