package it.polimi.ingsw.PSP14.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsParserTest {

    @Test
    void get() {
        SettingsParser settings;
        try {
            settings = new SettingsParser("src/main/resources/settings.set");
        } catch(Exception e) {
            fail();
            return;
        }
        settings.get("random");
    }
}