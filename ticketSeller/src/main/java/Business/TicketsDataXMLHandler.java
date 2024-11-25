/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Business;

import Entity.Ticket;
import Internal.Config;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LENOVO
 */
public class TicketsDataXMLHandler extends XML{
    private TicketToElement _toe;

    public TicketsDataXMLHandler() {
        this._toe = new TicketToElement();
    }
    
    @Override
    public void readFile(String filename) {
        try {
            DocumentBuilder db = Config.dbf.newDocumentBuilder();
            Config.TicketData = db.parse(new File(filename));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Failed to read the XML file.", e);
        }
        
    }

    @Override
    public void addToFile(Object object) {
        if (!(object instanceof Ticket)) {
            return;
        }
        Ticket ticketObject = (Ticket) object;
        String ticketDate = ticketObject.getDateString();
        
        Document doc = Config.TicketData;
        Element schedule = findSchedule(ticketDate, doc);
        
        String eventId = ticketObject.eventId();
        Element event = findEvent(eventId, schedule, doc);
        Element ticket  = _toe.toXMLElement(doc, object);
        event.appendChild(ticket);
        saveChanges(doc, Config.pathToTicketData);
    }
    
    private Element findSchedule(String date, Document doc) {
        NodeList scheduleList = doc.getElementsByTagName("schedule");
        for (int i = 0; i < scheduleList.getLength(); i++) {
            Node schedule = scheduleList.item(i);
            if (schedule.getNodeType() == Node.ELEMENT_NODE) {
                Element scheduleElement = (Element) schedule;
                String scheduleDate =  scheduleElement.getAttribute("Date");
                if (date == null ? scheduleDate == null : date.equals(scheduleDate)) {
                    return scheduleElement;
                }
            }
        }
        Element newScheduleElement = doc.createElement("schedule");
        newScheduleElement.setAttribute("Date", date);
        doc.getDocumentElement().appendChild(newScheduleElement);
        
        return newScheduleElement;
    }
    
    private Element findEvent(String eventId, Element schedule, Document doc) {
        NodeList eventList = schedule.getElementsByTagName("event");
        for (int i = 0; i < eventList.getLength(); i++) {
            Node eventNode = eventList.item(i);
            if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eventElement = (Element) eventNode;
                String toCompare = eventElement.getAttribute("Id");
                if (eventId == null ? toCompare == null : eventId.equals(toCompare)) {
                    return eventElement;
                }
            }
        }
        Element newEvent = doc.createElement("event");
        newEvent.setAttribute("Id", eventId);
        schedule.appendChild(newEvent);
        return newEvent;
    }

    @Override
    public void removeFromFile(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
