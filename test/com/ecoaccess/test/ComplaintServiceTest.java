package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Complaint;
import com.ecoaccess.service.ComplaintService;

public class ComplaintServiceTest {

    private final ComplaintService complaintService =
            new ComplaintService();

    @Test
    void createComplaintSuccessfully() {

        Complaint complaint =
                complaintService.createComplaint(
                        "P1002",
                        "BK-CF0B465E",
                        "JUnit Test Complaint",
                        "Testing complaint creation.",
                        3
                );

        assertNotNull(complaint);

        assertNotNull(complaint.getId());

        assertEquals(
                "P1002",
                complaint.getPassengerId()
        );

        assertEquals(
                "BK-CF0B465E",
                complaint.getBookingId()
        );

        assertEquals(
                "JUnit Test Complaint",
                complaint.getSubject()
        );

        assertEquals(
                3,
                complaint.getRating()
        );

        assertEquals(
                "Open",
                complaint.getStatus()
        );
    }


    @Test
    void complaintFailsForInvalidRating() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> complaintService.createComplaint(
                                "P1002",
                                "BK-CF0B465E",
                                "Invalid Rating",
                                "Testing invalid rating.",
                                6
                        )
                );

        assertEquals(
                "Rating must be between 1 and 5.",
                exception.getMessage()
        );
    }


    @Test
    void complaintFailsWhenBookingBelongsToAnotherPassenger() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> complaintService.createComplaint(
                                "P1002",
                                "BK-240101",
                                "Wrong Booking",
                                "Testing ownership validation.",
                                3
                        )
                );

        assertEquals(
                "Booking does not belong to passenger.",
                exception.getMessage()
        );
    }


    @Test
    void getPassengerComplaintsSuccessfully() {

        List<Complaint> complaints =
                complaintService.getPassengerComplaints(
                        "P1002"
                );

        assertNotNull(complaints);

        assertFalse(
                complaints.isEmpty()
        );

        for (Complaint complaint : complaints) {
            assertEquals(
                    "P1002",
                    complaint.getPassengerId()
            );
        }
    }


    @Test
    void getBookingComplaintsSuccessfully() {

        List<Complaint> complaints =
                complaintService.getBookingComplaints(
                        "BK-CF0B465E"
                );

        assertNotNull(complaints);

        assertFalse(
                complaints.isEmpty()
        );

        for (Complaint complaint : complaints) {
            assertEquals(
                    "BK-CF0B465E",
                    complaint.getBookingId()
            );
        }
    }


    @Test
    void resolveAndCloseComplaintSuccessfully() {

        Complaint created =
                complaintService.createComplaint(
                        "P1002",
                        "BK-CF0B465E",
                        "JUnit Lifecycle Complaint",
                        "Testing resolve and close.",
                        4
                );

        String complaintId =
                created.getId();

        Complaint resolved =
                complaintService.resolveComplaint(
                        complaintId
                );

        assertNotNull(resolved);

        assertEquals(
                "Resolved",
                resolved.getStatus()
        );

        Complaint closed =
                complaintService.closeComplaint(
                        complaintId
                );

        assertNotNull(closed);

        assertEquals(
                "Closed",
                closed.getStatus()
        );
    }


    @Test
    void closeOpenComplaintFails() {

        Complaint created =
                complaintService.createComplaint(
                        "P1002",
                        "BK-CF0B465E",
                        "JUnit Invalid Close",
                        "Testing invalid close operation.",
                        3
                );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> complaintService.closeComplaint(
                                created.getId()
                        )
                );

        assertEquals(
                "Only resolved complaints can be closed.",
                exception.getMessage()
        );
    }
}	