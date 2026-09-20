package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Booking;
import com.ecoaccess.model.BookingStatusHistory;
import com.ecoaccess.service.TrackingService;

public class TrackingServiceTest {

    private final TrackingService trackingService =
            new TrackingService();


    @Test
    	void getExistingBookingSuccessfully() {

        Booking booking =
                trackingService.getBooking(
                        "BK-CF0B465E"
                );

        assertNotNull(booking);

        assertEquals(
                "BK-CF0B465E",
                booking.getId()
        );

        assertEquals(
                "P1002",
                booking.getPassengerId()
        );

        assertEquals(
                "JRN002",
                booking.getJourneyId()
        );
    }


    @Test
    void getNonExistingBookingReturnsNull() {

        Booking booking =
                trackingService.getBooking(
                        "BK-DOES-NOT-EXIST"
                );

        assertNull(booking);
    }


    @Test
    void getBookingHistorySuccessfully() {

        List<BookingStatusHistory> history =
                trackingService.getBookingHistory(
                        "BK-CF0B465E"
                );

        assertNotNull(history);

        assertFalse(history.isEmpty());

        assertEquals(
                "BK-CF0B465E",
                history.get(0).getBookingId()
        );
    }


    @Test
    void getHistoryForNonExistingBookingReturnsEmptyList() {

        List<BookingStatusHistory> history =
                trackingService.getBookingHistory(
                        "BK-DOES-NOT-EXIST"
                );

        assertNotNull(history);

        assertTrue(history.isEmpty());
    }
}