package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.client.controller.SettingsParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsParserTest {

    @Test
    void getShouldImportOnlyExistingParameters() {
        SettingsParser settings;
        try {
            settings = new SettingsParser("src/main/resources/settings.txt");
        } catch(Exception e) {
            fail();
            return;
        }
        
        assertEquals("127.0.0.1", settings.get("hostname"));
        assertNotEquals("127.0.0.1", settings.get("hostnamewrongone"));
        assertNull(settings.get("wrongone"));
    }
}