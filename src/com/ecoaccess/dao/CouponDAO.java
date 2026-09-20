package com.ecoaccess.dao;

import com.ecoaccess.model.Coupon;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO {

    // ==========================================
    // SAVE COUPON
    // ==========================================

    public boolean save(Coupon coupon) {

        String sql = """
                INSERT INTO coupons (
                    id,
                    code,
                    passenger_id,
                    points_redeemed,
                    value,
                    remaining,
                    status,
                    created_at,
                    expires_at
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
                    1, coupon.getId());

            statement.setString(
                    2, coupon.getCode());

            statement.setString(
                    3, coupon.getPassengerId());

            statement.setInt(
                    4, coupon.getPointsUsed());

            statement.setDouble(
                    5, coupon.getCouponValue());

            statement.setDouble(
                    6, coupon.getRemainingValue());

            statement.setString(
                    7, coupon.getStatus());

            statement.setObject(
                    8, coupon.getCreatedAt());

            statement.setObject(
                    9, coupon.getExpiresAt());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // ==========================================
    // FIND COUPONS BY PASSENGER
    // ==========================================

    public List<Coupon> findByPassenger(
            String passengerId) {

        List<Coupon> coupons =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    code,
                    passenger_id,
                    points_redeemed,
                    value,
                    remaining,
                    status,
                    created_at,
                    expires_at
                FROM coupons
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

                    Coupon coupon =
                            new Coupon(
                                rs.getString("id"),
                                rs.getString("code"),
                                rs.getString("passenger_id"),
                                rs.getInt("points_redeemed"),
                                rs.getDouble("value"),
                                rs.getDouble("remaining"),
                                rs.getString("status"),
                                rs.getTimestamp("created_at")
                                  .toLocalDateTime(),
                                rs.getTimestamp("expires_at")
                                  .toLocalDateTime()
                            );

                    coupons.add(coupon);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return coupons;
    }
}