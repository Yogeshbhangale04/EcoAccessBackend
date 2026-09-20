package com.ecoaccess;

import com.ecoaccess.model.WasteSubmission;
import com.ecoaccess.service.WasteService;

public class WasteTest {

    public static void main(String[] args) {

        WasteService wasteService =
                new WasteService();

        System.out.println("================================");
        System.out.println("      WASTE MANAGEMENT TEST");
        System.out.println("================================");

        // ------------------------------------------
        // 1. Submit waste proof
        // ------------------------------------------

        System.out.println("\n1. SUBMIT WASTE PROOF");

        WasteSubmission submission =
                wasteService.submitWaste(
                        "P1001",
                        "waste_proof_test.jpg"
                );

        System.out.println(
                "Submission ID: "
                + submission.getId()
        );

        System.out.println(
                "Passenger ID: "
                + submission.getPassengerId()
        );

        System.out.println(
                "Photo: "
                + submission.getPhoto()
        );

        System.out.println(
                "Status: "
                + submission.getStatus()
        );

        System.out.println(
                "Reward Points: "
                + submission.getRewardPoints()
        );

        // ------------------------------------------
        // 2. Admin views pending submissions
        // ------------------------------------------

        System.out.println(
                "\n2. PENDING SUBMISSIONS"
        );

        wasteService
                .getPendingSubmissions()
                .forEach(w -> {

                    System.out.println(
                            w.getId()
                            + " | "
                            + w.getPassengerId()
                            + " | "
                            + w.getStatus()
                    );
                });

        // ------------------------------------------
        // 3. Admin accepts submission
        // ------------------------------------------

        System.out.println(
                "\n3. ADMIN ACCEPTS SUBMISSION"
        );

        WasteSubmission accepted =
                wasteService.acceptSubmission(
                        submission.getId(),
                        "ADM1"
                );

        System.out.println(
                "Submission ID: "
                + accepted.getId()
        );

        System.out.println(
                "Status: "
                + accepted.getStatus()
        );

        System.out.println(
                "Reward Points: "
                + accepted.getRewardPoints()
        );

        System.out.println(
                "Reviewed By: "
                + accepted.getReviewedBy()
        );

        System.out.println(
                "Remark: "
                + accepted.getRemark()
        );

        // ------------------------------------------
        // 4. Final submission status
        // ------------------------------------------

        System.out.println(
                "\n4. FINAL SUBMISSION"
        );

        WasteSubmission finalSubmission =
                wasteService.getSubmission(
                        submission.getId()
                );

        System.out.println(
                "ID: "
                + finalSubmission.getId()
        );

        System.out.println(
                "Status: "
                + finalSubmission.getStatus()
        );

        System.out.println(
                "Reward: "
                + finalSubmission.getRewardPoints()
        );

        System.out.println(
                "Reviewed By: "
                + finalSubmission.getReviewedBy()
        );

        System.out.println("\n================================");
        System.out.println("       TEST COMPLETED");
        System.out.println("================================");
    }
}