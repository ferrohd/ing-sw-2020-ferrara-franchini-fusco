package it.polimi.ingsw.PSP14.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidInputExceptionTest {

    @Test
    void getDescription() {
        assertEquals("Hello", new InvalidInputException("Hello").getDescription());
    }
}