/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.ViewModel;
import Entity.TCPClient;
import Internal.Config;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private static SignInTab signIn;;
//    
    public static void createAndShowGUI(ViewModel vm) throws ParserConfigurationException, SAXException, IOException 
    {
        viewModel = vm;
        viewModel.sendRequest();
        //viewModel.schedules();
        
        System.out.print(viewModel.schedules());
//        System.out.print(viewModel.locations());
        
        mainContainer = new JPanel();
        
        home = new HomeTab();
        home.setViewModel(viewModel);
        
        scheduleTab = new ScheduleListTab(Config.dayLimit, mainContainer);
        scheduleTab.setPreviousTab(home, mainContainer);
        scheduleTab.setViewModel(viewModel);
        scheduleTab.setRefreshButton();
        scheduleTab.initSchedule(viewModel);
        
        home.setLink(scheduleTab, "Lịch chiếu", mainContainer);
        
        signIn = new SignInTab();
        signIn.setViewModel(viewModel);
        signIn.setSignIn(home, mainContainer);
        
        home.setLink(signIn, "Thông tin khách hàng", mainContainer);
        
        
        
        frame = new JFrame("Movie ticket store client");
        frame.setPreferredSize(new Dimension(1600, 600));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        mainContainer.add(signIn.getContainer());
        frame.add(mainContainer);
        frame.pack();
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    vm.closeConnection();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.setVisible(false);
                frame.dispose();
            }
          });
        
        
    }
    

    
}
    
    