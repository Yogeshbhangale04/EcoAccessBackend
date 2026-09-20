package com.ecoaccess.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ecoaccess.dao.PaymentDAO;
import com.ecoaccess.model.Payment;

public class PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
    }

    public Payment makePayment(
            String bookingId,
            double amount,
            String paymentMethod) {

        if (bookingId == null || bookingId.isBlank()) {
            throw new IllegalArgumentException(
                    "Booking ID is required.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero.");
        }

        if (!"UPI".equalsIgnoreCase(paymentMethod)
                && !"CARD".equalsIgnoreCase(paymentMethod)) {

            throw new IllegalArgumentException(
                    "Payment method must be UPI or CARD.");
        }

        // Prevent duplicate payment
        Payment existing =
                paymentDAO.findByBookingId(bookingId);

        if (existing != null) {
            throw new IllegalStateException(
                    "Payment already exists for this booking.");
        }

        String paymentId =
                "PAY-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        String transactionId =
                "TXN-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        Payment payment = new Payment(
                paymentId,
                bookingId,
                amount,
                paymentMethod.toUpperCase(),
                "SUCCESS",
                transactionId,
                LocalDateTime.now()
        );

        boolean saved =
                paymentDAO.save(payment);

        if (!saved) {
            throw new IllegalStateException(
                    "Payment failed.");
        }

        return payment;
    }
}