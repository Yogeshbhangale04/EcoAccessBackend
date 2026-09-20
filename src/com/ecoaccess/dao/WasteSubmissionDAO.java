package com.ecoaccess.dao;

import com.ecoaccess.model.WasteSubmission;
import com.ecoaccess.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WasteSubmissionDAO {

    // ==========================================
    // SAVE SUBMISSION
    // ==========================================

    public boolean save(WasteSubmission submission) {

        String sql = """
                INSERT INTO waste_submissions (
                    id,
                    passenger_id,
                    photo,
                    status,
                    reward_points,
                    remark,
                    submitted_at,
                    reviewed_at,
                    reviewed_by
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
                    1, submission.getId());

            statement.setString(
                    2, submission.getPassengerId());

            statement.setString(
                    3, submission.getPhoto());

            statement.setString(
                    4, submission.getStatus());

            statement.setInt(
                    5, submission.getRewardPoints());

            statement.setString(
                    6, submission.getRemark());

            statement.setObject(
                    7, submission.getSubmittedAt());

            statement.setObject(
                    8, submission.getReviewedAt());

            statement.setString(
                    9, submission.getReviewedBy());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // ==========================================
    // FIND SUBMISSION BY ID
    // ==========================================

    public WasteSubmission findById(String id) {

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    photo,
                    status,
                    reward_points,
                    remark,
                    submitted_at,
                    reviewed_at,
                    reviewed_by
                FROM waste_submissions
                WHERE id = ?
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            try (ResultSet rs =
                    statement.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // ==========================================
    // FIND SUBMISSIONS BY PASSENGER
    // ==========================================

    public List<WasteSubmission> findByPassenger(
            String passengerId) {

        List<WasteSubmission> submissions =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    photo,
                    status,
                    reward_points,
                    remark,
                    submitted_at,
                    reviewed_at,
                    reviewed_by
                FROM waste_submissions
                WHERE passenger_id = ?
                ORDER BY submitted_at DESC
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
                    submissions.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return submissions;
    }


    // ==========================================
    // FIND PENDING SUBMISSIONS
    // ==========================================

    public List<WasteSubmission> findPending() {

        List<WasteSubmission> submissions =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    photo,
                    status,
                    reward_points,
                    remark,
                    submitted_at,
                    reviewed_at,
                    reviewed_by
                FROM waste_submissions
                WHERE status = 'Pending'
                ORDER BY submitted_at ASC
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet rs =
                    statement.executeQuery()
        ) {

            while (rs.next()) {
                submissions.add(mapRow(rs));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return submissions;
    }


    // ==========================================
    // UPDATE REVIEW
    // ==========================================

    public boolean updateReview(
            String id,
            String status,
            int rewardPoints,
            String remark,
            String reviewedBy) {

        String sql = """
                UPDATE waste_submissions
                SET
                    status = ?,
                    reward_points = ?,
                    remark = ?,
                    reviewed_at = CURRENT_TIMESTAMP,
                    reviewed_by = ?
                WHERE id = ?
                """;

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, status);

            statement.setInt(
                    2, rewardPoints);

            statement.setString(
                    3, remark);

            statement.setString(
                    4, reviewedBy);

            statement.setString(
                    5, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // ==========================================
    // CHECK LAST SUBMISSION
    // ==========================================

    public WasteSubmission findLatestByPassenger(
            String passengerId) {

        String sql = """
                SELECT
                    id,
                    passenger_id,
                    photo,
                    status,
                    reward_points,
                    remark,
                    submitted_at,
                    reviewed_at,
                    reviewed_by
                FROM waste_submissions
                WHERE passenger_id = ?
                ORDER BY submitted_at DESC
                LIMIT 1
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

                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // ==========================================
    // MAP DATABASE ROW TO OBJECT
    // ==========================================

    private WasteSubmission mapRow(
            ResultSet rs) throws SQLException {

        return new WasteSubmission(
                rs.getString("id"),
                rs.getString("passenger_id"),
                rs.getString("photo"),
                rs.getString("status"),
                rs.getInt("reward_points"),
                rs.getString("remark"),
                rs.getTimestamp("submitted_at")
                  .toLocalDateTime(),
                rs.getTimestamp("reviewed_at") != null
                    ? rs.getTimestamp("reviewed_at")
                      .toLocalDateTime()
                    : null,
                rs.getString("reviewed_by")
        );
    }
}