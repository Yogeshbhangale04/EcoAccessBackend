package com.ecoaccess.service;

import com.ecoaccess.dao.PassengerDAO;
import com.ecoaccess.model.Passenger;

public class PassengerService {

    private final PassengerDAO passengerDAO;

    public PassengerService() {
        this.passengerDAO = new PassengerDAO();
    }

    public Passenger login(String mobile, String password) {

        Passenger passenger = passengerDAO.findByMobile(mobile);

        if (passenger == null) {
            return null;
        }

        if (!passenger.getPassword().equals(password)) {
            return null;
        }

        return passenger;
    }


    public Passenger findByEmail(String email) {

        return passengerDAO.findByEmail(email);
    }
}