package com.ecoaccess.model;

public class Wheelchair {
    private String id;
    private String stationId;
    private int quantity;

    public Wheelchair() {}

    public Wheelchair(String id, String stationId, int quantity) {
        this.id = id;
        this.stationId = stationId;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return id + " | station=" + stationId + " | quantity=" + quantity;
    }
}
