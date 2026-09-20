package com.ecoaccess;

import com.ecoaccess.dao.AdminDAO;
import com.ecoaccess.dao.JourneyDAO;
import com.ecoaccess.dao.StaffDAO;
import com.ecoaccess.dao.StationDAO;
import com.ecoaccess.dao.TrainDAO;
import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Journey;
import com.ecoaccess.model.Staff;
import com.ecoaccess.model.Station;
import com.ecoaccess.model.Train;

public class TestBasicDAO {

    public static void main(String[] args) {

        // Staff
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.findByEmployeeId("STF1001");

        System.out.println("\n--- STAFF ---");
        System.out.println(staff);

        // Admin
        AdminDAO adminDAO = new AdminDAO();
        Admin admin = adminDAO.findByEmail("admin@ecoaccess.com");

        System.out.println("\n--- ADMIN ---");
        System.out.println(admin);

        // Stations
        StationDAO stationDAO = new StationDAO();

        System.out.println("\n--- STATIONS ---");

        for (Station station : stationDAO.findAll()) {
            System.out.println(station);
        }

        // Trains
        TrainDAO trainDAO = new TrainDAO();

        System.out.println("\n--- TRAINS ---");

        for (Train train : trainDAO.findAll()) {
            System.out.println(train);
        }

        // Journey / PNR
        JourneyDAO journeyDAO = new JourneyDAO();

        Journey journey =
                journeyDAO.findByPnr("4521987630");

        System.out.println("\n--- JOURNEY ---");
        System.out.println(journey);
    }
}