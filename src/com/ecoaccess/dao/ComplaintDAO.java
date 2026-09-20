package com.ecoaccess.dao;

import com.ecoaccess.model.Complaint;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    // ==========================================
    // SAVE COMPLAINT
    // ==========================================

    public boolean save(Complaint complaint) {

        String sql = """
                INSERT INTO complaints (
                    id,
                    passenger_id,
                    booking_id,
                    subject,
                    description,
                    rating,
                    status,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, complaint.getId());

            statement.setString(
                    2, complaint.getPassengerId());

            statement.setString(
                    3, complaint.getBookingId());

            statement.setString(
                    4, complaint.getSubject());

            statement.setString(
                    5, complaint.getDescription());

            statement.setInt(
                    6, complaint.getRating());

            statement.setString(
                    7, complaint.getStatus());

            statement.setObject(
                    8, complaint.getCreatedAt());

            statement.setObject(
                    9, complaint.getUpdatedAt());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // FIND BY ID
    // ==========================================

    public Complaint findById(String id) {

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    booking_id,
                    subject,
                    description,
                    rating,
                    status,
                    created_at,
                    updated_at
                FROM complaints
                WHERE id = ?
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            try (ResultSet rs =
                    statement.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // ==========================================
    // FIND BY PASSENGER
    // ==========================================

    public List<Complaint> findByPassenger(
            String passengerId) {

        List<Complaint> complaints =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    booking_id,
                    subject,
                    description,
                    rating,
                    status,
                    created_at,
                    updated_at
                FROM complaints
                WHERE passenger_id = ?
                ORDER BY created_at DESC
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, passengerId);

            try (ResultSet rs =
                    statement.executeQuery()) {

                while (rs.next()) {
                    complaints.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return complaints;
    }

    // ==========================================
    // FIND BY BOOKING
    // ==========================================

    public List<Complaint> findByBooking(
            String bookingId) {

        List<Complaint> complaints =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    booking_id,
                    subject,
                    description,
                    rating,
                    status,
                    created_at,
                    updated_at
                FROM complaints
                WHERE booking_id = ?
                ORDER BY created_at DESC
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, bookingId);

            try (ResultSet rs =
                    statement.executeQuery()) {

                while (rs.next()) {
                    complaints.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return complaints;
    }

    // ==========================================
    // FIND OPEN COMPLAINTS
    // ==========================================

    public List<Complaint> findOpen() {

        List<Complaint> complaints =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    booking_id,
                    subject,
                    description,
                    rating,
                    status,
                    created_at,
                    updated_at
                FROM complaints
                WHERE status = 'Open'
                ORDER BY created_at ASC
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet rs =
                    statement.executeQuery()
        ) {

            while (rs.next()) {
                complaints.add(mapRow(rs));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return complaints;
    }

    // ==========================================
    // UPDATE STATUS
    // ==========================================

    public boolean updateStatus(
            String id,
            String status) {

        String sql = """
                UPDATE complaints
                SET
                    status = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, status);

            statement.setString(
                    2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // MAP DATABASE ROW
    // ==========================================

    private Complaint mapRow(
            ResultSet rs) throws SQLException {

        return new Complaint(
                rs.getString("id"),
                rs.getString("passenger_id"),
                rs.getString("booking_id"),
                rs.getString("subject"),
                rs.getString("description"),
                rs.getInt("rating"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
                  .toLocalDateTime(),
                rs.getTimestamp("updated_at")
                  .toLocalDateTime()
        );
    }
}