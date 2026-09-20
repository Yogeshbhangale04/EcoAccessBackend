package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Booking;
import com.ecoaccess.service.BookingService;

public class BookingServiceTest {

    private final BookingService bookingService =
            new BookingService();

    @Test
    void createWheelchairBookingSuccessfully() {

        Booking booking =
                bookingService.createBooking(
                        "P1002",
                        "JRN002",
                        "Wheelchair",
                        "Main Entrance",
                        "Platform 2",
                        1,
                        0,
                        0,
                        0
                );

        assertNotNull(booking);

        assertNotNull(booking.getId());

        assertEquals(
                "P1002",
                booking.getPassengerId()
        );

        assertEquals(
                "JRN002",
                booking.getJourneyId()
        );

        assertEquals(
                "Wheelchair",
                booking.getService()
        );

        assertEquals(
                "Assigned",
                booking.getStatus()
        );

        assertEquals(
                50.0,
                booking.getBaseFare(),
                0.01
        );

        assertEquals(
                2.5,
                booking.getTaxAmount(),
                0.01
        );

        assertEquals(
                52.5,
                booking.getFinalFare(),
                0.01
        );
    }


    @Test
    void bookingFailsWhenJourneyIsNotValidated() {

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> bookingService.createBooking(
                                "P1002",
                                "JRN003",
                                "Wheelchair",
                                "Main Entrance",
                                "Platform 1",
                                1,
                                0,
                                0,
                                0
                        )
                );

        assertEquals(
                "Passenger must validate the journey before booking.",
                exception.getMessage()
        );
    }


    @Test
    void bookingFailsForMoreThanEightPassengers() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookingService.createBooking(
                                "P1002",
                                "JRN002",
                                "Wheelchair",
                                "Main Entrance",
                                "Platform 2",
                                9,
                                0,
                                0,
                                0
                        )
                );

        assertEquals(
                "Passenger count must be between 1 and 8.",
                exception.getMessage()
        );
    }


    @Test
    void bookingFailsForMoreThanTwentyBags() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookingService.createBooking(
                                "P1002",
                                "JRN002",
                                "Porter",
                                "Main Entrance",
                                "Platform 2",
                                1,
                                21,
                                10,
                                0
                        )
                );

        assertEquals(
                "Number of bags must be between 0 and 20.",
                exception.getMessage()
        );
    }


    @Test
    void bookingFailsForInvalidService() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookingService.createBooking(
                                "P1002",
                                "JRN002",
                                "Invalid Service",
                                "Main Entrance",
                                "Platform 2",
                                1,
                                0,
                                0,
                                0
                        )
                );

        assertEquals(
                "Invalid service.",
                exception.getMessage()
        );
    }
}