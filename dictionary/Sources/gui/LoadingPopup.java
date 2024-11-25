
package gui;

import javax.swing.*;
import java.awt.*;


public class LoadingPopup extends JFrame {
    private JLabel loadingLabel;
    private JPanel container;
    public LoadingPopup() {
        initializeComponents();
    }

    private void initializeComponents() {
        // Create a label to display "Loading..."
        loadingLabel = new JLabel("Loading...");
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setVerticalAlignment(SwingConstants.CENTER);
        loadingLabel.setFont(loadingLabel.getFont().deriveFont(Font.BOLD, 20f));
        loadingLabel.setPreferredSize(new Dimension(200, 80));
        // Set layout for the frame
        setLayout(new BorderLayout());

        container = new JPanel();
        container.setBackground(new Color(161, 141, 88));
        container.setPreferredSize(new Dimension(200, 80));
        container.add(loadingLabel);
        
        
        // Set frame properties
        setTitle("Loading");
        setSize(200, 100);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    public void showLoadingPopup() {
        setContentPane(container);
        pack();
        setVisible(true);
    }

    public void hideLoadingPopup() {
        // Hide the frame
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoadingPopup loadingPopup = new LoadingPopup();
            loadingPopup.showLoadingPopup();

            
        });
    }
}

