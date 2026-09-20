package com.ecoaccess.dao;

import com.ecoaccess.util.DBConnection;
import com.ecoaccess.model.BookingStatusHistory;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingStatusHistoryDAO {

    public boolean save(
            String bookingId,
            String oldStatus,
            String newStatus,
            String changedBy) {

        String sql = """
                INSERT INTO booking_status_history (
                    booking_id,
                    old_status,
                    new_status,
                    changed_by
                )
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, bookingId);
            statement.setString(2, oldStatus);
            statement.setString(3, newStatus);
            statement.setString(4, changedBy);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<BookingStatusHistory> findByBookingId(
            String bookingId) {

        List<BookingStatusHistory> history =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    booking_id,
                    old_status,
                    new_status,
                    changed_by,
                    changed_at
                FROM booking_status_history
                WHERE booking_id = ?
                ORDER BY changed_at ASC, id ASC
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, bookingId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    BookingStatusHistory item =
                            new BookingStatusHistory(
                                rs.getLong("id"),
                                rs.getString("booking_id"),
                                rs.getString("old_status"),
                                rs.getString("new_status"),
                                rs.getString("changed_by"),
                                rs.getTimestamp("changed_at")
                                  .toLocalDateTime()
                            );

                    history.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }
}