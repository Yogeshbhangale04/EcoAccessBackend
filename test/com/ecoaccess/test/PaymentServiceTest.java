//package com.ecoaccess.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import com.ecoaccess.model.Payment;
//import com.ecoaccess.service.PaymentService;
//
//public class PaymentServiceTest {
//
//    private final PaymentService paymentService =
//            new PaymentService();
//
//    @Test
//    void makeUpiPaymentSuccessfully() {
//
//        String bookingId = "BK-B9BC2811";
//
//        Payment payment =
//                paymentService.makePayment(
//                        bookingId,
//                        52.50,
//                        "UPI"
//                );
//
//        assertNotNull(payment);
//        assertNotNull(payment.getId());
//
//        assertEquals(
//                bookingId,
//                payment.getBookingId()
//        );
//
//        assertEquals(
//                52.50,
//                payment.getAmount(),
//                0.01
//        );
//
//        assertEquals(
//                "UPI",
//                payment.getPaymentMethod()
//        );
//
//        assertEquals(
//                "SUCCESS",
//                payment.getPaymentStatus()
//        );
//
//        assertNotNull(payment.getTransactionId());
//        assertNotNull(payment.getPaidAt());
//    }
//
//    @Test
//    void paymentFailsForInvalidAmount() {
//
//        IllegalArgumentException exception =
//                assertThrows(
//                        IllegalArgumentException.class,
//                        () -> paymentService.makePayment(
//                                "BK-PAY-TEST-002",
//                                0,
//                                "UPI"
//                        )
//                );
//
//        assertEquals(
//                "Payment amount must be greater than zero.",
//                exception.getMessage()
//        );
//    }
//
//
//    @Test
//    void paymentFailsForInvalidMethod() {
//
//        IllegalArgumentException exception =
//                assertThrows(
//                        IllegalArgumentException.class,
//                        () -> paymentService.makePayment(
//                                "BK-PAY-TEST-003",
//                                100,
//                                "CASH"
//                        )
//                );
//
//        assertEquals(
//                "Payment method must be UPI or CARD.",
//                exception.getMessage()
//        );
//    }
//
//
//    @Test
//    void paymentFailsWithoutBookingId() {
//
//        IllegalArgumentException exception =
//                assertThrows(
//                        IllegalArgumentException.class,
//                        () -> paymentService.makePayment(
//                                "",
//                                100,
//                                "UPI"
//                        )
//                );
//
//        assertEquals(
//                "Booking ID is required.",
//                exception.getMessage()
//        );
//    }
//
//
//    @Test
//    void paymentFailsForDuplicateBooking() {
//
//        String bookingId = "BK-CCC0C0C9";
//
//        // First payment
//        paymentService.makePayment(
//                bookingId,
//                75.00,
//                "CARD"
//        );
//
//        // Second payment for the same booking
//        IllegalStateException exception =
//                assertThrows(
//                        IllegalStateException.class,
//                        () -> paymentService.makePayment(
//                                bookingId,
//                                75.00,
//                                "CARD"
//                        )
//                );
//
//        assertEquals(
//                "Payment already exists for this booking.",
//                exception.getMessage()
//        );
//    }
//}



package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Payment;
import com.ecoaccess.service.BookingService;
import com.ecoaccess.service.PaymentService;

public class PaymentServiceTest {

    private final PaymentService paymentService =
            new PaymentService();

    @Test
    void makeUpiPaymentSuccessfully() {

        BookingService bookingService =
                new BookingService();

        Booking booking =
                bookingService.createBooking(
                        "P1002",
                        "JRN002",
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

        Payment payment =
                paymentService.makePayment(
                        booking.getId(),
                        booking.getFinalFare(),
                        "UPI"
                );

        assertNotNull(payment);
        assertNotNull(payment.getId());

        assertEquals(
                booking.getId(),
                payment.getBookingId()
        );

        assertEquals(
                booking.getFinalFare(),
                payment.getAmount(),
                0.01
        );

        assertEquals(
                "UPI",
                payment.getPaymentMethod()
        );

        assertEquals(
                "SUCCESS",
                payment.getPaymentStatus()
        );

        assertNotNull(payment.getTransactionId());
        assertNotNull(payment.getPaidAt());
    }


    @Test
    void paymentFailsForInvalidAmount() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> paymentService.makePayment(
                                "ANY-BOOKING-ID",
                                0,
                                "UPI"
                        )
                );

        assertEquals(
                "Payment amount must be greater than zero.",
                exception.getMessage()
        );
    }


    @Test
    void paymentFailsForInvalidMethod() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> paymentService.makePayment(
                                "ANY-BOOKING-ID",
                                100,
                                "CASH"
                        )
                );

        assertEquals(
                "Payment method must be UPI or CARD.",
                exception.getMessage()
        );
    }


    @Test
    void paymentFailsWithoutBookingId() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> paymentService.makePayment(
                                "",
                                100,
                                "UPI"
                        )
                );

        assertEquals(
                "Booking ID is required.",
                exception.getMessage()
        );
    }


    @Test
    void paymentFailsForDuplicateBooking() {

        BookingService bookingService =
                new BookingService();

        Booking booking =
                bookingService.createBooking(
                        "P1002",
                        "JRN002",
                        "Wheelchair",
                        "Main Entrance",
                        "Platform 1",
                        1,
                        0,
                        0,
                        0
                );

        assertNotNull(booking);

        String bookingId =
                booking.getId();

        // First payment
        Payment firstPayment =
                paymentService.makePayment(
                        bookingId,
                        booking.getFinalFare(),
                        "CARD"
                );

        assertNotNull(firstPayment);

        // Second payment for the same booking
        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> paymentService.makePayment(
                                bookingId,
                                booking.getFinalFare(),
                                "CARD"
                        )
                );

        assertEquals(
                "Payment already exists for this booking.",
                exception.getMessage()
        );
    }
}