package com.ecoaccess.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ecoaccess.model.Passenger;
import com.ecoaccess.service.PassengerService;

public class PassengerServiceTest {

    private final PassengerService passengerService =
            new PassengerService();

    @Test
    void loginWithValidCredentials() {

        Passenger passenger =
                passengerService.login(
                        "+919988776655",
                        "Test@123"
                );

        assertNotNull(passenger);

        assertEquals(
                "P1002",
                passenger.getId()
        );

        assertEquals(
                "Yogesh Bhangale",
                passenger.getName()
        );
    }

    @Test
    void loginWithInvalidPassword() {

        Passenger passenger =
                passengerService.login(
                        "+919988776655",
                        "WrongPassword"
                );

        assertNull(passenger);
    }

    @Test
    void loginWithUnknownMobile() {

        Passenger passenger =
                passengerService.login(
                        "+910000000000",
                        "Test@123"
                );

        assertNull(passenger);
    }
}