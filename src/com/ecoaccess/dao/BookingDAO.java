package com.ecoaccess.dao;

import com.ecoaccess.model.Booking;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean save(Booking booking) {

        String sql = """
                INSERT INTO bookings (
                    id,
                    passenger_id,
                    journey_id,
                    service,
                    station_id,
                    platform,
                    booking_date,
                    booking_time,
                    pickup_point,
                    drop_platform,
                    passenger_count,
                    bags,
                    weight_range,
                    base_fare,
                    tax_amount,
                    gross_fare,
                    discount,
                    final_fare,
                    status,
                    staff_id
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, booking.getId());
            statement.setString(2, booking.getPassengerId());
            statement.setString(3, booking.getJourneyId());
            statement.setString(4, booking.getService());
            statement.setString(5, booking.getStationId());
            statement.setInt(6, booking.getPlatform());
            statement.setObject(7, booking.getBookingDate());
            statement.setObject(8, booking.getBookingTime());
            statement.setString(9, booking.getPickupPoint());
            statement.setString(10, booking.getDropPlatform());
            statement.setInt(11, booking.getPassengerCount());
            statement.setInt(12, booking.getBags());
            statement.setString(13, booking.getWeightRange());
            statement.setDouble(14, booking.getBaseFare());
            statement.setDouble(15, booking.getTaxAmount());
            statement.setDouble(16, booking.getGrossFare());
            statement.setDouble(17, booking.getDiscount());
            statement.setDouble(18, booking.getFinalFare());
            statement.setString(19, booking.getStatus());
            statement.setString(20, booking.getStaffId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Booking findById(String bookingId) {

        String sql = """
                SELECT *
                FROM bookings
                WHERE id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, bookingId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapBooking(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Booking> findByPassenger(String passengerId) {

        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT *
                FROM bookings
                WHERE passenger_id = ?
                ORDER BY created_at DESC
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, passengerId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    bookings.add(mapBooking(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }


    private Booking mapBooking(ResultSet rs) throws SQLException {

        return new Booking(
            rs.getString("id"),
            rs.getString("passenger_id"),
            rs.getString("journey_id"),
            rs.getString("service"),
            rs.getString("station_id"),
            rs.getInt("platform"),
            rs.getDate("booking_date").toLocalDate(),
            rs.getTime("booking_time").toLocalTime(),
            rs.getString("pickup_point"),
            rs.getString("drop_platform"),
            rs.getInt("passenger_count"),
            rs.getInt("bags"),
            rs.getString("weight_range"),
            rs.getDouble("base_fare"),
            rs.getDouble("tax_amount"),
            rs.getDouble("gross_fare"),
            rs.getDouble("discount"),
            rs.getDouble("final_fare"),
            rs.getString("status"),
            rs.getString("staff_id")
        );
    }
    public List<Booking> findByStaff(String staffId) {

        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT *
                FROM bookings
                WHERE staff_id = ?
                ORDER BY created_at DESC
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, staffId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    bookings.add(mapBooking(rs));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return bookings;
    }
}