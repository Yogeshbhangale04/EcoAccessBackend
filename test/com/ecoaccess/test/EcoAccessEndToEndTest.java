package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Complaint;
import com.ecoaccess.model.Feedback;
import com.ecoaccess.model.Journey;
import com.ecoaccess.model.Passenger;
import com.ecoaccess.model.Payment;
import com.ecoaccess.model.Staff;

import com.ecoaccess.service.AdminDashboardService;
import com.ecoaccess.service.AdminService;
import com.ecoaccess.service.BookingService;
import com.ecoaccess.service.ComplaintService;
import com.ecoaccess.service.FeedbackService;
import com.ecoaccess.service.JourneyValidationService;
import com.ecoaccess.service.PassengerService;
import com.ecoaccess.service.PaymentService;
import com.ecoaccess.service.StaffBookingService;
import com.ecoaccess.service.StaffService;
import com.ecoaccess.service.TrackingService;

public class EcoAccessEndToEndTest {

    @Test
    void completeEcoAccessFlow() throws Exception {

        System.out.println("==========================================");
        System.out.println("     ECOACCESS END-TO-END TEST");
        System.out.println("==========================================");

        /*
         * 1. PASSENGER LOGIN
         */
        PassengerService passengerService = new PassengerService();

        Passenger passenger = passengerService.login(
                "+919988776655",
                "Test@123"
        );

        assertNotNull(passenger);
        assertEquals("P1002", passenger.getId());

        System.out.println("1. Passenger Login              : PASSED");


        /*
         * 2. PNR VALIDATION
         */
        JourneyValidationService journeyValidationService =
                new JourneyValidationService();

        Journey journey =
                journeyValidationService.validateJourney(
                        passenger.getId(),
                        "6109873421"
                );

        assertNotNull(journey);
        assertEquals("JRN002", journey.getId());

        System.out.println("2. PNR Validation               : PASSED");


        /*
         * 3. CREATE BOOKING
         *
         * Wheelchair service:
         * Base fare = ₹50
         * GST = 5%
         */
        BookingService bookingService = new BookingService();

        Booking booking = bookingService.createBooking(
                passenger.getId(),
                journey.getId(),
                "Wheelchair",
                "Main Entrance",
                "Platform 1",
                1,
                0,
                0,
                0
        );

        assertNotNull(booking);
        assertNotNull(booking.getId());

        assertEquals("P1002", booking.getPassengerId());
        assertEquals("JRN002", booking.getJourneyId());
        assertEquals("Wheelchair", booking.getService());

        System.out.println(
                "3. Booking Created              : PASSED"
        );
        System.out.println(
                "   Booking ID                    : "
                + booking.getId()
        );
        System.out.println(
                "   Fare                          : ₹"
                + booking.getFinalFare()
        );
        System.out.println(
                "   Staff                         : "
                + booking.getStaffId()
        );


        /*
         * 4. VERIFY STAFF ASSIGNMENT
         */
        assertNotNull(
                booking.getStaffId(),
                "Booking should have a staff member assigned."
        );

        assertEquals(
                "Assigned",
                booking.getStatus()
        );

        System.out.println(
                "4. Staff Assignment              : PASSED"
        );


        /*
         * 5. PAYMENT
         */
        PaymentService paymentService = new PaymentService();

        Payment payment = paymentService.makePayment(
                booking.getId(),
                booking.getFinalFare(),
                "UPI"
        );

        assertNotNull(payment);
        assertEquals(
                booking.getId(),
                payment.getBookingId()
        );
        assertEquals(
                "SUCCESS",
                payment.getPaymentStatus()
        );

        System.out.println(
                "5. Payment                       : PASSED"
        );
        System.out.println(
                "   Payment ID                    : "
                + payment.getId()
        );


        /*
         * 6. STAFF LOGIN
         */
        StaffService staffService = new StaffService();

        Staff staff = staffService.login(
                "STF1002",
                "Test@123"
        );

        assertNotNull(staff);
        assertEquals("STF1002", staff.getId());

        System.out.println(
                "6. Staff Login                  : PASSED"
        );


        /*
         * 7. STAFF ACCEPTS BOOKING
         */
        StaffBookingService staffBookingService =
                new StaffBookingService();

        boolean accepted =
                staffBookingService.acceptBooking(
                        booking.getId(),
                        staff
                );

        assertTrue(accepted);

        System.out.println(
                "7. Staff Accepted Booking       : PASSED"
        );


        /*
         * 8. STAFF REACHES PASSENGER
         */
        boolean reached =
                staffBookingService.reachedPassenger(
                        booking.getId(),
                        staff
                );

        assertTrue(reached);

        System.out.println(
                "8. Staff Reached Passenger      : PASSED"
        );


        /*
         * 9. SERVICE STARTED
         */
        boolean started =
                staffBookingService.startService(
                        booking.getId(),
                        staff
                );

        assertTrue(started);

        System.out.println(
                "9. Service Started              : PASSED"
        );


        /*
         * 10. SERVICE COMPLETED
         */
        boolean completed =
                staffBookingService.completeService(
                        booking.getId(),
                        staff
                );

        assertTrue(completed);

        System.out.println(
                "10. Service Completed           : PASSED"
        );


        /*
         * 11. VERIFY TRACKING
         */
        TrackingService trackingService =
                new TrackingService();

        Booking trackedBooking =
                trackingService.getBooking(
                        booking.getId()
                );

        assertNotNull(trackedBooking);

        assertEquals(
                "Completed",
                trackedBooking.getStatus()
        );

        System.out.println(
                "11. Tracking / Final Status     : PASSED"
        );


        /*
         * 12. CREATE FEEDBACK
         */
        FeedbackService feedbackService =
                new FeedbackService();

        Feedback feedback =
                feedbackService.createFeedback(
                        passenger.getId(),
                        booking.getId(),
                        5,
                        "Wheelchair Service",
                        "Service was completed successfully."
                );

        assertNotNull(feedback);
        assertEquals(
                "Submitted",
                feedback.getStatus()
        );

        System.out.println(
                "12. Feedback Submitted           : PASSED"
        );


        /*
         * 13. CREATE COMPLAINT
         */
        ComplaintService complaintService =
                new ComplaintService();

        Complaint complaint =
                complaintService.createComplaint(
                        passenger.getId(),
                        booking.getId(),
                        "Service Feedback",
                        "Test complaint for end-to-end validation.",
                        3
                );

        assertNotNull(complaint);
        assertEquals(
                "Open",
                complaint.getStatus()
        );

        System.out.println(
                "13. Complaint Created            : PASSED"
        );


        /*
         * 14. ADMIN LOGIN
         */
        AdminService adminService = new AdminService();

        Admin admin = adminService.login(
                "admin@ecoaccess.com",
                "Test@123"
        );

        assertNotNull(admin);
        assertEquals("ADM1", admin.getId());

        System.out.println(
                "14. Admin Login                  : PASSED"
        );


        /*
         * 15. ADMIN REVIEWS FEEDBACK
         */
        AdminDashboardService adminDashboardService =
                new AdminDashboardService();

        adminDashboardService.reviewFeedback(
                admin,
                feedback.getId()
        );

        Feedback reviewedFeedback =
                feedbackService.getFeedback(
                        feedback.getId()
                );

        assertEquals(
                "Reviewed",
                reviewedFeedback.getStatus()
        );

        System.out.println(
                "15. Admin Reviewed Feedback     : PASSED"
        );


        /*
         * 16. ADMIN RESOLVES COMPLAINT
         */
        adminDashboardService.resolveComplaint(
                admin,
                complaint.getId()
        );

        Complaint resolvedComplaint =
                complaintService.getComplaint(
                        complaint.getId()
                );

        assertEquals(
                "Resolved",
                resolvedComplaint.getStatus()
        );

        System.out.println(
                "16. Admin Resolved Complaint    : PASSED"
        );

       

        /*
         * 17. ADMIN CLOSES COMPLAINT
         */

        adminDashboardService.closeComplaint(
                admin,
                complaint.getId()
        );

        Complaint closedComplaint =
                complaintService.getComplaint(
                        complaint.getId()
                );

        assertEquals(
                "Closed",
                closedComplaint.getStatus()
        );

        System.out.println(
                "17. Admin Closed Complaint      : PASSED"
        );
       


        /*
         * FINAL RESULT
         */
        System.out.println();
        System.out.println("==========================================");
        System.out.println("   ECOACCESS END-TO-END TEST PASSED");
        System.out.println("==========================================");
    }
}