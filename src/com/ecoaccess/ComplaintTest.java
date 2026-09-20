package com.ecoaccess;

import com.ecoaccess.model.Complaint;
import com.ecoaccess.service.ComplaintService;

public class ComplaintTest {

    public static void main(String[] args) {

        ComplaintService complaintService =
                new ComplaintService();

        System.out.println("================================");
        System.out.println("      COMPLAINT SYSTEM TEST");
        System.out.println("================================");

        // ------------------------------------------
        // 1. Create Complaint
        // ------------------------------------------

        System.out.println(
                "\n1. CREATE COMPLAINT"
        );

        Complaint complaint =
                complaintService.createComplaint(
                        "P1002",
                        "BK-CF0B465E",
                        "Wheelchair service issue",
                        "The wheelchair service was delayed.",
                        2
                );

        System.out.println(
                "Complaint ID: "
                + complaint.getId()
        );

        System.out.println(
                "Passenger ID: "
                + complaint.getPassengerId()
        );

        System.out.println(
                "Booking ID: "
                + complaint.getBookingId()
        );

        System.out.println(
                "Subject: "
                + complaint.getSubject()
        );

        System.out.println(
                "Rating: "
                + complaint.getRating()
        );

        System.out.println(
                "Status: "
                + complaint.getStatus()
        );

        // ------------------------------------------
        // 2. Admin Views Open Complaints
        // ------------------------------------------

        System.out.println(
                "\n2. OPEN COMPLAINTS"
        );

        complaintService
                .getOpenComplaints()
                .forEach(c -> {

                    System.out.println(
                            c.getId()
                            + " | "
                            + c.getPassengerId()
                            + " | "
                            + c.getStatus()
                    );
                });

        // ------------------------------------------
        // 3. Resolve Complaint
        // ------------------------------------------

        System.out.println(
                "\n3. RESOLVE COMPLAINT"
        );

        Complaint resolved =
                complaintService.resolveComplaint(
                        complaint.getId()
                );

        System.out.println(
                "Complaint ID: "
                + resolved.getId()
        );

        System.out.println(
                "Status: "
                + resolved.getStatus()
        );

        // ------------------------------------------
        // 4. Close Complaint
        // ------------------------------------------

        System.out.println(
                "\n4. CLOSE COMPLAINT"
        );

        Complaint closed =
                complaintService.closeComplaint(
                        complaint.getId()
                );

        System.out.println(
                "Complaint ID: "
                + closed.getId()
        );

        System.out.println(
                "Status: "
                + closed.getStatus()
        );

        System.out.println("\n================================");
        System.out.println("       TEST COMPLETED");
        System.out.println("================================");
    }
}