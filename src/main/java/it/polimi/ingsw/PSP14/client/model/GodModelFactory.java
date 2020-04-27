package it.polimi.ingsw.PSP14.client.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns GodModel instances found in the godsFile.
 */
public class GodModelFactory {
    private Map<String, GodModel> godMap;

    /**
     * Constructs a GodModelFactory using a specific file.
     * @param godsFile file containing gods data
     * @throws IOException for errors with opening, reading, or parsing the file
     */
    public GodModelFactory(String godsFile) throws IOException {
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
                    new GodModelImplementation(godName, godAlias, godAbility, godDescription)
            );
        }
    }

    /**
     * Returns a GodModel instance by god name.
     * @param godName the name of the god
     * @return the GodModel instance
     */
    public GodModel getGodModel(String godName) {
        return godMap.get(godName);
    }
}

/**
 * Implementation of the GodModel, visible only to GodModelFactory.
 */
class GodModelImplementation implements GodModel {
    private final String name, alias, ability, description;

    public GodModelImplementation(String name, String alias, String ability, String description) {
        this.name = name;
        this.alias = alias;
        this.ability = ability;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getAbility() {
        return ability;
    }

    public String getDescription() {
        return description;
    }
}
