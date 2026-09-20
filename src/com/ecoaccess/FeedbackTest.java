package com.ecoaccess;

import com.ecoaccess.model.Feedback;
import com.ecoaccess.service.FeedbackService;

import java.util.List;

public class FeedbackTest {

    public static void main(String[] args) {

        FeedbackService feedbackService = new FeedbackService();

        try {

            String passengerId = "P1002";
            String bookingId = "BK-CF0B465E";

            // ==========================================
            // 1. CREATE FEEDBACK
            // ==========================================

            System.out.println("===== CREATE FEEDBACK =====");

            Feedback feedback = feedbackService.createFeedback(
                    passengerId,
                    bookingId,
                    4,
                    "Good wheelchair service",
                    "The staff was helpful and the service was completed properly."
            );

            System.out.println("Feedback ID: " + feedback.getId());
            System.out.println("Passenger: " + feedback.getPassengerId());
            System.out.println("Booking: " + feedback.getBookingId());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Subject: " + feedback.getSubject());
            System.out.println("Status: " + feedback.getStatus());
            System.out.println("Created At: " + feedback.getCreatedAt());


            // ==========================================
            // 2. GET FEEDBACK
            // ==========================================

            System.out.println("\n===== GET FEEDBACK =====");

            Feedback savedFeedback =
                    feedbackService.getFeedback(feedback.getId());

            System.out.println("Feedback ID: "
                    + savedFeedback.getId());

            System.out.println("Description: "
                    + savedFeedback.getDescription());

            System.out.println("Status: "
                    + savedFeedback.getStatus());


            // ==========================================
            // 3. GET PASSENGER FEEDBACK
            // ==========================================

            System.out.println("\n===== PASSENGER FEEDBACK =====");

            List<Feedback> passengerFeedback =
                    feedbackService.getPassengerFeedback(passengerId);

            for (Feedback f : passengerFeedback) {

                System.out.println(
                        f.getId()
                        + " | Rating: "
                        + f.getRating()
                        + " | "
                        + f.getSubject()
                        + " | "
                        + f.getStatus()
                );
            }


            // ==========================================
            // 4. GET BOOKING FEEDBACK
            // ==========================================

            System.out.println("\n===== BOOKING FEEDBACK =====");

            List<Feedback> bookingFeedback =
                    feedbackService.getBookingFeedback(bookingId);

            for (Feedback f : bookingFeedback) {

                System.out.println(
                        f.getId()
                        + " | Rating: "
                        + f.getRating()
                        + " | "
                        + f.getSubject()
                );
            }


            // ==========================================
            // 5. GET SUBMITTED FEEDBACK
            // ==========================================

            System.out.println("\n===== SUBMITTED FEEDBACK =====");

            List<Feedback> submitted =
                    feedbackService.getSubmittedFeedback();

            for (Feedback f : submitted) {

                System.out.println(
                        f.getId()
                        + " | "
                        + f.getPassengerId()
                        + " | Rating: "
                        + f.getRating()
                        + " | "
                        + f.getStatus()
                );
            }


            // ==========================================
            // 6. REVIEW FEEDBACK
            // ==========================================

            System.out.println("\n===== REVIEW FEEDBACK =====");

            feedbackService.reviewFeedback(feedback.getId());

            Feedback reviewed =
                    feedbackService.getFeedback(feedback.getId());

            System.out.println(
                    "Feedback status after review: "
                    + reviewed.getStatus()
            );


            // ==========================================
            // COMPLETE
            // ==========================================

            System.out.println("\n=================================");
            System.out.println("FEEDBACK TEST COMPLETED SUCCESSFULLY");
            System.out.println("=================================");

        } catch (Exception e) {

            System.out.println("\nFeedback test failed!");
            e.printStackTrace();
        }
    }
}