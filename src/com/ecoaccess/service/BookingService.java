package com.ecoaccess.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.ecoaccess.dao.BookingDAO;
import com.ecoaccess.dao.JourneyDAO;
import com.ecoaccess.dao.JourneyValidationDAO;
import com.ecoaccess.dao.StaffAssignmentDAO;
import com.ecoaccess.dao.VehicleDAO;
import com.ecoaccess.dao.WheelchairDAO;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Journey;
import com.ecoaccess.model.Staff;

public class BookingService {

    private static final double GST_RATE = 0.05;

    private static final double PORTER_BASE_FARE = 50.0;
    private static final double WHEELCHAIR_BASE_FARE = 50.0;
    private static final double VEHICLE_BASE_FARE = 70.0;

    private final BookingDAO bookingDAO;
    private final JourneyDAO journeyDAO;
    private final JourneyValidationDAO validationDAO;
    private final WheelchairDAO wheelchairDAO;
    private final VehicleDAO vehicleDAO;
    private final StaffAssignmentDAO staffAssignmentDAO;

    public BookingService() {

        this.bookingDAO = new BookingDAO();
        this.journeyDAO = new JourneyDAO();
        this.validationDAO = new JourneyValidationDAO();
        this.wheelchairDAO = new WheelchairDAO();
        this.vehicleDAO = new VehicleDAO();
        this.staffAssignmentDAO = new StaffAssignmentDAO();
    }

    public Booking createBooking(
            String passengerId,
            String journeyId,
            String service,
            String pickupPoint,
            String dropPlatform,
            int passengerCount,
            int bags,
            double totalWeight,
            double discount) {

        // ==========================================
        // 1. BASIC VALIDATION
        // ==========================================

        if (passengerId == null || passengerId.isBlank()) {
            throw new IllegalArgumentException(
                    "Passenger ID is required.");
        }

        if (journeyId == null || journeyId.isBlank()) {
            throw new IllegalArgumentException(
                    "Journey ID is required.");
        }

        if (service == null || service.isBlank()) {
            throw new IllegalArgumentException(
                    "Service is required.");
        }

        // ==========================================
        // 2. PASSENGER COUNT
        // ==========================================

        if (passengerCount < 1 || passengerCount > 8) {
            throw new IllegalArgumentException(
                    "Passenger count must be between 1 and 8.");
        }

        // ==========================================
        // 3. BAG LIMIT
        // ==========================================

        if (bags < 0 || bags > 20) {
            throw new IllegalArgumentException(
                    "Number of bags must be between 0 and 20.");
        }

        // ==========================================
        // 4. FIND JOURNEY
        // ==========================================

//        Journey journey =
//                journeyDAO.findByPnr(
//                        findPnrFromJourney(journeyId));
        
        Journey journey =
                journeyDAO.findById(journeyId);

        if (journey == null) {
            throw new IllegalArgumentException(
                    "Journey not found.");
        }

        // ==========================================
        // 5. CHECK JOURNEY VALIDATION
        // ==========================================

        boolean validated =
                validationDAO.isValidated(
                        passengerId,
                        journeyId);

        if (!validated) {
            throw new IllegalStateException(
                    "Passenger must validate the journey before booking.");
        }

        // ==========================================
        // 6. SERVICE VALIDATION
        // ==========================================

        if (!service.equals("Porter")
                && !service.equals("Wheelchair")
                && !service.equals("Inter Vehicle")) {

            throw new IllegalArgumentException(
                    "Invalid service.");
        }

        // ==========================================
        // 7. RESOURCE AVAILABILITY
        // ==========================================

        if (service.equals("Wheelchair")) {

            int quantity =
                    wheelchairDAO.getAvailableQuantity(
                            journey.getStationId());

            if (quantity <= 0) {
                throw new IllegalStateException(
                        "No wheelchair available at this station.");
            }
        }

        if (service.equals("Inter Vehicle")) {

            int quantity =
                    vehicleDAO.getAvailableQuantity(
                            journey.getStationId());

            if (quantity <= 0) {
                throw new IllegalStateException(
                        "No inter-platform vehicle available.");
            }
        }

        // ==========================================
        // 8. CALCULATE FARE
        // ==========================================

        double baseFare = calculateBaseFare(
                service,
                bags,
                totalWeight);

        double taxAmount =
                baseFare * GST_RATE;

        double grossFare =
                baseFare + taxAmount;

        // Prevent invalid discount
        if (discount < 0) {
            discount = 0;
        }

        if (discount > grossFare) {
            discount = grossFare;
        }

        double finalFare =
                grossFare - discount;

        // ==========================================
        // 9. FIND STAFF
        // ==========================================

        String requiredRole =
                getRequiredStaffRole(service);

        Staff staff =
                staffAssignmentDAO.findAvailableStaff(
                        requiredRole);

        String staffId = null;
        String status = "Booked";

        if (staff != null) {

            staffId = staff.getId();
            status = "Assigned";

            staffAssignmentDAO.updateStatus(
                    staff.getId(),
                    "Busy"
            );
        }

        // ==========================================
        // 10. CREATE BOOKING ID
        // ==========================================

        String bookingId =
                "BK-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        // ==========================================
        // 11. CREATE BOOKING
        // ==========================================

        Booking booking = new Booking(
                bookingId,
                passengerId,
                journeyId,
                service,
                journey.getStationId(),
                journey.getPlatform(),
                LocalDate.now(),
                LocalTime.now(),
                pickupPoint,
                dropPlatform,
                passengerCount,
                bags,
                getWeightRange(totalWeight),
                baseFare,
                taxAmount,
                grossFare,
                discount,
                finalFare,
                status,
                staffId
        );

        // ==========================================
        // 12. SAVE
        // ==========================================

        boolean saved =
                bookingDAO.save(booking);

        if (!saved) {
            throw new IllegalStateException(
                    "Failed to create booking.");
        }

        return booking;
    }


    private double calculateBaseFare(
            String service,
            int bags,
            double totalWeight) {

        if (service.equals("Wheelchair")) {
            return WHEELCHAIR_BASE_FARE;
        }

        if (service.equals("Inter Vehicle")) {
            return VEHICLE_BASE_FARE;
        }

        // Porter
        if (service.equals("Porter")) {

            double pricePerBag;

            if (totalWeight <= 10) {
                pricePerBag = 50;
            }
            else if (totalWeight <= 20) {
                pricePerBag = 60;
            }
            else if (totalWeight <= 30) {
                pricePerBag = 70;
            }
            else {
                pricePerBag = 80;
            }

            return PORTER_BASE_FARE
                    + (bags * pricePerBag);
        }

        return 0;
    }


    private String getWeightRange(double weight) {

        if (weight <= 10) {
            return "1-10 kg";
        }

        if (weight <= 20) {
            return "11-20 kg";
        }

        if (weight <= 30) {
            return "21-30 kg";
        }

        return "31+ kg";
    }


    private String getRequiredStaffRole(
            String service) {

        if (service.equals("Porter")) {
            return "Porter";
        }

        if (service.equals("Wheelchair")) {
            return "Wheelchair";
        }

        return "Vehicle";
    }


    private String findPnrFromJourney(
            String journeyId) {

        /*
         * Current JourneyDAO searches by PNR.
         * We need to add a direct journeyId
         * lookup before using this method.
         *
         * Temporary placeholder.
         */

        if (journeyId.equals("JRN001")) {
            return "4521987630";
        }

        if (journeyId.equals("JRN002")) {
            return "6109873421";
        }

        if (journeyId.equals("JRN003")) {
            return "8234561907";
        }

        return "";
    }
}