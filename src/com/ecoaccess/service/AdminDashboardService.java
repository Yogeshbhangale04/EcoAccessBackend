package com.ecoaccess.service;

import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Complaint;
import com.ecoaccess.model.Feedback;
import com.ecoaccess.model.WasteSubmission;

import java.sql.SQLException;
import java.util.List;

public class AdminDashboardService {

    private final WasteService wasteService;
    private final ComplaintService complaintService;
    private final FeedbackService feedbackService;

    public AdminDashboardService() {

        this.wasteService = new WasteService();
        this.complaintService = new ComplaintService();
        this.feedbackService = new FeedbackService();
    }

    // ==========================================
    // WASTE MANAGEMENT
    // ==========================================

    public List<WasteSubmission> getPendingWaste(Admin admin)
            throws SQLException {

        validateAdmin(admin);

        return wasteService.getPendingSubmissions();
    }

    public void acceptWaste(
            Admin admin,
            String submissionId) throws SQLException {

        validateAdmin(admin);

        wasteService.acceptSubmission(
                submissionId,
                admin.getId()
        );
    }

    public void rejectWaste(
            Admin admin,
            String submissionId,
            String remark) throws SQLException {

        validateAdmin(admin);

        wasteService.rejectSubmission(
                submissionId,
                admin.getId(),
                remark
        );
    }

    // ==========================================
    // COMPLAINT MANAGEMENT
    // ==========================================

    public List<Complaint> getOpenComplaints(Admin admin)
            throws SQLException {

        validateAdmin(admin);

        return complaintService.getOpenComplaints();
    }

    public void resolveComplaint(
            Admin admin,
            String complaintId) throws SQLException {

        validateAdmin(admin);

        complaintService.resolveComplaint(complaintId);
    }

    public void closeComplaint(
            Admin admin,
            String complaintId) throws SQLException {

        validateAdmin(admin);

        complaintService.closeComplaint(complaintId);
    }

    // ==========================================
    // FEEDBACK MANAGEMENT
    // ==========================================

    public List<Feedback> getSubmittedFeedback(Admin admin)
            throws SQLException {

        validateAdmin(admin);

        return feedbackService.getSubmittedFeedback();
    }

    public void reviewFeedback(
            Admin admin,
            String feedbackId) throws SQLException {

        validateAdmin(admin);

        feedbackService.reviewFeedback(feedbackId);
    }

    // ==========================================
    // ADMIN VALIDATION
    // ==========================================

    private void validateAdmin(Admin admin) {

        if (admin == null) {
            throw new IllegalArgumentException(
                    "Admin information is required.");
        }
    }
}