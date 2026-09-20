package com.ecoaccess.dao;

import com.ecoaccess.model.Journey;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JourneyDAO {

    public Journey findByPnr(String pnr) {

        String sql = """
                SELECT id,
                       pnr,
                       train_id,
                       station_id,
                       platform,
                       journey_date,
                       journey_time,
                       source,
                       destination,
                       coach,
                       travel_class
                FROM journeys
                WHERE pnr = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, pnr);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Journey(
                        rs.getString("id"),
                        rs.getString("pnr"),
                        rs.getString("train_id"),
                        rs.getString("station_id"),
                        rs.getInt("platform"),
                        rs.getDate("journey_date").toLocalDate(),
                        rs.getTime("journey_time").toLocalTime(),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("coach"),
                        rs.getString("travel_class")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Journey findById(String journeyId) {

        String sql = """
                SELECT id,
                       pnr,
                       train_id,
                       station_id,
                       platform,
                       journey_date,
                       journey_time,
                       source,
                       destination,
                       coach,
                       travel_class
                FROM journeys
                WHERE id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, journeyId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Journey(
                        rs.getString("id"),
                        rs.getString("pnr"),
                        rs.getString("train_id"),
                        rs.getString("station_id"),
                        rs.getInt("platform"),
                        rs.getDate("journey_date").toLocalDate(),
                        rs.getTime("journey_time").toLocalTime(),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("coach"),
                        rs.getString("travel_class")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

