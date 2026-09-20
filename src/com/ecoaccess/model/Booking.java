package com.ecoaccess.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private String id;
    private String passengerId;
    private String journeyId;
    private String service;
    private String stationId;
    private int platform;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String pickupPoint;
    private String dropPlatform;
    private int passengerCount;
    private int bags;
    private String weightRange;
    private double baseFare;
    private double taxAmount;
    private double grossFare;
    private double discount;
    private double finalFare;
    private String status;
    private String staffId;

    public Booking() {
    }

    public Booking(
            String id,
            String passengerId,
            String journeyId,
            String service,
            String stationId,
            int platform,
            LocalDate bookingDate,
            LocalTime bookingTime,
            String pickupPoint,
            String dropPlatform,
            int passengerCount,
            int bags,
            String weightRange,
            double baseFare,
            double taxAmount,
            double grossFare,
            double discount,
            double finalFare,
            String status,
            String staffId) {

        this.id = id;
        this.passengerId = passengerId;
        this.journeyId = journeyId;
        this.service = service;
        this.stationId = stationId;
        this.platform = platform;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.pickupPoint = pickupPoint;
        this.dropPlatform = dropPlatform;
        this.passengerCount = passengerCount;
        this.bags = bags;
        this.weightRange = weightRange;
        this.baseFare = baseFare;
        this.taxAmount = taxAmount;
        this.grossFare = grossFare;
        this.discount = discount;
        this.finalFare = finalFare;
        this.status = status;
        this.staffId = staffId;
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

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getDropPlatform() {
        return dropPlatform;
    }

    public void setDropPlatform(String dropPlatform) {
        this.dropPlatform = dropPlatform;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public String getWeightRange() {
        return weightRange;
    }

    public void setWeightRange(String weightRange) {
        this.weightRange = weightRange;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getGrossFare() {
        return grossFare;
    }

    public void setGrossFare(double grossFare) {
        this.grossFare = grossFare;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalFare() {
        return finalFare;
    }

    public void setFinalFare(double finalFare) {
        this.finalFare = finalFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", service='" + service + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", status='" + status + '\'' +
                ", finalFare=" + finalFare +
                '}';
    }
}