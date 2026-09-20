package com.ecoaccess.dao;

import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JourneyValidationDAO {

    public boolean isValidated(String passengerId, String journeyId) {

        String sql = """
                SELECT 1
                FROM journey_validations
                WHERE passenger_id = ?
                  AND journey_id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, passengerId);
            statement.setString(2, journeyId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean saveValidation(
            String validationId,
            String passengerId,
            String journeyId) {

        String sql = """
                INSERT INTO journey_validations
                (id, passenger_id, journey_id)
                VALUES (?, ?, ?)
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, validationId);
            statement.setString(2, passengerId);
            statement.setString(3, journeyId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}