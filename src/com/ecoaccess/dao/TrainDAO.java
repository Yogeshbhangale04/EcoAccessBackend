package com.ecoaccess.dao;

import com.ecoaccess.model.Train;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    public List<Train> findAll() {

        List<Train> trains = new ArrayList<>();

        String sql = """
                SELECT id, train_number, train_name
                FROM trains
                ORDER BY train_number
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {

            while (rs.next()) {

                Train train = new Train(
                    rs.getString("id"),
                    rs.getString("train_number"),
                    rs.getString("train_name")
                );

                trains.add(train);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trains;
    }
}