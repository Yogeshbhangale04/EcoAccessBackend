package com.ecoaccess.dao;

import com.ecoaccess.model.Payment;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {

    public boolean save(Payment payment) {

        String sql = """
                INSERT INTO payments (
                    id,
                    booking_id,
                    amount,
                    payment_method,
                    payment_status,
                    transaction_id,
                    paid_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, payment.getId());
            statement.setString(2, payment.getBookingId());
            statement.setDouble(3, payment.getAmount());
            statement.setString(4, payment.getPaymentMethod());
            statement.setString(5, payment.getPaymentStatus());
            statement.setString(6, payment.getTransactionId());

            if (payment.getPaidAt() != null) {
                statement.setObject(
                        7,
                        payment.getPaidAt()
                );
            } else {
                statement.setObject(7, null);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Payment findByBookingId(String bookingId) {

        String sql = """
                SELECT *
                FROM payments
                WHERE booking_id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, bookingId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Payment(
                        rs.getString("id"),
                        rs.getString("booking_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        rs.getString("transaction_id"),
                        rs.getTimestamp("paid_at") != null
                                ? rs.getTimestamp("paid_at")
                                    .toLocalDateTime()
                                : null
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}