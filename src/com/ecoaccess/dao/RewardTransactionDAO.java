package com.ecoaccess.dao;

import com.ecoaccess.model.RewardTransaction;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RewardTransactionDAO {

    public boolean save(RewardTransaction transaction) {

        String sql = """
                INSERT INTO reward_transactions (
                    id,
                    passenger_id,
                    transaction_type,
                    points,
                    reference_type,
                    reference_id,
                    description
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, transaction.getId());
            statement.setString(2, transaction.getPassengerId());
            statement.setString(3, transaction.getTransactionType());
            statement.setInt(4, transaction.getPoints());
            statement.setString(5, transaction.getReferenceType());
            statement.setString(6, transaction.getReferenceId());
            statement.setString(7, transaction.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    public List<RewardTransaction> findByPassenger(
            String passengerId) {

        List<RewardTransaction> transactions =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    transaction_type,
                    points,
                    reference_type,
                    reference_id,
                    description,
                    created_at
                FROM reward_transactions
                WHERE passenger_id = ?
                ORDER BY created_at DESC
                """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, passengerId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    RewardTransaction transaction =
                            new RewardTransaction(
                                rs.getString("id"),
                                rs.getString("passenger_id"),
                                rs.getString("transaction_type"),
                                rs.getInt("points"),
                                rs.getString("reference_type"),
                                rs.getString("reference_id"),
                                rs.getString("description"),
                                rs.getTimestamp("created_at")
                                  .toLocalDateTime()
                            );

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return transactions;
    }
}