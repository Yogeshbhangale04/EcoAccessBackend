package com.ecoaccess.model;

public class Train {

    private String id;
    private String trainNumber;
    private String trainName;

    public Train() {
    }

    public Train(String id, String trainNumber, String trainName) {
        this.id = id;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id='" + id + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                '}';
    }
}