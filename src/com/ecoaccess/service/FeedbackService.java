package com.ecoaccess.service;

import com.ecoaccess.dao.BookingDAO;
import com.ecoaccess.dao.FeedbackDAO;
import com.ecoaccess.dao.PassengerDAO;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Feedback;
import com.ecoaccess.model.Passenger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FeedbackService {

    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final PassengerDAO passengerDAO = new PassengerDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    // Create feedback
    public Feedback createFeedback(
            String passengerId,
            String bookingId,
            int rating,
            String subject,
            String description) throws SQLException {

        // Validate passenger
        Passenger passenger = passengerDAO.findById(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger not found: " + passengerId);
        }

        // Validate booking
        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found: " + bookingId);
        }

        // Make sure booking belongs to passenger
        if (!passengerId.equals(booking.getPassengerId())) {
            throw new IllegalArgumentException(
                    "Booking does not belong to this passenger.");
        }

        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5.");
        }

        // Validate subject
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Feedback subject is required.");
        }

        // Validate description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Feedback description is required.");
        }

        Feedback feedback = new Feedback();

        feedback.setId(
                "FDB-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        feedback.setPassengerId(passengerId);
        feedback.setBookingId(bookingId);
        feedback.setRating(rating);
        feedback.setSubject(subject);
        feedback.setDescription(description);
        feedback.setStatus("Submitted");
        feedback.setCreatedAt(LocalDateTime.now());

        feedbackDAO.save(feedback);

        return feedback;
    }

    // Get feedback by ID
    public Feedback getFeedback(String feedbackId)
            throws SQLException {

        Feedback feedback = feedbackDAO.findById(feedbackId);

        if (feedback == null) {
            throw new IllegalArgumentException(
                    "Feedback not found: " + feedbackId);
        }

        return feedback;
    }

    // Get passenger's feedback
    public List<Feedback> getPassengerFeedback(
            String passengerId) throws SQLException {

        Passenger passenger = passengerDAO.findById(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger not found: " + passengerId);
        }

        return feedbackDAO.findByPassenger(passengerId);
    }

    // Get feedback for a booking
    public List<Feedback> getBookingFeedback(
            String bookingId) throws SQLException {

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found: " + bookingId);
        }

        return feedbackDAO.findByBooking(bookingId);
    }

    // Get all submitted feedback
    public List<Feedback> getSubmittedFeedback()
            throws SQLException {

        return feedbackDAO.findSubmitted();
    }

    // Mark feedback as reviewed
    public void reviewFeedback(String feedbackId)
            throws SQLException {

        Feedback feedback = feedbackDAO.findById(feedbackId);

        if (feedback == null) {
            throw new IllegalArgumentException(
                    "Feedback not found: " + feedbackId);
        }

        if (!"Submitted".equals(feedback.getStatus())) {
            throw new IllegalArgumentException(
                    "Only submitted feedback can be reviewed.");
        }

        feedbackDAO.updateStatus(feedbackId, "Reviewed");
    }
}