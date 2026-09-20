package com.ecoaccess.dao;

import com.ecoaccess.model.Staff;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO {

    public Staff findByEmployeeId(String employeeId) {

        String sql = """
                SELECT id, employee_id, name, password, role, status
                FROM staff
                WHERE employee_id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, employeeId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return new Staff(
                        rs.getString("id"),
                        rs.getString("employee_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("status")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}