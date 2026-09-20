package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Journey;
import com.ecoaccess.service.JourneyValidationService;

public class JourneyValidationServiceTest {

    private final JourneyValidationService service =
            new JourneyValidationService();

    @Test
    void validateValidJourney() {

        Journey journey =
                service.validateJourney(
                        "P1001",
                        "4521987630"
                );

        assertNotNull(journey);

        assertEquals(
                "JRN001",
                journey.getId()
        );

        assertEquals(
                "4521987630",
                journey.getPnr()
        );
    }

    @Test
    void validateInvalidPnr() {

        Journey journey =
                service.validateJourney(
                        "P1001",
                        "0000000000"
                );

        assertNull(journey);
    }

    @Test
    void validateBlankPassengerId() {

        Journey journey =
                service.validateJourney(
                        "",
                        "4521987630"
                );

        assertNull(journey);
    }

    @Test
    void validateBlankPnr() {

        Journey journey =
                service.validateJourney(
                        "P1001",
                        ""
                );

        assertNull(journey);
    }
}