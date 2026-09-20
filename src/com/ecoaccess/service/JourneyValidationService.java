package com.ecoaccess.service;

import java.util.UUID;

import com.ecoaccess.dao.JourneyDAO;
import com.ecoaccess.dao.JourneyValidationDAO;
import com.ecoaccess.model.Journey;

public class JourneyValidationService {

    private final JourneyDAO journeyDAO;
    private final JourneyValidationDAO validationDAO;

    public JourneyValidationService() {
        this.journeyDAO = new JourneyDAO();
        this.validationDAO = new JourneyValidationDAO();
    }

    public Journey validateJourney(String passengerId, String pnr) {

        if (passengerId == null || passengerId.isBlank()) {
            return null;
        }

        if (pnr == null || pnr.isBlank()) {
            return null;
        }

        Journey journey = journeyDAO.findByPnr(pnr);

        if (journey == null) {
            return null;
        }

        if (validationDAO.isValidated(
                passengerId,
                journey.getId())) {

            return journey;
        }

        String validationId =
                "JV-" + UUID.randomUUID()
                           .toString()
                           .substring(0, 8)
                           .toUpperCase();

        boolean saved =
                validationDAO.saveValidation(
                        validationId,
                        passengerId,
                        journey.getId());

        if (!saved) {
            return null;
        }

        return journey;
    }
}