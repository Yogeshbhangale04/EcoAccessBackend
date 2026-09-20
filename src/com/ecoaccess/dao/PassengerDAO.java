package com.ecoaccess.dao;

import com.ecoaccess.model.Passenger;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerDAO {

    // Find passenger using mobile number
    public Passenger findByMobile(String mobile) {

        String sql = """
                SELECT id, name, mobile, email, password, reward_points
                FROM passengers
                WHERE mobile = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, mobile);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Passenger(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("reward_points")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Find passenger using email
    public Passenger findByEmail(String email) {

        String sql = """
                SELECT id, name, mobile, email, password, reward_points
                FROM passengers
                WHERE email = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Passenger(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("reward_points")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean updateRewardPoints(
            String passengerId,
            int newPoints) {

        String sql = """
                UPDATE passengers
                SET reward_points = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, newPoints);
            statement.setString(2, passengerId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public Passenger findById(String passengerId) {

        String sql = """
                SELECT
                    id,
                    name,
                    mobile,
                    email,
                    password,
                    reward_points
                FROM passengers
                WHERE id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, passengerId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Passenger(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("reward_points")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}