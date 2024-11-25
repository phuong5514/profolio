package Business;

import Entity.Schedule;
import Entity.Ticket;
import Helpers.Tuple;
import Internal.Config;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket _clientSocket;
    private ViewModel _vm;
    private BufferedReader _requestReader;
    private BufferedWriter _requestResponder;
    private ObjectOutputStream _requestObjectResponder;


    public ClientHandler(Socket clientSocket, ViewModel vm) throws IOException {
        this._clientSocket = clientSocket;
        this._vm = vm;

        InputStream is = this._clientSocket.getInputStream();
        _requestReader = new BufferedReader(new InputStreamReader(is));

        OutputStream os = this._clientSocket.getOutputStream();
        _requestResponder = new BufferedWriter(new OutputStreamWriter(os));
        _requestObjectResponder = new ObjectOutputStream(os);
    }

    @Override
    public void run() {
        try {
            while (!_vm.shouldStop()) {
                // Listen for requests from the client
                String request = listenForRequest();
                if (request.equalsIgnoreCase(Config.closeConnectionRequest) || request == null) {
                    System.out.println("Close connection request detected");
                    break;
                }

                // Handle the request and respond accordingly
                handleRequest(request);

            }

            _clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String listenForRequest() throws IOException {
        return _requestReader.readLine();
    }

    private void handleRequest(String request) throws IOException {
        System.out.println(request);
        
        if (request.startsWith(Config.locationsRequest)) {
            handleGetLocations();
        } else if (request.startsWith(Config.eventsRequest)) {
            handleGetEvents();
        } else if (request.startsWith(Config.seatDetailsRequest)) {
            handleGetSeatDetails(request);
        } else if (request.startsWith(Config.seatSizeRequest)) {
            handleGetSeatSize(request);
        } else if (request.startsWith(Config.buyTicketRequest)) {
            handleBookTicket(request);
        } else {
            respondToClient("Invalid request");
        }
    }

    private void handleGetLocations() throws IOException {
        ArrayList<String> locationIds = _vm.locationIds();
        String response = String.join(",", locationIds);
        respondToClient(response);
    }

    private void handleGetEvents() throws IOException {
        ArrayList<Schedule> response = _vm.schedulesForNextXDays();
        respondObjectToClient(response);
    }

    private void handleGetSeatDetails(String request) throws IOException {
        
        // request format: Config.get_seat_details (can change) + date + eventCode + session + locationId
        // eg: get_seat_details_08/06/2024_A_12:30-14:00_T1
        
        String params = request.substring(Config.seatDetailsRequest.length() + 1);
        String[] parts = params.split(Config.messageKeywordSeparator);
        
        if (parts.length != 4) {
            respondToClient("Invalid format!");
            return;
        }
        
        String date = parts[0];
        String eventId = parts[1];
        String sessionTime = parts[2];
        String locationId = parts[3];
        
        for (String part : parts) {
            System.out.println(part);
        }
        
        ArrayList<Ticket> response = _vm.getEventSeatData(eventId, date, sessionTime, locationId);
        System.out.println("ticket");
        System.out.println(response.size());
        for (Ticket ticket : response) {
            System.out.println(ticket.price());
            for (Ticket.Seat seat : ticket.seats()) {
                System.out.println("row");
                System.out.println(seat.row());
                
                System.out.println("col");
                System.out.println(seat.column());
            }
        }
        
        respondObjectToClient(response);
    }
    
    private void handleGetSeatSize(String request) throws IOException {
        String locationId = request.substring(Config.seatSizeRequest.length() + 1);
        System.out.println(locationId);
        ArrayList<Integer> response = _vm.getLocationSeatSize(locationId);
        System.out.println(response.size());
        respondObjectToClient(response);
    }

    
    
    private void handleBookTicket(String request) throws IOException {
//        _customerName = "";
//        _customerPhone = "";
//        _eventId = "";
//        _dateString = "";
//        _session = "";
//        _locationId = "";
//        _ticketPrice = 0;
//        _seats = new ArrayList<>();
        // format: <prefix>_<name>_<phone>_<date>_<eventID>_<locationId>_<session>_<price>_row1-col1,row2-col2,...
        String params = request.substring(Config.buyTicketRequest.length() + 1);
        String parts[] = params.split(Config.messageKeywordSeparator);
        
        if (parts.length != 8) {
            respondToClient("Invalid format!");
            return;
        }
        String name = parts[0];
        String phone = parts[1];
        String date = parts[2];
        String eventId = parts[3];
        String locationId = parts[4];
        String session = parts[5];
        String price = parts[6];
        String seatList = parts[7];
        ArrayList<Tuple<String, String>> seats = new ArrayList<>();
        String[] tupleList = seatList.split(Config.messageListItemSeparator);
        for (String item : tupleList) {
            String[] tupleParts = item.split(Config.messageMultipartsSeparator);
            if (tupleParts.length != 2) {
                continue;
            }
            seats.add(new Tuple(tupleParts[0], tupleParts[1]));
        }
        
        if (!(seats.size() > 0)) {
            respondToClient("Invalid format!");
            return;
        }

        _vm.addTicket(name, phone, date, eventId, locationId, session, price, seats);
        respondToClient("add new Ticket success!");
    }

    private void respondObjectToClient(Object response) throws IOException {
        _requestObjectResponder.writeObject(response);
        _requestObjectResponder.flush();
    }
    
    private void respondToClient(String response) throws IOException {
        _requestResponder.write(response);
        _requestResponder.newLine();
        _requestResponder.flush();
    }





}
