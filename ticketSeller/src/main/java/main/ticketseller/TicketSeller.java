/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main.ticketseller;

import Business.ViewModel;
import Entity.TCPServer;
import Internal.Config;
import UI.Gui;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketSeller {
    public static void main(String[] args) {   
        try {
            ViewModel vm = new ViewModel();
            TCPServer server = new TCPServer(Config.maxConnection, Config.port, vm);
            
            Thread serverThread = new Thread(() -> {
                server.start();
            });
           
            
            serverThread.start();
            
            Gui.runProgram(vm);
         
        } catch (IOException ex) {
            Logger.getLogger(TicketSeller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
