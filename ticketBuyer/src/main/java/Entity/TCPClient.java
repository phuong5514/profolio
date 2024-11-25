/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Business.ClientHandler;
import Business.ViewModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author LENOVO
 */
public class TCPClient {
    private Socket _clientSocket;
    private ClientHandler _handler;

    public TCPClient(String host, int hostPortNumber) throws IOException {
        _clientSocket = new Socket(host, hostPortNumber);
    }

    public ClientHandler getClientHandler() throws IOException {
        _handler = new ClientHandler(_clientSocket);
        return _handler;
    }
    
    public void closeClient() throws IOException {
        _clientSocket.close();
    }

}