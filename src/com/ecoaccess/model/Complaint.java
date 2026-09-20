package com.ecoaccess.model;

import java.time.LocalDateTime;

public class Complaint {

    private String id;
    private String passengerId;
    private String bookingId;
    private String subject;
    private String description;
    private int rating;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Complaint(
            String id,
            String passengerId,
            String bookingId,
            String subject,
            String description,
            int rating,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.passengerId = passengerId;
        this.bookingId = bookingId;
        this.subject = subject;
        this.description = description;
        this.rating = rating;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public String getId() {
        return id;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ==========================================
    // SETTERS
    // ==========================================

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}