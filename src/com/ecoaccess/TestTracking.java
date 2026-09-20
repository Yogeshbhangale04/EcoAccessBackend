package com.ecoaccess;

import java.util.List;

import com.ecoaccess.model.Booking;
import com.ecoaccess.model.BookingStatusHistory;
import com.ecoaccess.service.TrackingService;

public class TestTracking {

    public static void main(String[] args) {

        String bookingId = "BK-CF0B465E";

        TrackingService trackingService =
                new TrackingService();

        Booking booking =
                trackingService.getBooking(bookingId);

        System.out.println(
                "\n===== BOOKING DETAILS =====");

        if (booking != null) {

            System.out.println(
                    "Booking ID: "
                    + booking.getId());

            System.out.println(
                    "Service: "
                    + booking.getService());

            System.out.println(
                    "Current Status: "
                    + booking.getStatus());

            System.out.println(
                    "Staff: "
                    + booking.getStaffId());

            System.out.println(
                    "Final Fare: ₹"
                    + booking.getFinalFare());

        } else {

            System.out.println(
                    "Booking not found.");

            return;
        }


        System.out.println(
                "\n===== STATUS TIMELINE =====");

        List<BookingStatusHistory> history =
                trackingService.getBookingHistory(
                        bookingId);

        for (BookingStatusHistory item : history) {

            System.out.println(
                    item.getOldStatus()
                    + " -> "
                    + item.getNewStatus()
                    + " | By: "
                    + item.getChangedBy()
                    + " | "
                    + item.getChangedAt()
            );
        }
    }
}