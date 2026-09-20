package com.ecoaccess.dao;

import com.ecoaccess.model.Feedback;
import com.ecoaccess.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    // Save feedback
    public void save(Feedback feedback) throws SQLException {

        String sql = """
                INSERT INTO feedback
                (id, passenger_id, booking_id, rating, subject,
                 description, status, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, feedback.getId());
            ps.setString(2, feedback.getPassengerId());
            ps.setString(3, feedback.getBookingId());
            ps.setInt(4, feedback.getRating());
            ps.setString(5, feedback.getSubject());
            ps.setString(6, feedback.getDescription());
            ps.setString(7, feedback.getStatus());
            ps.setTimestamp(8, Timestamp.valueOf(feedback.getCreatedAt()));

            ps.executeUpdate();
        }
    }

    // Find feedback by ID
    public Feedback findById(String id) throws SQLException {

        String sql = """
                SELECT *
                FROM feedback
                WHERE id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    // Find feedback submitted by a passenger
    public List<Feedback> findByPassenger(String passengerId)
            throws SQLException {

        String sql = """
                SELECT *
                FROM feedback
                WHERE passenger_id = ?
                ORDER BY created_at DESC
                """;

        List<Feedback> feedbackList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, passengerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    feedbackList.add(mapResultSet(rs));
                }
            }
        }

        return feedbackList;
    }

    // Find feedback for a booking
    public List<Feedback> findByBooking(String bookingId)
            throws SQLException {

        String sql = """
                SELECT *
                FROM feedback
                WHERE booking_id = ?
                ORDER BY created_at DESC
                """;

        List<Feedback> feedbackList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    feedbackList.add(mapResultSet(rs));
                }
            }
        }

        return feedbackList;
    }

    // Find all submitted feedback
    public List<Feedback> findSubmitted() throws SQLException {

        String sql = """
                SELECT *
                FROM feedback
                WHERE status = 'Submitted'
                ORDER BY created_at ASC
                """;

        List<Feedback> feedbackList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                feedbackList.add(mapResultSet(rs));
            }
        }

        return feedbackList;
    }

    // Update feedback status
    public void updateStatus(String id, String status)
            throws SQLException {

        String sql = """
                UPDATE feedback
                SET status = ?
                WHERE id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, id);

            ps.executeUpdate();
        }
    }

    // Convert ResultSet into Feedback object
    private Feedback mapResultSet(ResultSet rs)
            throws SQLException {

        Feedback feedback = new Feedback();

        feedback.setId(rs.getString("id"));
        feedback.setPassengerId(rs.getString("passenger_id"));
        feedback.setBookingId(rs.getString("booking_id"));
        feedback.setRating(rs.getInt("rating"));
        feedback.setSubject(rs.getString("subject"));
        feedback.setDescription(rs.getString("description"));
        feedback.setStatus(rs.getString("status"));

        Timestamp timestamp = rs.getTimestamp("created_at");

        if (timestamp != null) {
            feedback.setCreatedAt(timestamp.toLocalDateTime());
        }

        return feedback;
    }
}