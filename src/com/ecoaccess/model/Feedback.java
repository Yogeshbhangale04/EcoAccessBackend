package com.ecoaccess.model;

import java.time.LocalDateTime;

public class Feedback {

    private String id;
    private String passengerId;
    private String bookingId;
    private int rating;
    private String subject;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public Feedback() {
    }

    public Feedback(String id, String passengerId, String bookingId,
                    int rating, String subject, String description,
                    String status, LocalDateTime createdAt) {

        this.id = id;
        this.passengerId = passengerId;
        this.bookingId = bookingId;
        this.rating = rating;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", rating=" + rating +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}