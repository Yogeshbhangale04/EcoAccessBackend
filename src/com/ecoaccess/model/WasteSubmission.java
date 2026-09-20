package com.ecoaccess.model;

import java.time.LocalDateTime;

public class WasteSubmission {

    private String id;
    private String passengerId;
    private String photo;
    private String status;
    private int rewardPoints;
    private String remark;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private String reviewedBy;

    public WasteSubmission(
            String id,
            String passengerId,
            String photo,
            String status,
            int rewardPoints,
            String remark,
            LocalDateTime submittedAt,
            LocalDateTime reviewedAt,
            String reviewedBy) {

        this.id = id;
        this.passengerId = passengerId;
        this.photo = photo;
        this.status = status;
        this.rewardPoints = rewardPoints;
        this.remark = remark;
        this.submittedAt = submittedAt;
        this.reviewedAt = reviewedAt;
        this.reviewedBy = reviewedBy;
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

    public String getPhoto() {
        return photo;
    }

    public String getStatus() {
        return status;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public String getRemark() {
        return remark;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    // ==========================================
    // SETTERS
    // ==========================================

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }
}