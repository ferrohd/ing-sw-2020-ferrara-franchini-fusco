package it.polimi.ingsw.PSP14.server.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GodfileParser {
    private static ArrayList<String> godIdList = new ArrayList<>();
    /**
     * Obtain the list of all available gods, by parsing the associated XML file.
     * The function use a memoizer to reduce the I/O.
     * https://en.wikipedia.org/wiki/Memoization
     * @param godsFile The path of the XML file where gods are declared.
     * @return An ArrayList containing the names of the available gods to play.
     * @throws IOException If there's an error reading the godlist file
     */
    public static ArrayList<String> getGodIdList(InputStream godsFile, int gamesize) throws IOException {
        // If godIdList isn't set OR the file to parse has changed
        if ( godIdList.isEmpty() ) {
            // Initialise the list
            godIdList = new ArrayList<>();
            // Parse the XML and generate a new godIdList.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc;
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse(godsFile);
            } catch (ParserConfigurationException | SAXException e) {
                throw new IOException("Error parsing god file");
            }

            Element root = (Element) doc.getFirstChild();
            NodeList gods = root.getElementsByTagName("god");

            for (int i = 0; i < gods.getLength(); ++i) {
                Element element = (Element) gods.item(i);
                String godName = element.getElementsByTagName("name").item(0).getTextContent();
                int max = Integer.parseInt(element.getElementsByTagName("max").item(0).getTextContent());
                if(max >= gamesize)
                    godIdList.add(godName);
            }
        }
        return godIdList;
    }
}
