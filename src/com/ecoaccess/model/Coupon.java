package com.ecoaccess.model;

import java.time.LocalDateTime;

public class Coupon {

    private String id;
    private String code;
    private String passengerId;
    private int pointsUsed;
    private double couponValue;
    private double remainingValue;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Coupon(
            String id,
            String code,
            String passengerId,
            int pointsUsed,
            double couponValue,
            double remainingValue,
            String status,
            LocalDateTime createdAt,
            LocalDateTime expiresAt) {

        this.id = id;
        this.code = code;
        this.passengerId = passengerId;
        this.pointsUsed = pointsUsed;
        this.couponValue = couponValue;
        this.remainingValue = remainingValue;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    public double getCouponValue() {
        return couponValue;
    }

    public double getRemainingValue() {
        return remainingValue;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    // Setters

    public void setRemainingValue(double remainingValue) {
        this.remainingValue = remainingValue;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}