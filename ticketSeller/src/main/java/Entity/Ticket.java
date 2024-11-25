
package Entity;

import Internal.Config;
import java.io.Serializable;
import java.util.ArrayList;


public class Ticket implements Serializable{

    public void setCustomerName(String _customerName) {
        this._customerName = _customerName;
    }

    public void setCustomerPhone(String _customerPhone) {
        this._customerPhone = _customerPhone;
    }

    public void setEventId(String _eventId) {
        this._eventId = _eventId;
    }

    public void setSession(String _session) {
        this._session = _session;
    }

    public void setLocationId(String _locationId) {
        this._locationId = _locationId;
    }

    public void setTicketPrice(float _ticketPrice) {
        this._ticketPrice = _ticketPrice;
    }

    public void setSeats(ArrayList<Seat> _seats) {
        this._seats = _seats;
    }
    
    public void addSeat(int row, int col) {
        this._seats.add(new Seat(row, col));
    }

    public String getDateString() {
        return _dateString;
    }

    public void setDateString(String _dateString) {
        this._dateString = _dateString;
    }
    
    
    private String _dateString;
    private String _customerName;
    private String _customerPhone;
    private String _eventId;
    private String _session;
    private String _locationId;
    private float _ticketPrice;
    
    public static class Seat implements Serializable{
        private int row;
        private int column;

        public Seat(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int row() {
            return row;
        }

        public int column() {
            return column;
        }
    }
    
    private ArrayList<Seat> _seats;


    public Ticket() {
        _customerName = "";
        _customerPhone = "";
        _eventId = "";
        _dateString = "";
        _session = "";
        _locationId = "";
        _ticketPrice = 0;
        _seats = new ArrayList<>();
    }

    public Ticket(String customerName, String customerPhone, String dateString ,String eventId, String session, String locationId, float ticketPrice, ArrayList<Seat> seats) {
        _customerName = customerName;
        _customerPhone = customerPhone;
        _dateString = dateString;
        _eventId = eventId;
        _session = session;
        _locationId = locationId;
        _ticketPrice = ticketPrice;
        _seats = seats;
    }
    
    public Ticket(String customerName, String customerPhone, String dateString ,String eventId, String session, String locationId, float ticketPrice) {
        _customerName = customerName;
        _customerPhone = customerPhone;
        _dateString = dateString;
        _eventId = eventId;
        _session = session;
        _locationId = locationId;
        _ticketPrice = ticketPrice;
        _seats = new ArrayList<>();
    }
    
    public String customerName() {
        return _customerName;
    }

    public String customerPhone() {
        return _customerPhone;
    }

    public String eventId() {
        return _eventId;
    }
    
    public String session() {
        return _session;
    }

    public String locationId() {
        return _locationId;
    }

    public float ticketPrice() {
        return _ticketPrice;
    }

    public ArrayList<Seat> seats() {
        return _seats;
    }

    public float price() {
        return _ticketPrice;
    }
    
        public String toMessage() {
        // format: <prefix>_<name>_<phone>_<date>_<eventID>_<locationId>_<session>_<price>_row1-col1,row2-col2,...
        StringBuilder message = new StringBuilder(Config.messageKeywordSeparator);
        message.append(this._customerName).append(Config.messageKeywordSeparator)
           .append(this._customerPhone).append(Config.messageKeywordSeparator)
           .append(this._dateString).append(Config.messageKeywordSeparator)
           .append(this._eventId).append(Config.messageKeywordSeparator)
           .append(this._locationId).append(Config.messageKeywordSeparator)
           .append(this._session).append(Config.messageKeywordSeparator)
           .append(this._ticketPrice).append(Config.messageKeywordSeparator);
        
        for (Seat seat : this._seats) { 
            message.append(seat.row);
            message.append(Config.messageMultipartsSeparator);
            message.append(seat.column);
            message.append(Config.messageListItemSeparator);
        }
        
        
        
        //remove the last character
        if (!this._seats.isEmpty()) {
            message.setLength(message.length() - Config.messageListItemSeparator.length());
        }
    
        return message.toString();
    }

}
