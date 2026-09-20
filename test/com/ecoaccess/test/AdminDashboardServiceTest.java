package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Complaint;
import com.ecoaccess.model.Feedback;
import com.ecoaccess.model.WasteSubmission;
import com.ecoaccess.service.AdminDashboardService;
import com.ecoaccess.service.AdminService;
import com.ecoaccess.service.FeedbackService;
public class AdminDashboardServiceTest {

    private final AdminDashboardService adminDashboardService =
            new AdminDashboardService();

    private final AdminService adminService =
            new AdminService();


    @Test
    void getAdminSuccessfully() {

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123"
                );

        assertNotNull(admin);
    }


    @Test
    void getPendingWasteSuccessfully()
            throws SQLException {

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123"
                );

        assertNotNull(admin);

        List<WasteSubmission> submissions =
                adminDashboardService.getPendingWaste(
                        admin
                );

        assertNotNull(submissions);

        for (WasteSubmission submission : submissions) {
            assertEquals(
                    "Pending",
                    submission.getStatus()
            );
        }
    }


    @Test
    void getOpenComplaintsSuccessfully()
            throws SQLException {

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123"
                );

        assertNotNull(admin);

        List<Complaint> complaints =
                adminDashboardService.getOpenComplaints(
                        admin
                );

        assertNotNull(complaints);

        assertFalse(
                complaints.isEmpty()
        );

        for (Complaint complaint : complaints) {
            assertEquals(
                    "Open",
                    complaint.getStatus()
            );
        }
    }


    @Test
    void resolveAndCloseComplaintSuccessfully()
            throws SQLException {

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123"
                );

        assertNotNull(admin);

        String complaintId =
                "CMP-20AF3599";

        adminDashboardService.resolveComplaint(
                admin,
                complaintId
        );

        Complaint resolved =
                new com.ecoaccess.service.ComplaintService()
                        .getComplaint(complaintId);

        assertNotNull(resolved);

        assertEquals(
                "Resolved",
                resolved.getStatus()
        );


        adminDashboardService.closeComplaint(
                admin,
                complaintId
        );

        Complaint closed =
                new com.ecoaccess.service.ComplaintService()
                        .getComplaint(complaintId);

        assertNotNull(closed);

        assertEquals(
                "Closed",
                closed.getStatus()
        );
    }


    @Test
    void getSubmittedFeedbackSuccessfully()
            throws SQLException {

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123"
                );

        assertNotNull(admin);

        List<Feedback> feedbackList =
                adminDashboardService.getSubmittedFeedback(
                        admin
                );

        assertNotNull(feedbackList);

        assertFalse(
                feedbackList.isEmpty()
        );

        for (Feedback feedback : feedbackList) {
            assertEquals(
                    "Submitted",
                    feedback.getStatus()
            );
        }
    }


    @Test
    void reviewFeedbackSuccessfully()
            throws SQLException {

        Admin admin =
                adminService.login(
                        "admin@ecoaccess.com",
                        "Test@123"
                );

        assertNotNull(admin);

        String feedbackId =
                "FDB-FD652500";

        adminDashboardService.reviewFeedback(
                admin,
                feedbackId
        );

        Feedback feedback =
                new FeedbackService()
                        .getFeedback(feedbackId);

        assertNotNull(feedback);

        assertEquals(
                "Reviewed",
                feedback.getStatus()
        );
    }


    @Test
    void nullAdminFails()
            throws SQLException {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> adminDashboardService
                                .getOpenComplaints(null)
                );

        assertEquals(
                "Admin information is required.",
                exception.getMessage()
        );
    }
}