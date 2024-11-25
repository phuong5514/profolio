/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package gui;

import bll.LikeHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ItemCard {
    private JPanel itemCard;
    private JPanel wordCol;
    private JPanel defCol;
    private JPanel miscCol; // favorite and stuff
    private LikeButton favoriteButton;
    private String word;
    private boolean favorite;
    
    public void addEventListener(LikeHandler lh) {
        favoriteButton.setActionCommand(word);
        favoriteButton.addActionListener(lh);
    }
    
    public ItemCard() {
        itemCard = new JPanel();
        itemCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.X_AXIS));
        
        defCol = new JPanel();
        defCol.setBackground(Color.white);
        defCol.setLayout(new BoxLayout(defCol, BoxLayout.Y_AXIS));
        defCol.setBackground(Color.white);
        
        JTextArea textArea = new JTextArea();
        textArea.append("Rỗng, chưa có từ được thêm vào danh sách yêu thích hoặc từ tìm kiếm không tồn tại trong từ điển");
        textArea.setEditable(false);
       // JScrollPane scrollPane = new JScrollPane(textArea);
        defCol.add(textArea);
        
        
        defCol.setPreferredSize(new Dimension(1000, itemCard.getHeight()));
        itemCard.setPreferredSize(new Dimension(1000, 600));
        itemCard.add(defCol);
    }
    
    public ItemCard(String word, ArrayList<String> definitions, boolean favorite) {
        itemCard = new JPanel();
        itemCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.X_AXIS));
        wordCol = new JPanel();
        
        defCol = new JPanel();
        defCol.setBackground(Color.white);
        defCol.setLayout(new BoxLayout(defCol, BoxLayout.Y_AXIS));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        defCol.add(scrollPane);

        for (String def : definitions) {
            textArea.append(def + "\n");
        }

        miscCol = new JPanel(new BorderLayout());
        miscCol.setBackground(Color.white);
        this.favorite = favorite;
        favoriteButton = new LikeButton(favorite);
        favoriteButton.setPreferredSize(new Dimension(20,20));
        
//        LikeHandler lh = new LikeHandler();
//        favoriteButton.addActionListener(lh);
        
        miscCol.add(favoriteButton, BorderLayout.CENTER);
        
        this.word = word;
        JTextArea textArea2 = new JTextArea();
        textArea2.setEditable(false);
        textArea2.setLineWrap(true);
        textArea2.getCaret().setVisible(false);
        textArea2.setHighlighter(null);
        textArea2.setFocusable(false);
        textArea2.setBackground(new Color(0, 0, 0, 0));
        textArea2.setFont(textArea2.getFont().deriveFont(Font.BOLD, 14f));
        textArea2.append(word);
        wordCol.add(textArea2);

        
        itemCard.add(wordCol);
        itemCard.add(defCol);
        itemCard.add(miscCol);   
        
        int height = itemCard.getHeight();
        wordCol.setPreferredSize(new Dimension(250, height)); 
        defCol.setPreferredSize(new Dimension(650, height));
        miscCol.setPreferredSize(new Dimension(100, height));
        
        
        
    }
    
    public ItemCard(String word, int count) {
        System.out.println("----");

        System.out.println(word);
        System.out.println(count);

        itemCard = new JPanel();
        itemCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.X_AXIS));
        
        wordCol = new JPanel();

        miscCol = new JPanel(new BorderLayout());
        miscCol.setBackground(Color.white);
        miscCol.add(new JLabel(String.valueOf(count) + " lần"));
        
        this.word = word;
        JTextArea textArea2 = new JTextArea();
        textArea2.setEditable(false);
        textArea2.getCaret().setVisible(false);
        textArea2.setFocusable(false);
        textArea2.setHighlighter(null);
        textArea2.setFocusable(false);
        textArea2.setLineWrap(true);
        textArea2.setBackground(new Color(0, 0, 0, 0));
        textArea2.setFont(textArea2.getFont().deriveFont(Font.BOLD, 14f));
        textArea2.append(word);
        wordCol.add(textArea2);

        
        itemCard.add(wordCol);
        itemCard.add(miscCol);   
        
        int height = 20;
        wordCol.setPreferredSize(new Dimension(200, height)); 
        miscCol.setPreferredSize(new Dimension(200, height));
        
        
        
    }

    
    public JPanel getItemCard() {
        return itemCard;
    }
    
    public String getWord() {
        return this.word;
    }
}
