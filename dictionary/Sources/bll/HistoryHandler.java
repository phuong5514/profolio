/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package bll;

import gui.LoadingPopup;
import gui.QueryHistory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author LENOVO
 */
public class HistoryHandler implements ActionListener{
    private static QueryHistory window;
    private static LoadingPopup lp;
    public HistoryHandler(QueryHistory window, LoadingPopup lp) {
        HistoryHandler.window = window;
        HistoryHandler.lp = lp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        lp.showLoadingPopup();
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                window.loadList();

                return null;
            }
            
            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    window.loadContent();
                    lp.hideLoadingPopup();
                });
            }
        };
        
        worker.execute();
    }
}
