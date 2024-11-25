package Business;

import Entity.Date;
import Entity.Event;
import Entity.Location;
import Entity.Schedule;
import Entity.TCPClient;
import Entity.Ticket;
import Entity.Time;
import Entity.User;
import Internal.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewModel{
    private static String configs;
    private static String HostIP;
    private TCPClient client;
    private ClientHandler clientHandler;

    private ArrayList<Schedule> storedSchedules;
//    private ArrayList<Location> locationDetails;
    
    private User user;
    
    
    public ViewModel() {
        storedSchedules = new ArrayList<>();
//        locationDetails = new ArrayList<>();
    }
    
    public void setClientHandler(ClientHandler ch) {
        clientHandler = ch;
    }
    
    public void connect() throws IOException {
        client = new TCPClient(Config.host, Config.port);
        clientHandler = client.getClientHandler();
    };
    
    public void signIn(User newUser) {
        user = newUser;
    }
    
    public String getUsername() {
        return user.name();
    }
    
    public String getPhoneNumber() {
        return user.phoneNumber();
    }
    
    public void sendRequest() {
        try {
            storedSchedules = (ArrayList<Schedule>) clientHandler.handleRequest(ClientHandler.RequestType.EVENTS_REQUEST, "");
//            String locationString = (String) clientHandler.handleRequest(ClientHandler.RequestType.LOCATIONS_REQUEST, "");
//            String[] locationIdStrings = locationString.split(Config.messageListItemSeparator);
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    public boolean sendBuyTicketRequest(Ticket generatedTicket) throws IOException, ClassNotFoundException {
        String parameters = generatedTicket.toMessage();
        boolean status = (boolean) clientHandler.handleRequest(ClientHandler.RequestType.BUY_TICKET_REQUEST, parameters); 
        return status;
    };
    
    public ArrayList<Ticket> sendLocationSeatStatesRequest(Date eventDate, String eventId, String session ,String locationId) throws IOException, ClassNotFoundException {
        StringBuilder builder = new StringBuilder();
        builder.append(eventDate.toString());
        builder.append(Config.messageKeywordSeparator);
        builder.append(eventId);
        builder.append(Config.messageKeywordSeparator);
        builder.append(session);
        builder.append(Config.messageKeywordSeparator);
        builder.append(locationId);
        String parameters = builder.toString();
        System.out.print(parameters);
        return (ArrayList<Ticket>) clientHandler.handleRequest(ClientHandler.RequestType.SEAT_DETAILS_REQUEST, parameters);
    } 
    
    public ArrayList<Integer> sendLocationSeatSizeRequest(String locationId) throws IOException, ClassNotFoundException {
        StringBuilder builder = new StringBuilder();
        builder.append(locationId);
        String parameters = builder.toString();
        System.out.println(parameters);

        return (ArrayList<Integer>) clientHandler.handleRequest(ClientHandler.RequestType.SEAT_SIZE_REQUEST, parameters);
    }
    
    public ArrayList<Schedule> schedules() {
        return storedSchedules;
    }
    
    
//    public ArrayList<Location> locations() {
//        return locationDetails;
//    }
    public void closeConnection() throws IOException, ClassNotFoundException {
        clientHandler.handleRequest(ClientHandler.RequestType.CLOSE_CONNECTION_REQUEST, "");
        client.closeClient();
    }
      
}
