package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Feedback;
import com.ecoaccess.service.FeedbackService;

public class FeedbackServiceTest {

    private final FeedbackService feedbackService =
            new FeedbackService();

    @Test
    void createFeedbackSuccessfully() throws SQLException {

        Feedback feedback =
                feedbackService.createFeedback(
                        "P1002",
                        "BK-CF0B465E",
                        4,
                        "JUnit Test Feedback",
                        "Testing feedback creation."
                );

        assertNotNull(feedback);

        assertNotNull(feedback.getId());

        assertEquals(
                "P1002",
                feedback.getPassengerId()
        );

        assertEquals(
                "BK-CF0B465E",
                feedback.getBookingId()
        );

        assertEquals(
                4,
                feedback.getRating()
        );

        assertEquals(
                "JUnit Test Feedback",
                feedback.getSubject()
        );

        assertEquals(
                "Testing feedback creation.",
                feedback.getDescription()
        );

        assertEquals(
                "Submitted",
                feedback.getStatus()
        );

        assertNotNull(feedback.getCreatedAt());
    }


    @Test
    void feedbackFailsForInvalidRating()
            throws SQLException {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> feedbackService.createFeedback(
                                "P1002",
                                "BK-CF0B465E",
                                6,
                                "Invalid Rating",
                                "Testing invalid rating."
                        )
                );

        assertEquals(
                "Rating must be between 1 and 5.",
                exception.getMessage()
        );
    }


    @Test
    void feedbackFailsWhenBookingBelongsToAnotherPassenger()
            throws SQLException {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> feedbackService.createFeedback(
                                "P1002",
                                "BK-240101",
                                4,
                                "Wrong Booking",
                                "Testing ownership validation."
                        )
                );

        assertEquals(
                "Booking does not belong to this passenger.",
                exception.getMessage()
        );
    }


    @Test
    void getFeedbackSuccessfully()
            throws SQLException {

        Feedback created =
                feedbackService.createFeedback(
                        "P1002",
                        "BK-CF0B465E",
                        5,
                        "Get Feedback Test",
                        "Testing feedback retrieval."
                );

        Feedback feedback =
                feedbackService.getFeedback(
                        created.getId()
                );

        assertNotNull(feedback);

        assertEquals(
                created.getId(),
                feedback.getId()
        );

        assertEquals(
                "P1002",
                feedback.getPassengerId()
        );
    }


    @Test
    void getPassengerFeedbackSuccessfully()
            throws SQLException {

        List<Feedback> feedbackList =
                feedbackService.getPassengerFeedback(
                        "P1002"
                );

        assertNotNull(feedbackList);

        assertFalse(
                feedbackList.isEmpty()
        );

        for (Feedback feedback : feedbackList) {
            assertEquals(
                    "P1002",
                    feedback.getPassengerId()
            );
        }
    }


    @Test
    void getBookingFeedbackSuccessfully()
            throws SQLException {

        List<Feedback> feedbackList =
                feedbackService.getBookingFeedback(
                        "BK-CF0B465E"
                );

        assertNotNull(feedbackList);

        assertFalse(
                feedbackList.isEmpty()
        );

        for (Feedback feedback : feedbackList) {
            assertEquals(
                    "BK-CF0B465E",
                    feedback.getBookingId()
            );
        }
    }


    @Test
    void reviewFeedbackSuccessfully()
            throws SQLException {

        Feedback created =
                feedbackService.createFeedback(
                        "P1002",
                        "BK-CF0B465E",
                        3,
                        "Review Test",
                        "Testing feedback review."
                );

        feedbackService.reviewFeedback(
                created.getId()
        );

        Feedback reviewed =
                feedbackService.getFeedback(
                        created.getId()
                );

        assertEquals(
                "Reviewed",
                reviewed.getStatus()
        );
    }


    @Test
    void reviewAlreadyReviewedFeedbackFails()
            throws SQLException {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> feedbackService.reviewFeedback(
                                "FDB001"
                        )
                );

        assertEquals(
                "Only submitted feedback can be reviewed.",
                exception.getMessage()
        );
    }
}