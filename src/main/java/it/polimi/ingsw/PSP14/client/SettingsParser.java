package it.polimi.ingsw.PSP14.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for reading the settings file and getting the single settings.
 *
 * The settings file is composed of lines. Each line represent a single setting.
 * Each setting is composed of two substrings, separated by the ':' character.
 * The left hand side represents the name of the setting. The right hand side represents the setting value.
 */
public class SettingsParser {
    private Map<String, String> settingsMap;

    /**
     * @param filename the path of the setting file
     * @throws IOException if errors happen while reading the file
     */
    public SettingsParser(String filename) throws IOException {
        settingsMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        while(line != null) {
            line = line.replace(" ", "");
            String[] tokens = line.split(":");
            settingsMap.put(tokens[0], tokens[1]);
        }
    }

    /**
     * @param key the name of the option
     * @return the value of the option as a string
     */
    public String getSetting(String key) {
        return settingsMap.get(key);
    }
}
