/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Business;

import Entity.Location;
import Internal.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author LENOVO
 */
public class LocationToElement extends ObjectToElement{

    @Override
    public Element toXMLElement(Document doc, Object object) {
        if (!(object instanceof Location)) {
            throw new IllegalArgumentException("Object must be of type Location");
        }
        
        // cast the object        
        Location location = (Location) object;

        // return variable
        Element locationElement = doc.createElement("location");
        locationElement.setAttribute("Id", location.getLocationId());

        // add name to locationElement
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(location.getLocationName()));
        locationElement.appendChild(name);

        // add rows to locationElement
        Element rows = doc.createElement("rows");
        rows.appendChild(doc.createTextNode(String.valueOf(location.getRowCount())));
        locationElement.appendChild(rows);

        // add columns to locationElement
        Element columns = doc.createElement("columns");
        columns.appendChild(doc.createTextNode(String.valueOf(location.getSeatsPerRow())));
        locationElement.appendChild(columns);

        return locationElement;
    }
}
