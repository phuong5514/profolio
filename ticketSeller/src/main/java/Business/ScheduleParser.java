/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Entity.Date;
import Entity.Event;
import Entity.Schedule;
import Entity.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ScheduleParser extends ObjectParser {

    @Override
    public Object parse(Document doc) {
        ArrayList<Schedule> result = new ArrayList<>();
        NodeList scheduleList = doc.getElementsByTagName("schedule");

        for (int i = 0; i < scheduleList.getLength(); i++) {
            Node scheduleNode = scheduleList.item(i);

            if (scheduleNode.getNodeType() == Node.ELEMENT_NODE) {
                Schedule schedule = new Schedule();
                
                Element scheduleElement = (Element) scheduleNode;
                String date = scheduleElement.getAttribute("Date");
                //date
                Date scheduleDate = new Date(date);
                schedule.setDay(scheduleDate);
                
                NodeList eventNodes = scheduleElement.getElementsByTagName("event");

                for (int j = 0; j < eventNodes.getLength(); j++) {
                    Node eventNode = eventNodes.item(j);

                    if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eventElement = (Element) eventNode;
                        //id, name
                        String id = eventElement.getAttribute("Id");
                        String name = eventElement.getElementsByTagName("name").item(0).getTextContent().trim();
                        
                        Event event = new Event(name, id);
                        
                        NodeList sessionNodes = eventElement.getElementsByTagName("session");
                        // sessions
                        for (int k = 0; k < sessionNodes.getLength(); k++) {
                            String session = sessionNodes.item(k).getTextContent().trim();
                            String sessionSeparator = "-";
                            String timeSeparator = ":";
                            String[] parts = session.split(sessionSeparator);
                            
                            ArrayList<Time> times = new ArrayList<>();
                            if (parts.length == 2) {
                                for (String part : parts) {
                                    String[] timeParts = part.split(timeSeparator);
                                    int h = Integer.parseInt(timeParts[0]);
                                    int m = Integer.parseInt(timeParts[1]);
                                    times.add(new Time(h,m));
                                }
                            }
                            
                            if (times.size() == 2) {
                                event.addSession(times.get(0), times.get(1));
                            }
                            
                        }

                        NodeList locationNodes = eventElement.getElementsByTagName("locationId");
                        for (int k = 0; k < locationNodes.getLength(); k++) {
                            Element locationElement = (Element) locationNodes.item(k);
                            String locationId = locationElement.getTextContent().trim();
                            String locationPrice = locationElement.getAttribute("price");
                            event.addLocation(locationId, Integer.parseInt(locationPrice));
                            
                        }

                        schedule.addEvent(event);
                    }
                }
                
                
                result.add(schedule);
            }
        }
                
            
        return result;
    }

}
