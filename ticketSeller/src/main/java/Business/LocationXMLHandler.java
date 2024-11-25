/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Business;

import Entity.Location;
import Internal.Config;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
        
public class LocationXMLHandler extends XML {
    private LocationToElement _ltoe;
    
    public LocationXMLHandler() {
        _ltoe = new LocationToElement();
    }

    @Override
    public void readFile(String filename) {
        try {
            DocumentBuilder db = Config.dbf.newDocumentBuilder();
            Config.Locations = db.parse(new File(filename));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Failed to read the XML file.", e);
        }
    }

    @Override
    public void addToFile(Object object) {
        if (!(object instanceof Location)) {
            throw new IllegalArgumentException("Object must be of type Location");
        }
        
        Document doc = Config.Locations;
        Location location = (Location) object;
        
        Element newLocation = _ltoe.toXMLElement(doc, location);
        
        doc.getDocumentElement().appendChild(newLocation);
        
        saveChanges(doc, Config.pathToLocations);
    }

    @Override
    public void removeFromFile(Object object) {
        if (!(object instanceof Location)) {
            throw new IllegalArgumentException("Object must be of type Location");
        }
        Location removeTarget = (Location) object;
        
        Document doc = Config.Locations;
        NodeList locations = doc.getElementsByTagName("location");
        for (int i = 0; i < locations.getLength(); i++) {
            Element location = (Element) locations.item(i);
            if (location.getAttribute("Id").equals(removeTarget.getLocationId())) {
                location.getParentNode().removeChild(location);
                break;
            }
        }
        saveChanges(doc,  Config.pathToLocations);
    }

}
