package com.ecoaccess;

import com.ecoaccess.dao.PassengerDAO;
import com.ecoaccess.model.Passenger;

public class TestPassengerDAO {

    public static void main(String[] args) {

        PassengerDAO passengerDAO = new PassengerDAO();

        Passenger passenger =
                passengerDAO.findByMobile("+919988776655");

        if (passenger != null) {

            System.out.println("Passenger Found!");
            System.out.println("ID: " + passenger.getId());
            System.out.println("Name: " + passenger.getName());
            System.out.println("Mobile: " + passenger.getMobile());
            System.out.println("Email: " + passenger.getEmail());
            System.out.println("Reward Points: " + passenger.getRewardPoints());

        } else {

            System.out.println("Passenger Not Found!");
        }
    }
}