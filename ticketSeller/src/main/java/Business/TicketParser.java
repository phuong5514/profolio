/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Entity.Location;
import Entity.Schedule;
import Entity.Ticket;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class TicketParser extends ObjectParser{
    @Override
    public Object parse(Document doc) {
        HashMap<String, HashMap<String, ArrayList<Ticket>>> result = new HashMap<>();

        NodeList scheduleList = doc.getElementsByTagName("schedule");
        for (int i = 0; i < scheduleList.getLength(); i++) {
            Node scheduleNode = scheduleList.item(i);
            if (scheduleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element scheduleElement = (Element) scheduleNode;
                String date = scheduleElement.getAttribute("Date");

                HashMap<String, ArrayList<Ticket>> eventsMap = new HashMap<>();
                NodeList eventList = scheduleElement.getElementsByTagName("event");

                for (int j = 0; j < eventList.getLength(); j++) {
                    Node eventNode = eventList.item(j);
                    if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eventElement = (Element) eventNode;
                        String eventId = eventElement.getAttribute("Id");

                        ArrayList<Ticket> tickets = new ArrayList<>();
                        NodeList ticketList = eventElement.getElementsByTagName("ticket");

                        for (int k = 0; k < ticketList.getLength(); k++) {
                            Node ticketNode = ticketList.item(k);
                            if (ticketNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element ticketElement = (Element) ticketNode;
                               

                                String customerName = ticketElement.getElementsByTagName("name").item(0).getTextContent();
                                String customerPhone = ticketElement.getElementsByTagName("phone").item(0).getTextContent();
                                String session = ticketElement.getElementsByTagName("session").item(0).getTextContent();
                                String locationId = ticketElement.getElementsByTagName("locationId").item(0).getTextContent();
                                float price = Float.parseFloat(ticketElement.getElementsByTagName("price").item(0).getTextContent());

                                Ticket newTicket = new Ticket();
                                newTicket.setCustomerName(customerName);
                                newTicket.setCustomerPhone(customerPhone);
                                newTicket.setSession(session);
                                newTicket.setLocationId(locationId);
                                newTicket.setTicketPrice(price);

                                NodeList seatList = ticketElement.getElementsByTagName("seat");
                                for (int l = 0; l < seatList.getLength(); l++) {
                                    Node seatNode = seatList.item(l);
                                    if (seatNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element seatElement = (Element) seatNode;
                                        int row = Integer.parseInt(seatElement.getAttribute("row"));
                                        int column = Integer.parseInt(seatElement.getAttribute("column"));
                                        newTicket.addSeat(row, row);
                                    }
                                }

                                tickets.add(newTicket);
                            }
                        }

                        eventsMap.put(eventId, tickets);
                    }
                }

                result.put(date, eventsMap);
            }
        }

        return result;
    }
}
