/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Business;

import Entity.Event;
import Entity.Location;
import Entity.Schedule;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ScheduleToElement extends ObjectToElement{
    @Override
    public Element toXMLElement(Document doc, Object object) {
        if (!(object instanceof Schedule)) {
            throw new IllegalArgumentException("Object must be of type Schedule");
        }
        
        // cast the object        
        Schedule schedule = (Schedule) object;

        // return variable
        Element scheduleElement = doc.createElement("schedule");
        scheduleElement.setAttribute("Date", schedule.getDay().toString());

        ArrayList<Event> eventList = schedule.getEventList();
        for (Event event : eventList) {
            // Create an event element
            Element eventElement = doc.createElement("event");
            eventElement.setAttribute("Id", event.eventId());

            // Create and append the name element
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(event.eventName()));
            eventElement.appendChild(nameElement);

            // Create and append the times element
            Element timesElement = doc.createElement("times");
            System.out.println(event.sessionCount());
            for (int i = 0; i < event.sessionCount(); i++) {
                String time = event.getSession(i);
                Element sessionElement = doc.createElement("session");
                sessionElement.appendChild(doc.createTextNode(time));
                timesElement.appendChild(sessionElement);
                
            }
            eventElement.appendChild(timesElement);

            // Create and append the locations element
            Element locationsElement = doc.createElement("locations");
            for (int i = 0; i < event.locationIds().size(); i++) {
                String location  = event.locationId(i);
                int price = event.priceOfLocation(i);
                
                Element locationElement = doc.createElement("locationId");
                locationElement.setAttribute("price", String.valueOf(price));
                locationElement.appendChild(doc.createTextNode(location));
                locationsElement.appendChild(locationElement);
                
            }
            eventElement.appendChild(locationsElement);

            // Append the event element to the schedule element
            scheduleElement.appendChild(eventElement);
        }
        return scheduleElement;       
    }
}
