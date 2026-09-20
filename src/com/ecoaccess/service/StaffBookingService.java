package com.ecoaccess.service;

import com.ecoaccess.dao.BookingDAO;
import com.ecoaccess.dao.StaffAssignmentDAO;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Staff;

import java.sql.SQLException;
import java.util.List;

public class StaffBookingService {

    private final BookingDAO bookingDAO;
    private final StaffAssignmentDAO staffAssignmentDAO;
    private final BookingStatusService bookingStatusService;

    public StaffBookingService() {
        this.bookingDAO = new BookingDAO();
        this.staffAssignmentDAO = new StaffAssignmentDAO();
        this.bookingStatusService = new BookingStatusService();
    }

    // View bookings assigned to a staff member
    public List<Booking> getAssignedBookings(String staffId)
            throws SQLException {

        return bookingDAO.findByStaff(staffId);
    }

    // Accept assigned booking
    public boolean acceptBooking(String bookingId, Staff staff)
            throws SQLException {

        validateStaff(staff);

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found: " + bookingId);
        }

        if (!staff.getId().equals(booking.getStaffId())) {
            throw new IllegalArgumentException(
                    "This booking is not assigned to this staff member.");
        }

        if (!"Assigned".equals(booking.getStatus())) {
            throw new IllegalArgumentException(
                    "Only Assigned bookings can be accepted.");
        }

        return bookingStatusService.updateStatus(
                bookingId,
                "Accepted",
                staff.getId()
        );
    }

    // Reject assigned booking
    public boolean rejectBooking(
            String bookingId,
            Staff staff) throws SQLException {

        validateStaff(staff);

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found: " + bookingId);
        }

        if (!staff.getId().equals(booking.getStaffId())) {
            throw new IllegalArgumentException(
                    "This booking is not assigned to this staff member.");
        }

        if (!"Assigned".equals(booking.getStatus())) {
            throw new IllegalArgumentException(
                    "Only Assigned bookings can be rejected.");
        }

        return bookingStatusService.updateStatus(
                bookingId,
                "Rejected",
                staff.getId()
        );
    }

    // Staff reached passenger
    public boolean reachedPassenger(
            String bookingId,
            Staff staff) throws SQLException {

        validateStaff(staff);

        validateAssignedBooking(bookingId, staff);

        return bookingStatusService.updateStatus(
                bookingId,
                "Reached Passenger",
                staff.getId()
        );
    }

    // Start service
    public boolean startService(
            String bookingId,
            Staff staff) throws SQLException {

        validateStaff(staff);

        validateAssignedBooking(bookingId, staff);

        return bookingStatusService.updateStatus(
                bookingId,
                "Service Started",
                staff.getId()
        );
    }

    // Complete service
    public boolean completeService(
            String bookingId,
            Staff staff) throws SQLException {

        validateStaff(staff);

        validateAssignedBooking(bookingId, staff);

        return bookingStatusService.updateStatus(
                bookingId,
                "Completed",
                staff.getId()
        );
    }

    private void validateStaff(Staff staff) {

        if (staff == null) {
            throw new IllegalArgumentException(
                    "Staff information is required.");
        }
    }

    private void validateAssignedBooking(
            String bookingId,
            Staff staff) throws SQLException {

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found: " + bookingId);
        }

        if (!staff.getId().equals(booking.getStaffId())) {
            throw new IllegalArgumentException(
                    "This booking is not assigned to this staff member.");
        }
    }
}