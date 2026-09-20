package com.ecoaccess;

import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Journey;
import com.ecoaccess.model.Passenger;
import com.ecoaccess.model.Staff;
import com.ecoaccess.service.AdminService;
import com.ecoaccess.service.JourneyValidationService;
import com.ecoaccess.service.PassengerService;
import com.ecoaccess.service.StaffService;

public class TestServices {

    public static void main(String[] args) {

        // =========================
        // PASSENGER LOGIN
        // =========================

        PassengerService passengerService =
                new PassengerService();

        Passenger passenger =
                passengerService.login(
                        "+919988776655",
                        "Test@123");

        System.out.println("\n--- PASSENGER LOGIN ---");

        if (passenger != null) {
            System.out.println("Login Successful");
            System.out.println(passenger);
        } else {
            System.out.println("Login Failed");
        }


        // =========================
        // STAFF LOGIN
        // =========================

        StaffService staffService =
                new StaffService();

        Staff staff =
                staffService.login(
                        "STF1001",
                        "Test@123");

        System.out.println("\n--- STAFF LOGIN ---");

        if (staff != null) {
            System.out.println("Login Successful");
            System.out.println(staff);
        } else {
            System.out.println("Login Failed");
        }


        // =========================
        // ADMIN LOGIN
        // =========================

        AdminService adminService =
                new AdminService();

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123");

        System.out.println("\n--- ADMIN LOGIN ---");

        if (admin != null) {
            System.out.println("Login Successful");
            System.out.println(admin);
        } else {
            System.out.println("Login Failed");
        }


        // =========================
        // JOURNEY VALIDATION
        // =========================

        JourneyValidationService journeyService =
                new JourneyValidationService();

        Journey journey =
                journeyService.validateJourney(
                        "P1002",
                        "6109873421");

        System.out.println("\n--- JOURNEY VALIDATION ---");

        if (journey != null) {
            System.out.println("Journey Validated Successfully");
            System.out.println(journey);
        } else {
            System.out.println("Journey Validation Failed");
        }
    }
}