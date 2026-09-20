package com.ecoaccess.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ecoaccess.dao.BookingDAO;
import com.ecoaccess.dao.ComplaintDAO;
import com.ecoaccess.dao.PassengerDAO;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Complaint;
import com.ecoaccess.model.Passenger;

public class ComplaintService {

    private final ComplaintDAO complaintDAO;
    private final PassengerDAO passengerDAO;
    private final BookingDAO bookingDAO;

    public ComplaintService() {
        this.complaintDAO = new ComplaintDAO();
        this.passengerDAO = new PassengerDAO();
        this.bookingDAO = new BookingDAO();
    }

    // ==========================================
    // CREATE COMPLAINT
    // ==========================================

    public Complaint createComplaint(
            String passengerId,
            String bookingId,
            String subject,
            String description,
            int rating) {

        // Validate passenger
        Passenger passenger =
                passengerDAO.findById(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger not found.");
        }

        // Validate booking
        Booking booking =
                bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found.");
        }

        // Make sure booking belongs to passenger
        if (!passengerId.equals(
                booking.getPassengerId())) {

            throw new IllegalArgumentException(
                    "Booking does not belong to passenger.");
        }

        // Validate subject
        if (subject == null ||
                subject.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Complaint subject is required.");
        }

        // Validate description
        if (description == null ||
                description.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Complaint description is required.");
        }

        // Validate rating
        if (rating < 1 || rating > 5) {

            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5.");
        }

        String complaintId =
                "CMP-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        LocalDateTime now =
                LocalDateTime.now();

        Complaint complaint =
                new Complaint(
                        complaintId,
                        passengerId,
                        bookingId,
                        subject,
                        description,
                        rating,
                        "Open",
                        now,
                        now
                );

        boolean saved =
                complaintDAO.save(complaint);

        if (!saved) {
            throw new IllegalStateException(
                    "Failed to create complaint.");
        }

        return complaint;
    }

    // ==========================================
    // GET COMPLAINT
    // ==========================================

    public Complaint getComplaint(
            String complaintId) {

        return complaintDAO.findById(
                complaintId);
    }

    // ==========================================
    // GET PASSENGER COMPLAINTS
    // ==========================================

    public List<Complaint> getPassengerComplaints(
            String passengerId) {

        return complaintDAO.findByPassenger(
                passengerId);
    }

    // ==========================================
    // GET BOOKING COMPLAINTS
    // ==========================================

    public List<Complaint> getBookingComplaints(
            String bookingId) {

        return complaintDAO.findByBooking(
                bookingId);
    }

    // ==========================================
    // ADMIN - GET OPEN COMPLAINTS
    // ==========================================

    public List<Complaint> getOpenComplaints() {

        return complaintDAO.findOpen();
    }

    // ==========================================
    // ADMIN - RESOLVE COMPLAINT
    // ==========================================

    public Complaint resolveComplaint(
            String complaintId) {

        Complaint complaint =
                complaintDAO.findById(
                        complaintId);

        if (complaint == null) {
            throw new IllegalArgumentException(
                    "Complaint not found.");
        }

        if (!"Open".equals(
                complaint.getStatus())) {

            throw new IllegalStateException(
                    "Only open complaints can be resolved.");
        }

        boolean updated =
                complaintDAO.updateStatus(
                        complaintId,
                        "Resolved");

        if (!updated) {
            throw new IllegalStateException(
                    "Failed to resolve complaint.");
        }

        return complaintDAO.findById(
                complaintId);
    }

    // ==========================================
    // ADMIN - CLOSE COMPLAINT
    // ==========================================

    public Complaint closeComplaint(
            String complaintId) {

        Complaint complaint =
                complaintDAO.findById(
                        complaintId);

        if (complaint == null) {
            throw new IllegalArgumentException(
                    "Complaint not found.");
        }

        if (!"Resolved".equals(
                complaint.getStatus())) {

            throw new IllegalStateException(
                    "Only resolved complaints can be closed.");
        }

        boolean updated =
                complaintDAO.updateStatus(
                        complaintId,
                        "Closed");

        if (!updated) {
            throw new IllegalStateException(
                    "Failed to close complaint.");
        }

        return complaintDAO.findById(
                complaintId);
    }
}