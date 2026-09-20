package com.ecoaccess.dao;

import com.ecoaccess.model.Staff;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffAssignmentDAO {

    public Staff findAvailableStaff(String role) {

        String sql = """
                SELECT id, employee_id, name, password, role, status
                FROM staff
                WHERE role = ?
                  AND status = 'Available'
                ORDER BY id
                LIMIT 1
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, role);

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
    
    public boolean updateStatus(String staffId, String status) {

        String sql = """
                UPDATE staff
                SET status = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setString(2, staffId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}