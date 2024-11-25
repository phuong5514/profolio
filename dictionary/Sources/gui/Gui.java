package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;



public class Gui {
    private static JFrame frame;
    private static Menu mainMenu;
    private static Body appBody;
    
    
    private static void createAndShowGUI() throws ParserConfigurationException, SAXException, IOException 
    {
        frame = new JFrame("Dictionary");
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().setSize(800,400);
        
        appBody = new Body();
        appBody.loadDictionary("Resources\\Anh_Viet.xml", "Resources\\Viet_Anh.xml");
        
        mainMenu = new Menu(appBody);
        

        frame.setJMenuBar(mainMenu.getMenu());
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(appBody.getBody(), BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
    public static void runProgram() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
    
    


