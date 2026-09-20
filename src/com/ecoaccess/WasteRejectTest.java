//package com.ecoaccess;
//
//import com.ecoaccess.model.WasteSubmission;
//import com.ecoaccess.service.WasteService;
//
//public class WasteRejectTest {
//
//    public static void main(String[] args) {
//
//        WasteService wasteService =
//                new WasteService();
//
//        System.out.println("================================");
//        System.out.println("    WASTE REJECTION TEST");
//        System.out.println("================================");
//
//        // ------------------------------------------
//        // 1. Submit waste proof
//        // ------------------------------------------
//
//        System.out.println("\n1. SUBMIT WASTE PROOF");
//
//        WasteSubmission submission =
//                wasteService.submitWaste(
//                        "P1002",
//                        "invalid_waste_proof.jpg"
//                );
//
//        System.out.println(
//                "Submission ID: "
//                + submission.getId()
//        );
//
//        System.out.println(
//                "Status: "
//                + submission.getStatus()
//        );
//
//        // ------------------------------------------
//        // 2. Admin rejects submission
//        // ------------------------------------------
//
//        System.out.println(
//                "\n2. ADMIN REJECTS SUBMISSION"
//        );
//
//        WasteSubmission rejected =
//                wasteService.rejectSubmission(
//                        submission.getId(),
//                        "ADM1",
//                        "Waste disposal proof is not clear."
//                );
//
//        System.out.println(
//                "Submission ID: "
//                + rejected.getId()
//        );
//
//        System.out.println(
//                "Status: "
//                + rejected.getStatus()
//        );
//
//        System.out.println(
//                "Reward Points: "
//                + rejected.getRewardPoints()
//        );
//
//        System.out.println(
//                "Reviewed By: "
//                + rejected.getReviewedBy()
//        );
//
//        System.out.println(
//                "Remark: "
//                + rejected.getRemark()
//        );
//
//        System.out.println("\n================================");
//        System.out.println("       TEST COMPLETED");
//        System.out.println("================================");
//    }
//}



package com.ecoaccess;

import com.ecoaccess.model.WasteSubmission;
import com.ecoaccess.service.WasteService;

public class WasteRejectTest {

    public static void main(String[] args) {

        WasteService wasteService =
                new WasteService();

        String submissionId = "WS-0A2FDAEA";

        System.out.println("================================");
        System.out.println("    WASTE REJECTION TEST");
        System.out.println("================================");

        // ------------------------------------------
        // 1. Get existing pending submission
        // ------------------------------------------

        System.out.println(
                "\n1. EXISTING SUBMISSION"
        );

        WasteSubmission submission =
                wasteService.getSubmission(
                        submissionId
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
                "Status: "
                + submission.getStatus()
        );

        // ------------------------------------------
        // 2. Admin rejects submission
        // ------------------------------------------

        System.out.println(
                "\n2. ADMIN REJECTS SUBMISSION"
        );

        WasteSubmission rejected =
                wasteService.rejectSubmission(
                        submissionId,
                        "ADM1",
                        "Waste disposal proof is not clear."
                );

        System.out.println(
                "Submission ID: "
                + rejected.getId()
        );

        System.out.println(
                "Status: "
                + rejected.getStatus()
        );

        System.out.println(
                "Reward Points: "
                + rejected.getRewardPoints()
        );

        System.out.println(
                "Reviewed By: "
                + rejected.getReviewedBy()
        );

        System.out.println(
                "Remark: "
                + rejected.getRemark()
        );

        System.out.println("\n================================");
        System.out.println("       TEST COMPLETED");
        System.out.println("================================");
    }
}