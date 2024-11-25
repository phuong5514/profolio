/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Entity.Ticket;
import Entity.Ticket.Seat;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TicketToElement  extends ObjectToElement {
    
    @Override
    public Element toXMLElement(Document doc, Object object) {
        if (!(object instanceof Ticket)) {
            throw new IllegalArgumentException("Object must be of type Ticket");
        }
        
        // cast the object        
        Ticket ticket = (Ticket) object;

        Element ticketElement = doc.createElement("ticket");
        
        Element customerElement = doc.createElement("customer");
        
        Element customerName = doc.createElement("name");
        customerName.appendChild(doc.createTextNode(ticket.customerName()));
        customerElement.appendChild(customerName);

        Element customerPhone = doc.createElement("phone");
        customerPhone.appendChild(doc.createTextNode(ticket.customerPhone()));
        customerElement.appendChild(customerPhone);

        ticketElement.appendChild(customerElement);

        Element session = doc.createElement("session");
        session.appendChild(doc.createTextNode(ticket.session()));
        ticketElement.appendChild(session);

        Element locationId = doc.createElement("locationId");
        locationId.appendChild(doc.createTextNode(ticket.locationId()));
        ticketElement.appendChild(locationId);

        Element ticketPrice = doc.createElement("price");
        ticketPrice.appendChild(doc.createTextNode(String.valueOf(ticket.ticketPrice())));
        ticketElement.appendChild(ticketPrice);

        Element seatsElement = doc.createElement("seats");
        ArrayList<Seat> seats = ticket.seats();
        
        for (Ticket.Seat seat : seats) {
            Element seatElement = doc.createElement("seat");
            seatElement.setAttribute("row", String.valueOf(seat.row()));
            seatElement.setAttribute("column", String.valueOf(seat.column()));
            seatsElement.appendChild(seatElement);
        }
        ticketElement.appendChild(seatsElement);

        return ticketElement;
    
    }
    
}
