package com.ecoaccess.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ecoaccess.dao.CouponDAO;
import com.ecoaccess.dao.PassengerDAO;
import com.ecoaccess.dao.RewardTransactionDAO;
import com.ecoaccess.model.Coupon;
import com.ecoaccess.model.Passenger;
import com.ecoaccess.model.RewardTransaction;

public class RewardService {

    private static final int MIN_REDEEM_POINTS = 100;
    private static final double VALUE_PER_POINT = 0.50;
    private static final int WASTE_REWARD_POINTS = 20;

    private final PassengerDAO passengerDAO;
    private final RewardTransactionDAO transactionDAO;
    private final CouponDAO couponDAO;

    public RewardService() {

        this.passengerDAO = new PassengerDAO();
        this.transactionDAO = new RewardTransactionDAO();
        this.couponDAO = new CouponDAO();
    }

    // ==========================================
    // EARN POINTS
    // ==========================================

    public int earnWasteReward(
            String passengerId,
            String wasteSubmissionId) {

        return addPoints(
                passengerId,
                WASTE_REWARD_POINTS,
                "WASTE",
                wasteSubmissionId,
                "Waste disposal reward"
        );
    }

    public int addPoints(
            String passengerId,
            int points,
            String referenceType,
            String referenceId,
            String description) {

        if (points <= 0) {
            throw new IllegalArgumentException(
                    "Points must be greater than zero.");
        }

        Passenger passenger =
                passengerDAO.findById(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger not found.");
        }

        int newPoints =
                passenger.getRewardPoints() + points;

        boolean updated =
                passengerDAO.updateRewardPoints(
                        passengerId,
                        newPoints);

        if (!updated) {
            throw new IllegalStateException(
                    "Failed to update reward points.");
        }

        String transactionId =
                "RT-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        RewardTransaction transaction =
                new RewardTransaction(
                        transactionId,
                        passengerId,
                        "EARN",
                        points,
                        referenceType,
                        referenceId,
                        description,
                        LocalDateTime.now()
                );

        transactionDAO.save(transaction);

        return newPoints;
    }

    // ==========================================
    // REDEEM POINTS
    // ==========================================

    public Coupon redeemPoints(
            String passengerId) {

        Passenger passenger =
                passengerDAO.findById(passengerId);

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger not found.");
        }

        int points =
                passenger.getRewardPoints();

        if (points < MIN_REDEEM_POINTS) {
            throw new IllegalStateException(
                    "Minimum "
                    + MIN_REDEEM_POINTS
                    + " points required.");
        }

        double couponValue =
                points * VALUE_PER_POINT;

        String couponId =
                "CP-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        String code =
                "ECO-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 6)
                   .toUpperCase();

        LocalDateTime now =
                LocalDateTime.now();

        LocalDateTime expiry =
                now.plusHours(24);

        Coupon coupon =
                new Coupon(
                        couponId,
                        code,
                        passengerId,
                        points,
                        couponValue,
                        couponValue,
                        "Active",
                        now,
                        expiry
                );

        boolean saved =
                couponDAO.save(coupon);

        if (!saved) {
            throw new IllegalStateException(
                    "Failed to create coupon.");
        }

        // Reset passenger points
        boolean updated =
                passengerDAO.updateRewardPoints(
                        passengerId,
                        0);

        if (!updated) {
            throw new IllegalStateException(
                    "Failed to update passenger points.");
        }

        // Record redemption
        String transactionId =
                "RT-" +
                UUID.randomUUID()
                   .toString()
                   .substring(0, 8)
                   .toUpperCase();

        RewardTransaction transaction =
                new RewardTransaction(
                        transactionId,
                        passengerId,
                        "REDEEM",
                        points,
                        "COUPON",
                        couponId,
                        "Points redeemed for coupon",
                        now
                );

        transactionDAO.save(transaction);

        return coupon;
    }

    // ==========================================
    // GET PASSENGER COUPONS
    // ==========================================

    public List<Coupon> getPassengerCoupons(
            String passengerId) {

        return couponDAO.findByPassenger(
                passengerId);
    }
}