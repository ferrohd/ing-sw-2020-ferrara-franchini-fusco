package it.polimi.ingsw.PSP14.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EndGameExceptionTest {

    @Test
    public void exceptionShouldInstantiateAndThrowCorrectly() {
        assertThrows(EndGameException.class, () -> {
            throw new EndGameException();
        });
    }
}