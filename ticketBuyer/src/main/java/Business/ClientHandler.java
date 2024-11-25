/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Internal.Config;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler{
    private BufferedReader _reader;
    private ObjectInputStream _objectReader;
    private BufferedWriter _writer;
    private Socket _socket;




    
    public enum RequestType {
        LOCATIONS_REQUEST,
        EVENTS_REQUEST,
        SEAT_DETAILS_REQUEST,
        SEAT_SIZE_REQUEST,
        BUY_TICKET_REQUEST,
        CLOSE_CONNECTION_REQUEST
    }
    
    public ClientHandler(Socket clientSocket) throws IOException {
        _socket = clientSocket; 
        
        InputStream input = _socket.getInputStream();
        InputStreamReader inputReader = new InputStreamReader(input);
        _reader = new BufferedReader(inputReader);
        _objectReader = new ObjectInputStream(input);
        
        OutputStream output = _socket.getOutputStream();
        OutputStreamWriter outputWriter = new OutputStreamWriter(output);
        _writer = new BufferedWriter(outputWriter);
    

    }  
    
    
    public Object handleRequest(RequestType type, String parameters) throws IOException, ClassNotFoundException {
        switch (type) {
            case LOCATIONS_REQUEST:
                return handleGetLocations();
            case EVENTS_REQUEST:
                return handleGetEvents();
            case SEAT_DETAILS_REQUEST:
                return handleGetSeatDetails(parameters);
            case SEAT_SIZE_REQUEST:
                return handleGetSeatSize(parameters);
            case BUY_TICKET_REQUEST:
                return handleBuyTicket(parameters);
            case CLOSE_CONNECTION_REQUEST:
                handleCloseConnection();
                break;
            default:
                System.out.println("Unknown request");
                break;
        }
        
        return null;
    }

    private String handleGetLocations() throws IOException {
        String message = Config.locationsRequest;
        sendToServer(message);
        
        return listenForStringResponse();
    }

    private Object handleGetEvents() throws IOException, ClassNotFoundException {
        String message = Config.eventsRequest;
        sendToServer(message);
        
        return listenForObjectResponse();
    }

    private Object handleGetSeatDetails(String parameters) throws IOException, ClassNotFoundException {
        String message = Config.seatDetailsRequest + Config.messageKeywordSeparator + parameters;
        sendToServer(message);
        
        return listenForObjectResponse();
    }
    
    private Object handleGetSeatSize(String parameters) throws IOException, ClassNotFoundException {
        String message = Config.seatSizeRequest + Config.messageKeywordSeparator + parameters;
        sendToServer(message);
        
        return listenForObjectResponse();
    }

    private boolean handleBuyTicket(String parameters) throws IOException {
        String message = Config.buyTicketRequest + parameters;
        sendToServer(message);
        
        String respond = listenForStringResponse();
        if (respond.contains("success")) {
            return true;
        } else {
            return false;
        }
    }

    private void handleCloseConnection() throws IOException {
        sendToServer(Config.closeConnectionRequest);
        _socket.close();
    }
    
    private void sendToServer(String response) throws IOException {
        _writer.write(response);
        _writer.newLine();
        _writer.flush();
    }

    private String listenForStringResponse() throws IOException {
        return this._reader.readLine();
    }
    
    private Object listenForObjectResponse() throws IOException, ClassNotFoundException {
        return this._objectReader.readObject();
    }
    


}
