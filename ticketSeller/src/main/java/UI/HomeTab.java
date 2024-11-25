/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HomeTab extends Tab{
    private GridContainerWithFooter grid;
    
    
    public HomeTab() {
        super("Home");
        grid  = new GridContainerWithFooter(2,2,8,8);
        grid.setBorder(super.margin);
        super.addToTab(grid, BorderLayout.CENTER);
        
    }
    
    public void setCloseConnection() {
        JButton closeButton = new JButton("close server");
        
        closeButton.addActionListener((ActionEvent e) -> {
//            this.vm.setShouldStop(true);
//            JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this.mainContainer);;
//            window.setVisible(false);
//            window.dispose();
            System.exit(0);

        });
        
        grid.setFooter(closeButton);
    }
    
    public void setLink(Tab toTab, String title, JPanel container) {
        JButton newLink = new JButton(title);
        
        newLink.addActionListener((ActionEvent e) -> {
            container.removeAll();
            container.add(toTab.getContainer());
            container.revalidate();
            container.repaint();
        });
        
        grid.addToGrid(newLink);
    }
    
    public void removeAllLink() {
        super.mainContainer.removeAll();
    }

    
    
}
