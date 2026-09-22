package com.ecoaccess;

import com.ecoaccess.dao.AdminDAO;
import com.ecoaccess.dao.StaffDAO;
import com.ecoaccess.dao.VehicleDAO;
import com.ecoaccess.dao.WheelchairDAO;
import com.ecoaccess.model.Admin;
import com.ecoaccess.model.Staff;
import com.ecoaccess.model.Vehicle;
import com.ecoaccess.model.Wheelchair;
import com.ecoaccess.model.Booking;
import com.ecoaccess.model.Passenger;
import com.ecoaccess.model.Journey;
import com.ecoaccess.service.BookingService;
import com.ecoaccess.service.ComplaintService;
import com.ecoaccess.service.FeedbackService;
import com.ecoaccess.service.JourneyValidationService;
import com.ecoaccess.service.PassengerService;
import com.ecoaccess.service.PaymentService;
import com.ecoaccess.service.StaffBookingService;
import com.ecoaccess.service.StaffService;
import com.ecoaccess.service.WasteService;
import com.ecoaccess.service.RewardService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/** Menu-driven console application for the three EcoAccess stakeholders. */
public class ConsoleApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final PassengerService passengerService = new PassengerService();
    private final StaffService staffService = new StaffService();
    private final AdminDAO adminDAO = new AdminDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final WheelchairDAO wheelchairDAO = new WheelchairDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    public void start() {
        while (true) {
            System.out.println("\n========== ECOACCESS ==========");
            System.out.println("1. Passenger");
            System.out.println("2. Staff");
            System.out.println("3. Admin");
            System.out.println("0. Exit");
            int choice = integer("Select stakeholder: ");
            try {
                switch (choice) {
                    case 1 -> passengerMenu();
                    case 2 -> staffMenu();
                    case 3 -> adminMenu();
                    case 0 -> { System.out.println("Goodbye."); return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void passengerMenu() {
        Passenger passenger = passengerService.login(text("Mobile: "), text("Password: "));
        if (passenger == null) { System.out.println("Invalid passenger login."); return; }
        JourneyValidationService validation = new JourneyValidationService();
        BookingService bookings = new BookingService();
        PaymentService payments = new PaymentService();
        WasteService waste = new WasteService();
        ComplaintService complaints = new ComplaintService();
        FeedbackService feedback = new FeedbackService();
        RewardService rewards = new RewardService();

        while (true) {
            System.out.println("\n--- Passenger: " + passenger.getName() + " ---");
            System.out.println("1. Validate journey");
            System.out.println("2. Create accessibility booking");
            System.out.println("3. Make payment");
            System.out.println("4. Submit waste proof");
            System.out.println("5. Redeem reward points");
            System.out.println("6. Submit complaint");
            System.out.println("7. Submit feedback");
            System.out.println("0. Logout");
            int c = integer("Choice: ");
            try {
                switch (c) {
                    case 1 -> {
                        Journey j = validation.validateJourney(passenger.getId(), text("PNR: "));
                        System.out.println(j == null ? "Journey validation failed." : "Journey validated: " + j.getId());
                    }
                    case 2 -> {
                        Booking b = bookings.createBooking(passenger.getId(), text("Journey ID: "),
                                text("Service (Porter/Wheelchair/Inter Vehicle): "), text("Pickup point: "),
                                text("Drop platform: "), integer("Passenger count: "), integer("Bags: "),
                                decimal("Total weight: "), decimal("Discount: "));
                        System.out.println("Booking created: " + b.getId() + ", fare=" + b.getFinalFare());
                    }
                    case 3 -> System.out.println("Payment successful: " + payments.makePayment(text("Booking ID: "), decimal("Amount: "), text("Method (UPI/CARD): ")).getId());
                    case 4 -> System.out.println("Waste submitted: " + waste.submitWaste(passenger.getId(), text("Photo path/reference: ")).getId());
                    case 5 -> System.out.println("Coupon created: " + rewards.redeemPoints(passenger.getId()).getCode());
                    case 6 -> System.out.println("Complaint created: " + complaints.createComplaint(passenger.getId(), text("Booking ID: "), text("Subject: "), text("Description: "), integer("Rating 1-5: ")).getId());
                    case 7 -> System.out.println("Feedback created: " + feedback.createFeedback(passenger.getId(), text("Booking ID: "), integer("Rating 1-5: "), text("Subject: "), text("Description: ")).getId());
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) { System.out.println("Operation failed: " + e.getMessage()); }
        }
    }

    private void staffMenu() {
        Staff staff = staffService.login(text("Employee ID: "), text("Password: "));
        if (staff == null) { System.out.println("Invalid staff login."); return; }
        StaffBookingService service = new StaffBookingService();
        while (true) {
            System.out.println("\n--- Staff: " + staff.getName() + " (" + staff.getRole() + ") ---");
            System.out.println("1. View assigned bookings");
            System.out.println("2. Accept booking");
            System.out.println("3. Reject booking");
            System.out.println("4. Mark reached passenger");
            System.out.println("5. Start service");
            System.out.println("6. Complete service");
            System.out.println("0. Logout");
            int c = integer("Choice: ");
            try {
                switch (c) {
                    case 1 -> service.getAssignedBookings(staff.getId()).forEach(System.out::println);
                    case 2 -> System.out.println(service.acceptBooking(text("Booking ID: "), staff));
                    case 3 -> System.out.println(service.rejectBooking(text("Booking ID: "), staff));
                    case 4 -> System.out.println(service.reachedPassenger(text("Booking ID: "), staff));
                    case 5 -> System.out.println(service.startService(text("Booking ID: "), staff));
                    case 6 -> System.out.println(service.completeService(text("Booking ID: "), staff));
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) { System.out.println("Operation failed: " + e.getMessage()); }
        }
    }

    private void adminMenu() {
        Admin admin = new com.ecoaccess.service.AdminService().login(text("Email: "), text("Password: "));
        if (admin == null) { System.out.println("Invalid admin login."); return; }
        while (true) {
            System.out.println("\n--- Admin: " + admin.getName() + " ---");
            System.out.println("1. Staff CRUD");
            System.out.println("2. Wheelchair CRUD");
            System.out.println("3. Vehicle CRUD");
            System.out.println("0. Logout");
            int c = integer("Choice: ");
            if (c == 0) return;
            if (c == 1) staffCrud(); else if (c == 2) wheelchairCrud(); else if (c == 3) vehicleCrud(); else System.out.println("Invalid choice.");
        }
    }

    private void staffCrud() {
        System.out.println("1 Add  2 List  3 Update  4 Delete  0 Back");
        int c=integer("Choice: ");
        try {
            if(c==1){ Staff s=new Staff("STF"+shortId(),text("Employee ID: "),text("Name: "),text("Password: "),text("Role (Porter/Wheelchair/Vehicle): "),text("Status (Available/Unavailable/Busy/Offline): ")); System.out.println(staffDAO.save(s)?"Created: "+s:"Create failed"); }
            else if(c==2) staffDAO.findAll().forEach(System.out::println);
            else if(c==3){String id=text("Staff ID: "); Staff s=staffDAO.findById(id); if(s==null){System.out.println("Not found");return;} s.setEmployeeId(text("Employee ID: "));s.setName(text("Name: "));s.setPassword(text("Password: "));s.setRole(text("Role: "));s.setStatus(text("Status: "));System.out.println(staffDAO.update(s)?"Updated":"Update failed");}
            else if(c==4) System.out.println(staffDAO.delete(text("Staff ID: "))?"Deleted":"Delete failed");
        } catch(Exception e){System.out.println("Operation failed: "+e.getMessage());}
    }

    private void wheelchairCrud() {
        System.out.println("1 Add  2 List  3 Update  4 Delete  0 Back"); int c=integer("Choice: ");
        try { if(c==1){Wheelchair w=new Wheelchair("WC"+shortId(),text("Station ID: "),nonNegative("Quantity: "));System.out.println(wheelchairDAO.save(w)?"Created: "+w:"Create failed");} else if(c==2)wheelchairDAO.findAll().forEach(System.out::println); else if(c==3){String id=text("Wheelchair ID: ");Wheelchair w=wheelchairDAO.findById(id);if(w==null){System.out.println("Not found");return;}w.setStationId(text("Station ID: "));w.setQuantity(nonNegative("Quantity: "));System.out.println(wheelchairDAO.update(w)?"Updated":"Update failed");} else if(c==4)System.out.println(wheelchairDAO.delete(text("Wheelchair ID: "))?"Deleted":"Delete failed");}catch(Exception e){System.out.println("Operation failed: "+e.getMessage());}
    }

    private void vehicleCrud() {
        System.out.println("1 Add  2 List  3 Update  4 Delete  0 Back"); int c=integer("Choice: ");
        try { if(c==1){Vehicle v=new Vehicle("V"+shortId(),text("Station ID: "),nonNegative("Quantity: "));System.out.println(vehicleDAO.save(v)?"Created: "+v:"Create failed");} else if(c==2)vehicleDAO.findAll().forEach(System.out::println); else if(c==3){String id=text("Vehicle ID: ");Vehicle v=vehicleDAO.findById(id);if(v==null){System.out.println("Not found");return;}v.setStationId(text("Station ID: "));v.setQuantity(nonNegative("Quantity: "));System.out.println(vehicleDAO.update(v)?"Updated":"Update failed");} else if(c==4)System.out.println(vehicleDAO.delete(text("Vehicle ID: "))?"Deleted":"Delete failed");}catch(Exception e){System.out.println("Operation failed: "+e.getMessage());}
    }

    private String text(String label){System.out.print(label);return scanner.nextLine().trim();}
    private int integer(String label){while(true)try{return Integer.parseInt(text(label));}catch(NumberFormatException e){System.out.println("Enter a valid integer.");}}
    private int nonNegative(String label){int n=integer(label);if(n<0)throw new IllegalArgumentException("Value cannot be negative.");return n;}
    private double decimal(String label){while(true)try{return Double.parseDouble(text(label));}catch(NumberFormatException e){System.out.println("Enter a valid number.");}}
    private String shortId(){return UUID.randomUUID().toString().substring(0,8).toUpperCase();}
}
