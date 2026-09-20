//package com.ecoaccess;
//
//import com.ecoaccess.model.Admin;
//import com.ecoaccess.model.Complaint;
//import com.ecoaccess.model.Feedback;
//import com.ecoaccess.model.WasteSubmission;
//import com.ecoaccess.service.AdminDashboardService;
//import com.ecoaccess.service.AdminService;
//import com.ecoaccess.service.WasteService;
//
//public class AdminActionTest {
//
//    public static void main(String[] args) {
//
//        AdminService adminService = new AdminService();
//        AdminDashboardService dashboardService =
//                new AdminDashboardService();
//
//        WasteService wasteService = new WasteService();
//
//        try {
//
//            // ==========================================
//            // 1. ADMIN LOGIN
//            // ==========================================
//
//            System.out.println("===== ADMIN LOGIN =====");
//
//            Admin admin = adminService.login(
//                    "admin@ecoaccess.com",
//                    "Test@123"
//            );
//
//            if (admin == null) {
//                throw new RuntimeException(
//                        "Admin login failed.");
//            }
//
//            System.out.println(
//                    "Admin login successful: "
//                    + admin.getId());
//
//
//            // ==========================================
//            // 2. CREATE PENDING WASTE
//            // ==========================================
//
//            System.out.println("\n===== CREATE TEST WASTE =====");
//
//            WasteSubmission waste =
//                    wasteService.submitWaste(
//                            "P1002",
//                            "test-waste-proof.jpg"
//                    );
//
//            System.out.println(
//                    "Waste ID: " + waste.getId());
//
//            System.out.println(
//                    "Status: " + waste.getStatus());
//
//
//            // ==========================================
//            // 3. ACCEPT WASTE
//            // ==========================================
//
//            System.out.println("\n===== ACCEPT WASTE =====");
//
//            dashboardService.acceptWaste(
//                    admin,
//                    waste.getId()
//            );
//
//            WasteSubmission acceptedWaste =
//                    wasteService.getSubmission(
//                            waste.getId());
//
//            System.out.println(
//                    "Waste status: "
//                    + acceptedWaste.getStatus());
//
//            System.out.println(
//                    "Reward points: "
//                    + acceptedWaste.getRewardPoints());
//
//            System.out.println(
//                    "Reviewed by: "
//                    + acceptedWaste.getReviewedBy());
//
//
//            // ==========================================
//            // 4. RESOLVE COMPLAINT
//            // ==========================================
//
//            System.out.println("\n===== RESOLVE COMPLAINT =====");
//
//            String complaintId = "CMP001";
//
//            dashboardService.resolveComplaint(
//                    admin,
//                    complaintId
//            );
//
//            Complaint resolvedComplaint =
//                    new com.ecoaccess.service.ComplaintService()
//                            .getComplaint(complaintId);
//
//            System.out.println(
//                    "Complaint status: "
//                    + resolvedComplaint.getStatus());
//
//
//            // ==========================================
//            // 5. CLOSE COMPLAINT
//            // ==========================================
//
//            System.out.println("\n===== CLOSE COMPLAINT =====");
//
//            dashboardService.closeComplaint(
//                    admin,
//                    complaintId
//            );
//
//            Complaint closedComplaint =
//                    new com.ecoaccess.service.ComplaintService()
//                            .getComplaint(complaintId);
//
//            System.out.println(
//                    "Complaint status: "
//                    + closedComplaint.getStatus());
//
//
//            // ==========================================
//            // 6. REVIEW FEEDBACK
//            // ==========================================
//
//            System.out.println("\n===== REVIEW FEEDBACK =====");
//
//            String feedbackId = "FDB001";
//
//            dashboardService.reviewFeedback(
//                    admin,
//                    feedbackId
//            );
//
//            Feedback reviewedFeedback =
//                    new com.ecoaccess.service.FeedbackService()
//                            .getFeedback(feedbackId);
//
//            System.out.println(
//                    "Feedback status: "
//                    + reviewedFeedback.getStatus());
//
//
//            // ==========================================
//            // COMPLETE
//            // ==========================================
//
//            System.out.println(
//                    "\n======================================");
//
//            System.out.println(
//                    "ADMIN ACTION TEST COMPLETED SUCCESSFULLY");
//
//            System.out.println(
//                    "======================================");
//
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    "\nAdmin action test failed!");
//
//            e.printStackTrace();
//        }
//    }
//}


package com.ecoaccess;

import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Complaint;
import com.ecoaccess.model.Feedback;
import com.ecoaccess.service.AdminDashboardService;
import com.ecoaccess.service.AdminService;
import com.ecoaccess.service.ComplaintService;
import com.ecoaccess.service.FeedbackService;

public class AdminActionTest {

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

            System.out.println(
                    "Admin login successful: "
                    + admin.getId());


            // ==========================================
            // 2. RESOLVE COMPLAINT
            // ==========================================

            System.out.println("\n===== RESOLVE COMPLAINT =====");

            String complaintId = "CMP001";

            dashboardService.resolveComplaint(
                    admin,
                    complaintId
            );

            ComplaintService complaintService =
                    new ComplaintService();

            Complaint resolvedComplaint =
                    complaintService.getComplaint(
                            complaintId
                    );

            System.out.println(
                    "Complaint status: "
                    + resolvedComplaint.getStatus()
            );


            // ==========================================
            // 3. CLOSE COMPLAINT
            // ==========================================

            System.out.println("\n===== CLOSE COMPLAINT =====");

            dashboardService.closeComplaint(
                    admin,
                    complaintId
            );

            Complaint closedComplaint =
                    complaintService.getComplaint(
                            complaintId
                    );

            System.out.println(
                    "Complaint status: "
                    + closedComplaint.getStatus()
            );


            // ==========================================
            // 4. REVIEW FEEDBACK
            // ==========================================

            System.out.println("\n===== REVIEW FEEDBACK =====");

            String feedbackId = "FDB001";

            dashboardService.reviewFeedback(
                    admin,
                    feedbackId
            );

            FeedbackService feedbackService =
                    new FeedbackService();

            Feedback reviewedFeedback =
                    feedbackService.getFeedback(
                            feedbackId
                    );

            System.out.println(
                    "Feedback status: "
                    + reviewedFeedback.getStatus()
            );


            // ==========================================
            // COMPLETE
            // ==========================================

            System.out.println(
                    "\n======================================");

            System.out.println(
                    "ADMIN ACTION TEST COMPLETED SUCCESSFULLY");

            System.out.println(
                    "======================================");


        } catch (Exception e) {

            System.out.println(
                    "\nAdmin action test failed!");

            e.printStackTrace();
        }
    }
}