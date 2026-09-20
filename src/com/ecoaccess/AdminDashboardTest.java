package com.ecoaccess;

import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Complaint;
import com.ecoaccess.model.Feedback;
import com.ecoaccess.model.WasteSubmission;
import com.ecoaccess.service.AdminService;
import com.ecoaccess.service.AdminDashboardService;

import java.util.List;

public class AdminDashboardTest {

    public static void main(String[] args) {

        AdminService adminService = new AdminService();
        AdminDashboardService dashboardService =
                new AdminDashboardService();

        try {

            // ==========================================
            // 1. ADMIN LOGIN
            // ==========================================

            System.out.println("===== ADMIN LOGIN =====");

            Admin admin = adminService.login(
                    "admin@ecoaccess.com",
                    "Test@123"
            );

            if (admin == null) {
                throw new RuntimeException(
                        "Admin login failed.");
            }

            System.out.println("Admin ID: " + admin.getId());
            System.out.println("Name: " + admin.getName());
            System.out.println("Email: " + admin.getEmail());


            // ==========================================
            // 2. PENDING WASTE
            // ==========================================

            System.out.println("\n===== PENDING WASTE =====");

            List<WasteSubmission> pendingWaste =
                    dashboardService.getPendingWaste(admin);

            System.out.println(
                    "Pending waste count: "
                    + pendingWaste.size());

            for (WasteSubmission waste : pendingWaste) {

                System.out.println(
                        waste.getId()
                        + " | Passenger: "
                        + waste.getPassengerId()
                        + " | Status: "
                        + waste.getStatus()
                );
            }


            // ==========================================
            // 3. OPEN COMPLAINTS
            // ==========================================

            System.out.println("\n===== OPEN COMPLAINTS =====");

            List<Complaint> complaints =
                    dashboardService.getOpenComplaints(admin);

            System.out.println(
                    "Open complaint count: "
                    + complaints.size());

            for (Complaint complaint : complaints) {

                System.out.println(
                        complaint.getId()
                        + " | Passenger: "
                        + complaint.getPassengerId()
                        + " | Subject: "
                        + complaint.getSubject()
                        + " | Rating: "
                        + complaint.getRating()
                        + " | Status: "
                        + complaint.getStatus()
                );
            }


            // ==========================================
            // 4. SUBMITTED FEEDBACK
            // ==========================================

            System.out.println("\n===== SUBMITTED FEEDBACK =====");

            List<Feedback> feedbackList =
                    dashboardService.getSubmittedFeedback(admin);

            System.out.println(
                    "Submitted feedback count: "
                    + feedbackList.size());

            for (Feedback feedback : feedbackList) {

                System.out.println(
                        feedback.getId()
                        + " | Passenger: "
                        + feedback.getPassengerId()
                        + " | Rating: "
                        + feedback.getRating()
                        + " | Subject: "
                        + feedback.getSubject()
                        + " | Status: "
                        + feedback.getStatus()
                );
            }


            // ==========================================
            // 5. COMPLETE
            // ==========================================

            System.out.println(
                    "\n======================================");

            System.out.println(
                    "ADMIN DASHBOARD TEST COMPLETED SUCCESSFULLY");

            System.out.println(
                    "======================================");


        } catch (Exception e) {

            System.out.println(
                    "\nAdmin dashboard test failed!");

            e.printStackTrace();
        }
    }
}