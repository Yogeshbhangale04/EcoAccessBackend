package com.ecoaccess.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ecoaccess.dao.WasteSubmissionDAO;
import com.ecoaccess.model.WasteSubmission;

public class WasteService {

    private static final int REWARD_POINTS = 20;

    private static final int COOLDOWN_HOURS = 4;

    private final WasteSubmissionDAO wasteDAO;
    private final RewardService rewardService;

    public WasteService() {

        this.wasteDAO = new WasteSubmissionDAO();
        this.rewardService = new RewardService();
    }

    // ==========================================
    // SUBMIT WASTE PROOF
    // ==========================================

    public WasteSubmission submitWaste(
            String passengerId,
            String photo) {

        if (passengerId == null ||
                passengerId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Passenger ID is required.");
        }

        if (photo == null ||
                photo.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Waste proof photo is required.");
        }

        // ------------------------------------------
        // Check 4-hour cooldown
        // ------------------------------------------

        WasteSubmission latest =
                wasteDAO.findLatestByPassenger(
                        passengerId);

        if (latest != null) {

            LocalDateTime cooldownTime =
                    latest.getSubmittedAt()
                          .plusHours(COOLDOWN_HOURS);

            if (LocalDateTime.now()
                    .isBefore(cooldownTime)) {

                throw new IllegalStateException(
                        "You can submit waste proof again after "
                        + cooldownTime);
            }
        }

        // ------------------------------------------
        // Create submission
        // ------------------------------------------

        String submissionId =
                "WS-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        WasteSubmission submission =
                new WasteSubmission(
                        submissionId,
                        passengerId,
                        photo,
                        "Pending",
                        0,
                        null,
                        LocalDateTime.now(),
                        null,
                        null
                );

        boolean saved =
                wasteDAO.save(submission);

        if (!saved) {

            throw new IllegalStateException(
                    "Failed to submit waste proof.");
        }

        return submission;
    }

    // ==========================================
    // ADMIN - GET PENDING SUBMISSIONS
    // ==========================================

    public List<WasteSubmission>
            getPendingSubmissions() {

        return wasteDAO.findPending();
    }

    // ==========================================
    // ADMIN - ACCEPT SUBMISSION
    // ==========================================

    public WasteSubmission acceptSubmission(
            String submissionId,
            String adminId) {

        WasteSubmission submission =
                wasteDAO.findById(submissionId);

        if (submission == null) {

            throw new IllegalArgumentException(
                    "Waste submission not found.");
        }

        if (!"Pending".equals(
                submission.getStatus())) {

            throw new IllegalStateException(
                    "Only pending submissions can be accepted.");
        }

        if (adminId == null ||
                adminId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Admin ID is required.");
        }

        // ------------------------------------------
        // Update submission
        // ------------------------------------------

        boolean updated =
                wasteDAO.updateReview(
                        submissionId,
                        "Accepted",
                        REWARD_POINTS,
                        "Waste proof accepted.",
                        adminId
                );

        if (!updated) {

            throw new IllegalStateException(
                    "Failed to accept waste submission.");
        }

        // ------------------------------------------
        // Award reward points
        // ------------------------------------------

        rewardService.addPoints(
                submission.getPassengerId(),
                REWARD_POINTS,
                "WASTE",
                submissionId,
                "Waste disposal reward"
        );

        return wasteDAO.findById(submissionId);
    }

    // ==========================================
    // ADMIN - REJECT SUBMISSION
    // ==========================================

    public WasteSubmission rejectSubmission(
            String submissionId,
            String adminId,
            String remark) {

        WasteSubmission submission =
                wasteDAO.findById(submissionId);

        if (submission == null) {

            throw new IllegalArgumentException(
                    "Waste submission not found.");
        }

        if (!"Pending".equals(
                submission.getStatus())) {

            throw new IllegalStateException(
                    "Only pending submissions can be rejected.");
        }

        if (adminId == null ||
                adminId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Admin ID is required.");
        }

        if (remark == null ||
                remark.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Rejection remark is required.");
        }

        // ------------------------------------------
        // Reject submission
        // ------------------------------------------

        boolean updated =
                wasteDAO.updateReview(
                        submissionId,
                        "Rejected",
                        0,
                        remark,
                        adminId
                );

        if (!updated) {

            throw new IllegalStateException(
                    "Failed to reject waste submission.");
        }

        return wasteDAO.findById(submissionId);
    }

    // ==========================================
    // GET PASSENGER SUBMISSIONS
    // ==========================================

    public List<WasteSubmission>
            getPassengerSubmissions(
                    String passengerId) {

        return wasteDAO.findByPassenger(
                passengerId);
    }

    // ==========================================
    // GET SUBMISSION
    // ==========================================

    public WasteSubmission getSubmission(
            String submissionId) {

        return wasteDAO.findById(
                submissionId);
    }
}