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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class AppendPrompt extends PromptWindow{
    private static JTextField wordField; 
    private static JTextArea definitionField;
    
    private static JButton addButton;
    
    @Override
    public String getWord() {
        String res = wordField.getText();
        wordField.setText(null);
        return res;
    }
    
    @Override
    public String getMeanings() {
        String res = definitionField.getText();
        wordField.setText(null);
        return res;
    }
    
    public AppendPrompt(Body app) throws ParserConfigurationException, IOException, SAXException {
        frame = new JFrame("Add a new word");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        mainBody = new JPanel();
        mainBody.setLayout(new BoxLayout(mainBody, BoxLayout.Y_AXIS));
        
        JPanel fieldContainer = new JPanel();
        JPanel fieldContainer2 = new JPanel();

        this.rh = new RequestHandler(this, app);
        
        wordField = new JTextField();
        wordField.setEditable(true);
        wordField.setPreferredSize(new Dimension(400, 60));
        
        definitionField = new JTextArea();
        definitionField.setEditable(true);
        definitionField.setPreferredSize(new Dimension(400, 380));
        
        JLabel wordLabel = new JLabel("word");
        wordLabel.setPreferredSize(new Dimension(100, 60));
        
        fieldContainer.add(wordLabel);
        fieldContainer.add(wordField);
        
        JLabel meaningLabel = new JLabel("meanings");
        meaningLabel.setPreferredSize(new Dimension(100, 380));
        fieldContainer2.add(meaningLabel);
        fieldContainer2.add(definitionField);
        
        addButton = new JButton("Add word");
        addButton.setActionCommand("add");
        addButton.addActionListener(rh);
        
        frame.add(mainBody);
        mainBody.add(fieldContainer);
        mainBody.add(fieldContainer2);
        mainBody.add(addButton);
        
        frame.pack();
    }
    
    
    public void createSubWindow() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
    
}
