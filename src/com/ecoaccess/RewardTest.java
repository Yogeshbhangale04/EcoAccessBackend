package com.ecoaccess;

import com.ecoaccess.model.Coupon;
import com.ecoaccess.service.RewardService;

public class RewardTest {

    public static void main(String[] args) {

        RewardService rewardService =
                new RewardService();

        System.out.println("================================");
        System.out.println("     REWARD SYSTEM TEST");
        System.out.println("================================");

        // ------------------------------------------
        // 1. Earn Waste Reward
        // ------------------------------------------

        System.out.println("\n1. EARNING WASTE REWARD");

        int newPoints =
                rewardService.earnWasteReward(
                        "P1002",
                        "WS-TEST-001");

        System.out.println(
                "New Reward Points: " + newPoints
        );

        // ------------------------------------------
        // 2. Redeem Points
        // ------------------------------------------

        System.out.println("\n2. REDEEMING REWARD POINTS");

        Coupon coupon =
                rewardService.redeemPoints("P1002");

        System.out.println(
                "Coupon ID: " + coupon.getId()
        );

        System.out.println(
                "Coupon Code: " + coupon.getCode()
        );

        System.out.println(
                "Points Used: " + coupon.getPointsUsed()
        );

        System.out.println(
                "Coupon Value: ₹" + coupon.getCouponValue()
        );

        System.out.println(
                "Remaining Balance: ₹"
                + coupon.getRemainingValue()
        );

        System.out.println(
                "Status: " + coupon.getStatus()
        );

        System.out.println(
                "Expires At: " + coupon.getExpiresAt()
        );

        // ------------------------------------------
        // 3. Display Passenger Coupons
        // ------------------------------------------

        System.out.println("\n3. PASSENGER COUPONS");

        rewardService
                .getPassengerCoupons("P1002")
                .forEach(c -> {

                    System.out.println(
                            c.getCode()
                            + " | ₹"
                            + c.getCouponValue()
                            + " | "
                            + c.getStatus()
                    );
                });

        System.out.println("\n================================");
        System.out.println("       TEST COMPLETED");
        System.out.println("================================");
    }
}