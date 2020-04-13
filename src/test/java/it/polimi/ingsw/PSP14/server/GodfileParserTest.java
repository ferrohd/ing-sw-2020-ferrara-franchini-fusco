package it.polimi.ingsw.PSP14.server;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import it.polimi.ingsw.PSP14.server.controller.GodfileParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

public class GodfileParserTest {
    @Test
    void shouldParseCorrectly() {
        assertDoesNotThrow(() -> {
            ArrayList<String> test = GodfileParser.getGodIdList("src/test/resources/godlist_valid_1.xml");
            ArrayList<String> expected = new ArrayList<String>();
            expected.add("Apollo");
            assertEquals(expected, test);
        });
    }

    @Test
    void shouldFailParse() {
        assertThrows(FileNotFoundException.class, () -> {
            GodfileParser.getGodIdList("src/test/resources/godlist_invalid_2_electric_boogaloo.xml");
        });
    }

    @Test
    void shouldFailParse2() {
        assertThrows(SAXException.class, () -> {
            GodfileParser.getGodIdList("src/test/resources/godlist_invalid_1.xml");
        });
    }
}
