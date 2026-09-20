package com.ecoaccess;

import com.ecoaccess.model.Payment;
import com.ecoaccess.service.PaymentService;

public class TestPayment {

    public static void main(String[] args) {

        PaymentService paymentService =
                new PaymentService();

        try {

            Payment payment =
                    paymentService.makePayment(
                            "BK-CF0B465E",
                            52.50,
                            "UPI"
                    );

            System.out.println(
                    "\n===== PAYMENT SUCCESS =====");

            System.out.println(
                    "Payment ID: "
                    + payment.getId());

            System.out.println(
                    "Booking ID: "
                    + payment.getBookingId());

            System.out.println(
                    "Amount: ₹"
                    + payment.getAmount());

            System.out.println(
                    "Method: "
                    + payment.getPaymentMethod());

            System.out.println(
                    "Status: "
                    + payment.getPaymentStatus());

            System.out.println(
                    "Transaction ID: "
                    + payment.getTransactionId());

        } catch (Exception e) {

            System.out.println(
                    "\n===== PAYMENT FAILED =====");

            System.out.println(
                    e.getMessage());
        }
    }
}