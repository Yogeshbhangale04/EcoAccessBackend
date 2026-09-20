package com.ecoaccess.service;

import com.ecoaccess.dao.BookingDAO;
import com.ecoaccess.dao.BookingStatusHistoryDAO;
import com.ecoaccess.dao.StaffAssignmentDAO;
import com.ecoaccess.model.Booking;

public class BookingStatusService {

    private final BookingDAO bookingDAO;
    private final BookingStatusHistoryDAO historyDAO;
    private final StaffAssignmentDAO staffDAO;

    public BookingStatusService() {
        this.bookingDAO = new BookingDAO();
        this.historyDAO = new BookingStatusHistoryDAO();
        this.staffDAO = new StaffAssignmentDAO();
    }

    public boolean updateStatus(
            String bookingId,
            String newStatus,
            String changedBy) {

        Booking booking =
                bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found.");
        }

        String oldStatus =
                booking.getStatus();

        if (!isValidTransition(
                oldStatus,
                newStatus)) {

            throw new IllegalStateException(
                    "Invalid status transition: "
                    + oldStatus
                    + " -> "
                    + newStatus);
        }

        // Update booking table
        boolean updated =
                updateBookingStatus(
                        bookingId,
                        newStatus);

        if (!updated) {
            return false;
        }

        // Save status history
        historyDAO.save(
                bookingId,
                oldStatus,
                newStatus,
                changedBy
        );

        // Staff becomes available after completion/rejection
        if (("Completed".equals(newStatus)
                || "Rejected".equals(newStatus))
                && booking.getStaffId() != null) {

            staffDAO.updateStatus(
                    booking.getStaffId(),
                    "Available"
            );
        }

        return true;
    }


    private boolean updateBookingStatus(
            String bookingId,
            String status) {

        return updateUsingDAO(
                bookingId,
                status);
    }


    private boolean updateUsingDAO(
            String bookingId,
            String status) {

        String sql = """
                UPDATE bookings
                SET status = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (
            java.sql.Connection connection =
                    com.ecoaccess.util.DBConnection
                    .getConnection();

            java.sql.PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setString(2, bookingId);

            return statement.executeUpdate() > 0;

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private boolean isValidTransition(
            String current,
            String next) {

        if ("Booked".equals(current)
                && "Assigned".equals(next)) {
            return true;
        }

        if ("Booked".equals(current)
                && "Accepted".equals(next)) {
            return true;
        }

        if ("Assigned".equals(current)
                && "Accepted".equals(next)) {
            return true;
        }

        if ("Assigned".equals(current)
                && "Rejected".equals(next)) {
            return true;
        }

        if ("Accepted".equals(current)
                && "Reached Passenger".equals(next)) {
            return true;
        }

        if ("Accepted".equals(current)
                && "Rejected".equals(next)) {
            return true;
        }

        if ("Reached Passenger".equals(current)
                && "Service Started".equals(next)) {
            return true;
        }

        if ("Service Started".equals(current)
                && "Completed".equals(next)) {
            return true;
        }

        return false;
    }
}