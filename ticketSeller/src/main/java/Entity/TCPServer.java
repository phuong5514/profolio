/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Business.ClientHandler;
import Business.ViewModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCPServer {
    private ServerSocket _serverSocket;
    private ExecutorService _executor;
    private ViewModel _vm;
    
    public TCPServer(int maxConnections, int portNumber, ViewModel vm) throws IOException {
        _serverSocket = new ServerSocket(portNumber);
        _executor = Executors.newFixedThreadPool(maxConnections);
        _vm = vm;
    }
    
    public void start() {
        System.out.println("Server started...");
        while (!_vm.shouldStop()) {
//            future?
            try {
                Socket clientSocket = _serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                _executor.execute(new ClientHandler(clientSocket, _vm));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        stop();
    }

    public void stop() {
        try {
            _serverSocket.close();
            _executor.shutdown();
            System.out.println("Server stopped...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
