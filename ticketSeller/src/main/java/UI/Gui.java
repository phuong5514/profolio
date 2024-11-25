/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.ViewModel;
import Internal.Config;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;



public class Gui {
    private static ViewModel viewModel;
    private static JFrame frame;    
    private static JPanel mainContainer;
    private static HomeTab home;
    private static ScheduleListTab scheduleTab;
    private static EditLocationTab locationTab;
    
    private static void createAndShowGUI(ViewModel vm) throws ParserConfigurationException, SAXException, IOException 
    {
        viewModel = vm;     
        mainContainer = new JPanel();
        
        home = new HomeTab();
        home.setViewModel(vm);
        home.setCloseConnection();
        
        scheduleTab = new ScheduleListTab(Config.dayLimit, mainContainer);
        locationTab = new EditLocationTab(viewModel);
        
        scheduleTab.setPreviousTab(home, mainContainer);
        scheduleTab.initSchedule(viewModel);
        scheduleTab.setReload(vm);
        
        locationTab.setPreviousTab(home, mainContainer);
        locationTab.loadLocationList(viewModel);

        home.setLink(scheduleTab, "Lịch chiếu", mainContainer);
        home.setLink(locationTab, "Phòng chiếu", mainContainer);
        
        
        frame = new JFrame("Movie ticket administator");
        frame.setPreferredSize(new Dimension(1400, 600));
//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
       
        mainContainer.add(home.getContainer());
        frame.add(mainContainer);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
    public static void runProgram(ViewModel vm) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI(vm);
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
    
    