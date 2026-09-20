package com.ecoaccess;

import com.ecoaccess.service.BookingStatusService;

public class TestBookingStatus {

    public static void main(String[] args) {

        BookingStatusService statusService =
                new BookingStatusService();

        String bookingId = "BK-CF0B465E";

        try {

            System.out.println("\n===== STATUS WORKFLOW =====");

            // Assigned -> Accepted
            boolean accepted =
                    statusService.updateStatus(
                            bookingId,
                            "Accepted",
                            "STF1002"
                    );

            System.out.println(
                    "Assigned -> Accepted: "
                    + accepted);


            // Accepted -> Reached Passenger
            boolean reached =
                    statusService.updateStatus(
                            bookingId,
                            "Reached Passenger",
                            "STF1002"
                    );

            System.out.println(
                    "Accepted -> Reached Passenger: "
                    + reached);


            // Reached Passenger -> Service Started
            boolean started =
                    statusService.updateStatus(
                            bookingId,
                            "Service Started",
                            "STF1002"
                    );

            System.out.println(
                    "Reached Passenger -> Service Started: "
                    + started);


            // Service Started -> Completed
            boolean completed =
                    statusService.updateStatus(
                            bookingId,
                            "Completed",
                            "STF1002"
                    );

            System.out.println(
                    "Service Started -> Completed: "
                    + completed);


            System.out.println(
                    "\n===== WORKFLOW COMPLETED =====");

        } catch (Exception e) {

            System.out.println(
                    "\n===== WORKFLOW FAILED =====");

            System.out.println(
                    e.getMessage());

            e.printStackTrace();
        }
    }
}