/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.ViewModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.*;



public class Tab {
    protected JPanel mainContainer;
    protected HorizontalContainer header;
    protected JLabel title;
    protected Tab homeTab;
    protected Tab previousTab;
    protected EmptyBorder margin;
    protected EmptyBorder rightMargin;
    protected Border border;
    protected JPanel window;
    protected ViewModel vm;
    
    public Tab(String panelTitle) {
        margin = new EmptyBorder(12,12,12,12);
        rightMargin = new EmptyBorder(0,0,0, 8);
        border = BorderFactory.createLineBorder(Color.gray);
        
        mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setMinimumSize(new Dimension(1200, 600));
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(border);
        
        header = new HorizontalContainer();
        header.setBackground(Color.WHITE);
        header.setBorder(margin);
        
        
        title = new JLabel(panelTitle);
        title.setBorder(rightMargin);   
        header.add(title);
        
        addToTab(header, BorderLayout.NORTH);
    }
    
    public void setHome(Tab home) {
        homeTab = home;
    };
    
    protected void toHome() {
        window.removeAll();
        window.add(homeTab.getContainer());
        window.revalidate();
        window.repaint();
    }
    
    public void setViewModel(ViewModel vm) {
        this.vm = vm;
    }
    
    public void setPreviousTab(Tab prev, JPanel container) {
        previousTab = prev;
        window = container;
        JButton backButton = new JButton("quay lại");
        backButton.addActionListener((ActionEvent e) -> {
            returnToPreviousTab();
        });   
        
        header.add(backButton);
    }
    
    protected void returnToPreviousTab() {
        window.removeAll();
        window.add(previousTab.getContainer());
        window.revalidate();
        window.repaint();
    }
    
    public JPanel getContainer() {
        return this.mainContainer;
    }
    
    public String getTitle() {
        return this.title.getText();
    }
    
    public void addToTab(Component comp, String layout) {
        comp.setMinimumSize(new Dimension(mainContainer.getWidth(), comp.getPreferredSize().height));
        mainContainer.add(comp, layout);
    }
    
    public void addToHeader(Component comp) {
        header.addComponent(comp);
    }
    
    protected void errorPopup(String msg, String type) {
        JOptionPane.showMessageDialog(
            null,
            msg, 
            type,
            JOptionPane.ERROR_MESSAGE
        );
    }
}
