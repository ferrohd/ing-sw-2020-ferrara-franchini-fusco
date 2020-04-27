package it.polimi.ingsw.PSP14.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidActionExceptionTest {
    @Test
    void exceptionShouldInstantiate() {
        assertDoesNotThrow(() -> {
            Exception test = new InvalidActionException();
        });
    }
}