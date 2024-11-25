
package gui;

import bll.HistoryHandler;
import dal.DictionaryWord;
import dal.HistoryReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class QueryHistory {
    private static JFrame frame;
    private static HistoryHandler hh;
    private static HistoryReader hReader;
    private static ArrayList<DictionaryWord> dictionary = new ArrayList<>();
    
    private static JPanel controlContainer;
    private static JPanel fieldBox1;
    private static JTextField field1; 

    private static JPanel fieldBox2;
    private static JTextField field2;
    
    private static JButton searchButton;
    
    private static JPanel searchList;
    private static JScrollPane scrollPane;
    
    
    public QueryHistory(LoadingPopup lp) throws ParserConfigurationException, IOException, SAXException {
        hReader = new HistoryReader("Resources\\history.xml");
        
        frame = new JFrame("History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        JPanel mainBody = new JPanel();
        mainBody.setLayout(new BorderLayout());
        mainBody.setSize(800, 600);
        controlContainer = new JPanel();
        
        field1 = new JTextField();
        field1.setEditable(true);
        field2 = new JTextField();
        field2.setEditable(true);
        
        // Create layout
        controlContainer = new JPanel();
        controlContainer.setSize(800, 80);
        controlContainer.setBorder(new EmptyBorder(10,25, 10, 25));
        

        fieldBox1 = new JPanel();
        fieldBox1.setLayout(new GridLayout(2,1));
        fieldBox1.add(new JLabel("Từ ngày: "));
        fieldBox1.add(field1);
        fieldBox1.setBorder(new EmptyBorder(10,15, 10, 15));
        fieldBox1.setBackground(Color.white);
        fieldBox1.setPreferredSize(new Dimension(200,60));

        fieldBox2 = new JPanel();
        fieldBox2.setLayout(new GridLayout(2,1));
        fieldBox2.add(new JLabel("Đến ngày: "));
        fieldBox2.add(field2);
        fieldBox2.setBorder(new EmptyBorder(10,15, 10, 15));
        fieldBox2.setBackground(Color.white);
        fieldBox2.setPreferredSize(new Dimension(200,60));

        searchButton = new JButton("Tìm kiếm");
        searchButton.setSize(50,20);
        
     
        
        controlContainer.add(fieldBox1);
        controlContainer.add(fieldBox2);
        controlContainer.add(searchButton);
        controlContainer.setBorder(new EmptyBorder(10,25, 10, 25));
        controlContainer.setBackground(Color.white);
        
        searchList = new JPanel();
        searchList.setLayout(new GridLayout(10, 3));
        searchList.setSize(800, 520);
        scrollPane = new JScrollPane();
        searchList.setBackground(Color.pink);
        searchList.setVisible(true);
        searchList.setBorder(new EmptyBorder(10,15, 10, 15));
//        searchList.add(scrollPane);
        scrollPane.setBackground(Color.WHITE);
        
        frame.add(mainBody);
        mainBody.add(controlContainer, BorderLayout.PAGE_START);
        mainBody.add(searchList, BorderLayout.CENTER);
        
        hh = new HistoryHandler(this, lp);
        searchButton.addActionListener(hh);
    }
    
    public void loadList(String startDate, String endDate) {
        if (!dictionary.isEmpty()) {
            dictionary.clear();
        }
        
        dictionary = hReader.getList(startDate, endDate);
    }
    
    public void loadList() {
        System.out.println("loadList");
        if (!dictionary.isEmpty()) {
            dictionary.clear();
        }
        
        String startDate = field1.getText();
        String endDate = field2.getText();
        
        dictionary = hReader.getList(startDate, endDate);
    }
    
    public void loadContent() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("loadContent");
            scrollPane.setViewportView(null);
            
            JPanel contentPanel = new JPanel(); // Create a panel to hold components
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 

            for (int i = 0; i < dictionary.size(); i++) {
                System.out.println(i);
                DictionaryWord item = dictionary.get(i);
                ItemCard newItemCard = new ItemCard(item.getWord(), item.getCount());
                searchList.add(newItemCard.getItemCard());
            }
            
            scrollPane.setViewportView(contentPanel);
            scrollPane.revalidate(); 
            scrollPane.repaint(); 
            
            searchList.revalidate();
            searchList.repaint();
           
        });

    }
    
    public void createSubWindow() {
        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    
}
