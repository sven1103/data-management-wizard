package IO;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DOMParser {

    private String species=null;

    public String parse(String filepathXML) {

        try {
            // read xml file
            File inputFile = new File(filepathXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // get all elements which are tagged with "Item"
            NodeList nList = doc.getElementsByTagName("Item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                // find element with "Name="ScientificName"" to parse the
                // name of the species
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("Name").equals("ScientificName")) {
                        this.species = eElement.getTextContent();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.species;
    }

}