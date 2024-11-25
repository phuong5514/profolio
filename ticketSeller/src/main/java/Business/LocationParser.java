/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Entity.Event;
import Entity.Location;
import Entity.Schedule;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LocationParser extends ObjectParser{

    @Override
    public Object parse(Document doc) {
        ArrayList<Location> result = new ArrayList<>();
        NodeList locationList = doc.getElementsByTagName("location");

        for (int i = 0; i < locationList.getLength(); i++) {
            Node locationNode = locationList.item(i);

            if (locationNode.getNodeType() == Node.ELEMENT_NODE) {
                Location location = new Location();
                
                Element locationElement = (Element) locationNode;
                String id = locationElement.getAttribute("Id");
                
                String[] keys = {"name", "rows", "columns"};
                ArrayList<String> raws = new ArrayList<>();
                for (String key : keys) {
                    Element e = (Element) locationElement.getElementsByTagName(key).item(0);
                    raws.add(e.getTextContent().trim());
                }
                
                location.setLocationId(id);
                location.setLocationName(raws.get(0));
                location.setRowCount(Integer.valueOf(raws.get(1)));
                location.setSeatsPerRow(Integer.valueOf(raws.get(2)));
                result.add(location);
            }
        }
                
            
        return result;
    }
    
}
