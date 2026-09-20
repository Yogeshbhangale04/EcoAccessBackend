package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Staff;
import com.ecoaccess.service.StaffBookingService;
import com.ecoaccess.service.StaffService;

public class StaffBookingServiceTest {

    private final StaffBookingService staffBookingService =
            new StaffBookingService();

    private final StaffService staffService =
            new StaffService();


    @Test
    void getAssignedBookingsSuccessfully()
            throws SQLException {

        List<Booking> bookings =
                staffBookingService.getAssignedBookings(
                        "STF1002"
                );

        assertNotNull(bookings);

        assertFalse(
                bookings.isEmpty()
        );

        for (Booking booking : bookings) {

            assertEquals(
                    "STF1002",
                    booking.getStaffId()
            );
        }
    }


    @Test
    void completeBookingThroughFullLifecycle()
            throws SQLException {

        Staff staff =
                staffService.login(
                        "STF1002",
                        "Test@123"
                );

        assertNotNull(staff);


        // Assigned -> Accepted
        boolean accepted =
                staffBookingService.acceptBooking(
                        "BK-240101",
                        staff
                );

        assertTrue(accepted);


        // Accepted -> Reached Passenger
        boolean reached =
                staffBookingService.reachedPassenger(
                        "BK-240101",
                        staff
                );

        assertTrue(reached);


        // Reached Passenger -> Service Started
        boolean started =
                staffBookingService.startService(
                        "BK-240101",
                        staff
                );

        assertTrue(started);


        // Service Started -> Completed
        boolean completed =
                staffBookingService.completeService(
                        "BK-240101",
                        staff
                );

        assertTrue(completed);
    }


    @Test
    void nullStaffFails()
            throws SQLException {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> staffBookingService.acceptBooking(
                                "BK-240101",
                                null
                        )
                );

        assertEquals(
                "Staff information is required.",
                exception.getMessage()
        );
    }


    @Test
    void wrongStaffCannotAcceptBooking()
            throws SQLException {

        Staff staff =
                staffService.login(
                        "STF1001",
                        "Test@123"
                );

        assertNotNull(staff);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> staffBookingService.acceptBooking(
                                "BK-240101",
                                staff
                        )
                );

        assertEquals(
                "This booking is not assigned to this staff member.",
                exception.getMessage()
        );
    }


    @Test
    void nonExistingBookingFails()
            throws SQLException {

        Staff staff =
                staffService.login(
                        "STF1002",
                        "Test@123"
                );

        assertNotNull(staff);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> staffBookingService.acceptBooking(
                                "BK-DOES-NOT-EXIST",
                                staff
                        )
                );

        assertEquals(
                "Booking not found: BK-DOES-NOT-EXIST",
                exception.getMessage()
        );
    }
}