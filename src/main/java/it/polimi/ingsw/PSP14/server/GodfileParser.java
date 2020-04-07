package it.polimi.ingsw.PSP14.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GodfileParser {
    private static ArrayList<String> godIdList = new ArrayList<>();
    private static String lastGodFile = "";
    /**
     * Obtain the list of all available gods, by parsing the associated XML file.
     * The function use a memoizer to reduce the I/O.
     * https://en.wikipedia.org/wiki/Memoization
     * @param godsFile The path of the XML file where gods are declared.
     * @return An ArrayList containing the names of the available gods to play.
     * @throws ParserConfigurationException If there's an error with the parser
     * @throws IOException If there's an error reading the godlist file
     * @throws SAXException If there's an error parsing the XML
     */
    public static ArrayList<String> getGodIdList(String godsFile) throws ParserConfigurationException, IOException, SAXException {
        // If godIdList isn't set OR the file to parse has changed
        if ( godIdList.isEmpty() || !godsFile.equals(lastGodFile) ) {
            // Initialise the list
            godIdList = new ArrayList<>();
            // Parse the XML and generate a new godIdList.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(godsFile));

            Element root = (Element) doc.getFirstChild();
            NodeList gods = root.getElementsByTagName("god");

            for (int i = 0; i < gods.getLength(); ++i) {
                Element element = (Element) gods.item(i);
                String godName = element.getElementsByTagName("name").item(0).getTextContent();

                godIdList.add(godName);
            }
        }

        lastGodFile = godsFile;
        return godIdList;
    }
}
