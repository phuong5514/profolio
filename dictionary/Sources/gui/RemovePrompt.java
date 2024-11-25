/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package gui;

import bll.RequestHandler;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author LENOVO
 */
public class RemovePrompt extends PromptWindow{    
    private static JTextField wordField; 
    
    private static JButton removeButton;
    
    @Override
    public String getWord() {
        String res = wordField.getText();
        wordField.setText(null);
        return res;
    
    }
    
    public RemovePrompt(Body app) throws ParserConfigurationException, IOException, SAXException {
        this.rh = new RequestHandler(this, app);
        frame = new JFrame("Remove a word");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        mainBody = new JPanel();
        mainBody.setLayout(new BoxLayout(mainBody, BoxLayout.Y_AXIS));
        
        JPanel fieldContainer = new JPanel();
       
        wordField = new JTextField();
        wordField.setEditable(true);
        wordField.setPreferredSize(new Dimension(200, 40));
        
        JLabel wordLabel = new JLabel("word to remove");
        wordLabel.setPreferredSize(new Dimension(100, 40));
        
        fieldContainer.add(wordLabel);
        fieldContainer.add(wordField);
        
        removeButton = new JButton("remove");
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(rh);
        
        frame.add(mainBody);
        mainBody.add(fieldContainer);
        mainBody.add(removeButton);
        
        frame.pack();
    }
    
    
    public void createSubWindow() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    @Override
    public String getMeanings() {
        return null;
    }
}