package com.ecoaccess.service;

import java.util.List;

import com.ecoaccess.dao.BookingDAO;
import com.ecoaccess.dao.BookingStatusHistoryDAO;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.BookingStatusHistory;

public class TrackingService {

    private final BookingDAO bookingDAO;
    private final BookingStatusHistoryDAO historyDAO;

    public TrackingService() {
        this.bookingDAO = new BookingDAO();
        this.historyDAO = new BookingStatusHistoryDAO();
    }

    public Booking getBooking(String bookingId) {

        return bookingDAO.findById(bookingId);
    }

    public List<BookingStatusHistory> getBookingHistory(
            String bookingId) {

        return historyDAO.findByBookingId(bookingId);
    }
}