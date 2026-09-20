package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.service.BookingStatusService;

public class BookingStatusServiceTest {

    private final BookingStatusService statusService =
            new BookingStatusService();

    @Test
    void completeBookingThroughValidLifecycle() {

        String bookingId = "BK-BE48FCB5";

        assertTrue(
                statusService.updateStatus(
                        bookingId,
                        "Accepted",
                        "STF1002"
                )
        );

        assertTrue(
                statusService.updateStatus(
                        bookingId,
                        "Reached Passenger",
                        "STF1002"
                )
        );

        assertTrue(
                statusService.updateStatus(
                        bookingId,
                        "Service Started",
                        "STF1002"
                )
        );

        assertTrue(
                statusService.updateStatus(
                        bookingId,
                        "Completed",
                        "STF1002"
                )
        );
    }


    @Test
    void invalidStatusTransitionThrowsException() {

        String bookingId = "BK-240102";

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> statusService.updateStatus(
                                bookingId,
                                "Completed",
                                "STF1001"
                        )
                );

        assertTrue(
                exception.getMessage()
                        .startsWith(
                                "Invalid status transition:"
                        )
        );
    }


    @Test
    void unknownBookingThrowsException() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> statusService.updateStatus(
                                "BK-DOES-NOT-EXIST",
                                "Accepted",
                                "STF1001"
                        )
                );

        assertEquals(
                "Booking not found.",
                exception.getMessage()
        );
    }
}