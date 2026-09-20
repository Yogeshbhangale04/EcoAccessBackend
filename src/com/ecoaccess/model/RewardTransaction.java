package com.ecoaccess.model;

import java.time.LocalDateTime;

public class RewardTransaction {

    private String id;
    private String passengerId;
    private String transactionType;
    private int points;
    private String referenceType;
    private String referenceId;
    private String description;
    private LocalDateTime createdAt;

    public RewardTransaction() {
    }

    public RewardTransaction(
            String id,
            String passengerId,
            String transactionType,
            int points,
            String referenceType,
            String referenceId,
            String description,
            LocalDateTime createdAt) {

        this.id = id;
        this.passengerId = passengerId;
        this.transactionType = transactionType;
        this.points = points;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.description = description;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}