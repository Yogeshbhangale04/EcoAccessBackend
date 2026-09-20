package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.WasteSubmission;
import com.ecoaccess.service.WasteService;

public class WasteServiceTest {

    private final WasteService wasteService =
            new WasteService();

    @Test
    void getExistingSubmissionSuccessfully() {

        WasteSubmission submission =
                wasteService.getSubmission(
                        "WS-0D42703A"
                );

        assertNotNull(submission);

        assertEquals(
                "WS-0D42703A",
                submission.getId()
        );

        assertEquals(
                "P1001",
                submission.getPassengerId()
        );

        assertEquals(
                "Accepted",
                submission.getStatus()
        );

        assertEquals(
                20,
                submission.getRewardPoints()
        );
    }


    @Test
    void getNonExistingSubmissionReturnsNull() {

        WasteSubmission submission =
                wasteService.getSubmission(
                        "WS-DOES-NOT-EXIST"
                );

        assertNull(submission);
    }


    @Test
    void getPassengerSubmissionsSuccessfully() {

        List<WasteSubmission> submissions =
                wasteService.getPassengerSubmissions(
                        "P1001"
                );

        assertNotNull(submissions);

        assertFalse(
                submissions.isEmpty()
        );

        assertEquals(
                "P1001",
                submissions.get(0).getPassengerId()
        );
    }


    @Test
    void getPendingSubmissionsReturnsList() {

        List<WasteSubmission> submissions =
                wasteService.getPendingSubmissions();

        assertNotNull(submissions);

        for (WasteSubmission submission : submissions) {
            assertEquals(
                    "Pending",
                    submission.getStatus()
            );
        }
    }


    @Test
    void submitWasteFailsDuringCooldown() {

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> wasteService.submitWaste(
                                "P1001",
                                "test-photo.jpg"
                        )
                );

        assertTrue(
                exception.getMessage()
                        .startsWith(
                                "You can submit waste proof again after"
                        )
        );
    }


    @Test
    void submitWasteFailsWithoutPassengerId() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> wasteService.submitWaste(
                                "",
                                "test-photo.jpg"
                        )
                );

        assertEquals(
                "Passenger ID is required.",
                exception.getMessage()
        );
    }


    @Test
    void submitWasteFailsWithoutPhoto() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> wasteService.submitWaste(
                                "P1001",
                                ""
                        )
                );

        assertEquals(
                "Waste proof photo is required.",
                exception.getMessage()
        );
    }
}