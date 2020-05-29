package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.server.controller.GodfileParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GodfileParserTest {
    @Test
    void shouldParseCorrectly() {
        assertDoesNotThrow(() -> {
            ArrayList<String> test = GodfileParser.getGodIdList("src/test/resources/godlist_valid_1.xml", 2);
            ArrayList<String> expected = new ArrayList<String>();
            expected.add("Apollo");
            assertEquals(expected, test);
        });
    }

    @Test
    void shouldFailParse() {
        assertThrows(FileNotFoundException.class, () -> {
            GodfileParser.getGodIdList("src/test/resources/godlist_invalid_2_electric_boogaloo.xml", 2);
        });
    }

    @Test
    void shouldFailParse2() {
        assertThrows(SAXException.class, () -> {
            GodfileParser.getGodIdList("src/test/resources/godlist_invalid_1.xml", 2);
        });
    }
}
