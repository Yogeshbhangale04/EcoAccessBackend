package com.ecoaccess;

import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Staff;
import com.ecoaccess.service.StaffBookingService;
import com.ecoaccess.service.StaffService;

import java.util.List;

public class StaffBookingTest {

    public static void main(String[] args) {

        StaffService staffService = new StaffService();
        StaffBookingService staffBookingService =
                new StaffBookingService();

        try {

            // ==========================================
            // 1. STAFF LOGIN
            // ==========================================

            System.out.println("===== STAFF LOGIN =====");

            Staff staff = staffService.login(
                    "STF1002",
                    "Test@123"
            );

            if (staff == null) {
                throw new RuntimeException(
                        "Staff login failed.");
            }

            System.out.println("Staff ID: " + staff.getId());
            System.out.println("Employee ID: "
                    + staff.getEmployeeId());
            System.out.println("Name: " + staff.getName());
            System.out.println("Role: " + staff.getRole());
            System.out.println("Status: " + staff.getStatus());


            // ==========================================
            // 2. VIEW ASSIGNED BOOKINGS
            // ==========================================

            System.out.println("\n===== ASSIGNED BOOKINGS =====");

            List<Booking> bookings =
                    staffBookingService.getAssignedBookings(
                            staff.getId());

            for (Booking booking : bookings) {

                System.out.println(
                        booking.getId()
                        + " | Service: "
                        + booking.getService()
                        + " | Status: "
                        + booking.getStatus()
                );
            }


            // ==========================================
            // 3. FIND AN ASSIGNED BOOKING
            // ==========================================

            Booking assignedBooking = null;

            for (Booking booking : bookings) {

                if ("Assigned".equals(booking.getStatus())) {
                    assignedBooking = booking;
                    break;
                }
            }

            if (assignedBooking == null) {

                System.out.println(
                        "\nNo Assigned booking available for testing.");

                return;
            }

            String bookingId = assignedBooking.getId();

            System.out.println(
                    "\nTesting Booking: " + bookingId);


            // ==========================================
            // 4. ACCEPT
            // ==========================================

            System.out.println("\n===== ACCEPT BOOKING =====");

            boolean accepted =
                    staffBookingService.acceptBooking(
                            bookingId,
                            staff
                    );

            System.out.println(
                    "Accepted: " + accepted);


            // ==========================================
            // 5. REACHED PASSENGER
            // ==========================================

            System.out.println("\n===== REACHED PASSENGER =====");

            boolean reached =
                    staffBookingService.reachedPassenger(
                            bookingId,
                            staff
                    );

            System.out.println(
                    "Reached Passenger: " + reached);


            // ==========================================
            // 6. START SERVICE
            // ==========================================

            System.out.println("\n===== START SERVICE =====");

            boolean started =
                    staffBookingService.startService(
                            bookingId,
                            staff
                    );

            System.out.println(
                    "Service Started: " + started);


            // ==========================================
            // 7. COMPLETE SERVICE
            // ==========================================

            System.out.println("\n===== COMPLETE SERVICE =====");

            boolean completed =
                    staffBookingService.completeService(
                            bookingId,
                            staff
                    );

            System.out.println(
                    "Completed: " + completed);


            // ==========================================
            // 8. FINAL STATUS
            // ==========================================

            Booking finalBooking =
                    staffBookingService
                            .getAssignedBookings(staff.getId())
                            .stream()
                            .filter(b -> bookingId.equals(b.getId()))
                            .findFirst()
                            .orElse(null);

            System.out.println("\n===== FINAL STATUS =====");

            if (finalBooking != null) {

                System.out.println(
                        "Booking: " + finalBooking.getId());

                System.out.println(
                        "Final Status: "
                        + finalBooking.getStatus());
            }


            System.out.println(
                    "\n======================================");

            System.out.println(
                    "STAFF BOOKING TEST COMPLETED SUCCESSFULLY");

            System.out.println(
                    "======================================");


        } catch (Exception e) {

            System.out.println(
                    "\nStaff booking test failed!");

            e.printStackTrace();
        }
    }
}