package it.polimi.ingsw.PSP14.client.view.cli;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns GodModel instances found in the godsFile.
 */
public class GodFactory {
    private final Map<String, UIGod> godMap;
    private static GodFactory instance = null;

    public static GodFactory getInstance() throws IOException {
        if(instance == null) {
            instance = new GodFactory("src/main/resources/gods/godlist.xml");
        }

        return instance;
    }

    /**
     * Constructs a GodModelFactory using a specific file.
     * @param godsFile file containing gods data
     * @throws IOException for errors with opening, reading, or parsing the file
     */
    private GodFactory(String godsFile) throws IOException {
        godMap = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(godsFile));
        } catch (Exception e) {
            throw new IOException();
        }

        Element root = (Element) doc.getFirstChild();
        NodeList gods = root.getElementsByTagName("god");

        for(int i = 0; i < gods.getLength(); ++i) {
            Element element = (Element) gods.item(i);
            String godName = element.getElementsByTagName("name").item(0).getTextContent();
            String godAlias = element.getElementsByTagName("alias").item(0).getTextContent();
            String godAbility = element.getElementsByTagName("ability").item(0).getTextContent();
            String godDescription = element.getElementsByTagName("description").item(0).getTextContent();

            godMap.put(
                    godName,
                    new UIGod(godName, godAlias, godAbility, godDescription)
            );
        }
    }

    /**
     * Returns a GodModel instance by god name.
     * @param godName the name of the god
     * @return the GodModel instance
     */
    public UIGod getGod(String godName) {
        return godMap.get(godName);
    }
}
