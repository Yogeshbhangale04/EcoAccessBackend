package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Coupon;
import com.ecoaccess.service.RewardService;

public class RewardServiceTest {

    private final RewardService rewardService =
            new RewardService();

    @Test
    void addPointsSuccessfully() {

        int newPoints =
                rewardService.addPoints(
                        "P1001",
                        10,
                        "TEST",
                        "TEST-001",
                        "JUnit reward test"
                );

        assertEquals(350, newPoints);
    }

    @Test
    void earnWasteRewardSuccessfully() {

        int newPoints =
                rewardService.earnWasteReward(
                        "P1001",
                        "WS-JUNIT-001"
                );

        assertEquals(370, newPoints);
    }

    @Test
    void addPointsFailsForInvalidPoints() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> rewardService.addPoints(
                                "P1001",
                                0,
                                "TEST",
                                "TEST-002",
                                "Invalid points test"
                        )
                );

        assertEquals(
                "Points must be greater than zero.",
                exception.getMessage()
        );
    }

    @Test
    void addPointsFailsForUnknownPassenger() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> rewardService.addPoints(
                                "P9999",
                                10,
                                "TEST",
                                "TEST-003",
                                "Unknown passenger test"
                        )
                );

        assertEquals(
                "Passenger not found.",
                exception.getMessage()
        );
    }

    @Test
    void redeemPointsSuccessfully() {

        Coupon coupon =
                rewardService.redeemPoints(
                        "P1001"
                );

        assertNotNull(coupon);

        assertNotNull(coupon.getId());
        assertNotNull(coupon.getCode());

        assertEquals(
                "P1001",
                coupon.getPassengerId()
        );

        assertEquals(
                "Active",
                coupon.getStatus()
        );

        assertEquals(
                370,
                coupon.getPointsUsed()
        );

        assertEquals(
                185.0,
                coupon.getCouponValue(),
                0.01
        );

        assertEquals(
                185.0,
                coupon.getRemainingValue(),
                0.01
        );

        assertNotNull(coupon.getCreatedAt());
        assertNotNull(coupon.getExpiresAt());
    }
}