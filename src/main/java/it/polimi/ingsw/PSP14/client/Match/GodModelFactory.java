package it.polimi.ingsw.PSP14.client.Match;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public GodModelFactory(String godsFile) throws ParserConfigurationException, IOException, SAXException {
        godMap = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(godsFile));

        Node root = doc.getFirstChild();

        for(int i = 0; i < root.getChildNodes().getLength(); ++i) {
            Node node = root.getChildNodes().item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
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
