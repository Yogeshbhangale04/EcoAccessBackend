package com.ecoaccess.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Journey {

    private String id;
    private String pnr;
    private String trainId;
    private String stationId;
    private int platform;
    private LocalDate journeyDate;
    private LocalTime journeyTime;
    private String source;
    private String destination;
    private String coach;
    private String travelClass;

    public Journey() {
    }

    public Journey(String id, String pnr, String trainId,
                   String stationId, int platform,
                   LocalDate journeyDate, LocalTime journeyTime,
                   String source, String destination,
                   String coach, String travelClass) {

        this.id = id;
        this.pnr = pnr;
        this.trainId = trainId;
        this.stationId = stationId;
        this.platform = platform;
        this.journeyDate = journeyDate;
        this.journeyTime = journeyTime;
        this.source = source;
        this.destination = destination;
        this.coach = coach;
        this.travelClass = travelClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
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

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public LocalTime getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(LocalTime journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id='" + id + '\'' +
                ", pnr='" + pnr + '\'' +
                ", platform=" + platform +
                ", journeyDate=" + journeyDate +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", coach='" + coach + '\'' +
                ", travelClass='" + travelClass + '\'' +
                '}';
    }
}