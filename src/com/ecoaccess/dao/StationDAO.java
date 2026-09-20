package com.ecoaccess.dao;

import com.ecoaccess.model.Station;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    public List<Station> findAll() {

        List<Station> stations = new ArrayList<>();

        String sql = """
                SELECT id, name
                FROM stations
                ORDER BY name
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {

            while (rs.next()) {

                Station station = new Station(
                    rs.getString("id"),
                    rs.getString("name")
                );

                stations.add(station);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stations;
    }
}