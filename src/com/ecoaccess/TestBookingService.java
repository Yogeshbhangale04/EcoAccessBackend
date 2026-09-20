package com.ecoaccess;

import com.ecoaccess.model.Booking;
import com.ecoaccess.service.BookingService;

public class TestBookingService {

    public static void main(String[] args) {

        BookingService bookingService =
                new BookingService();

        try {

            Booking booking =
                    bookingService.createBooking(
                            "P1002",
                            "JRN002",
                            "Wheelchair",
                            "Main Entrance",
                            "Platform 2 Waiting Area",
                            1,
                            0,
                            0,
                            0
                    );

            System.out.println("\n===== BOOKING CREATED =====");

            System.out.println("Booking ID: "
                    + booking.getId());

            System.out.println("Passenger ID: "
                    + booking.getPassengerId());

            System.out.println("Service: "
                    + booking.getService());

            System.out.println("Station ID: "
                    + booking.getStationId());

            System.out.println("Platform: "
                    + booking.getPlatform());

            System.out.println("Base Fare: ₹"
                    + booking.getBaseFare());

            System.out.println("GST: ₹"
                    + booking.getTaxAmount());

            System.out.println("Gross Fare: ₹"
                    + booking.getGrossFare());

            System.out.println("Discount: ₹"
                    + booking.getDiscount());

            System.out.println("Final Fare: ₹"
                    + booking.getFinalFare());

            System.out.println("Status: "
                    + booking.getStatus());

            System.out.println("Staff ID: "
                    + booking.getStaffId());

        } catch (Exception e) {

            System.out.println("\n===== BOOKING FAILED =====");
            System.out.println(e.getMessage());

            e.printStackTrace();
        }
    }
}