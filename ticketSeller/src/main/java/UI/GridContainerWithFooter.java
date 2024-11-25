/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class GridContainerWithFooter extends JPanel {
    private JPanel gridPanel;
    private JPanel footerPanel;
    private int _columnCount;

    public GridContainerWithFooter(int rows, int cols, int hgap, int vgap) {
        setLayout(new BorderLayout());

        // Initialize the grid panel with a GridLayout
        gridPanel = new JPanel(new GridLayout(rows, cols, hgap, vgap));
        add(gridPanel, BorderLayout.CENTER);

        // Initialize the footer panel
        footerPanel = new JPanel();
        add(footerPanel, BorderLayout.SOUTH);
        
        _columnCount = cols;
    }

    // Method to add a component to the grid
    public void addToGrid(Component component) {
        gridPanel.add(component);
        revalidate();
        repaint();
    }

    // Method to set the footer component
    public void setFooter(Component component) {
        footerPanel.removeAll();
        footerPanel.add(component);
        revalidate();
        repaint();
    }
    
    public void removeRow(int rowIndex) {

        // Calculate the range of components to remove
        int startIndex = rowIndex * _columnCount;
        int endIndex = startIndex + _columnCount;

        // Remove components from the specified row
        for (int i = startIndex; i < endIndex; i++) {
            gridPanel.remove(startIndex); 
        }

        revalidate();
        repaint();
    }   
}