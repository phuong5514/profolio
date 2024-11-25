/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main.ticketbuyer;

import Business.ViewModel;
import UI.Gui;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class TicketBuyer {
    
    public static void main(String[] args) {
        ViewModel vm = new ViewModel();
        try {
            vm.connect();
        } catch (IOException ex) {
            Logger.getLogger(TicketBuyer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Gui.createAndShowGUI(vm);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(TicketBuyer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
