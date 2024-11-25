/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import bll.MenuHandler;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Menu {
    private final JMenuBar menuBar;
    private final MenuHandler menuEventsHandler;
    
    private static QueryHistory qh;
    private static AppendPrompt ap;
    private static RemovePrompt rp;
    
    private static Body appBody;

    private static JMenu editor;
    private static JMenuItem eAdd;
    private static JMenuItem eDelete;
    
    private static JMenu sorter;
    private static JMenuItem sAtoZ;
    private static JMenuItem sZtoA;
    
    private static JToggleButton languageToggle;
    private static final String AV = "EN->VI";
    private static final String VA = "VI->EN";
    
    private static JToggleButton favoriteToggle;
    private static final String FAV = "fav";
    private static final String NOFAV = "normal";
    
    private static JButton dataHandler;
    
    private static PlaceholderTextField searchBar;
    
    private static final String ADD_COMMAND = "ADD";
    private static final String DELETE_COMMAND = "DELETE";
    private static final String SORT_A_COMMAND = "AZ";
    private static final String SORT_Z_COMMAND = "ZA";
    private static final String LANG_TOGGLE = "LANGTOGGLE";
    private static final String FAV_TOGGLE = "FAVTOGGLE";
    private static final String DATA_HANDLE = "DATAHANDLE";
    private static final String SEARCH = "SEARCH";
    public Menu(Body appBody) throws ParserConfigurationException, IOException, SAXException {
        menuBar = new JMenuBar();
        menuEventsHandler = new MenuHandler(appBody, this);
        Menu.appBody = appBody;
        qh = new QueryHistory(appBody.getPU());
        ap = new AppendPrompt(appBody);
        rp = new RemovePrompt(appBody);
        
        editor = new JMenu("Edit");
        
        eAdd = new JMenuItem("Add new entry");
        eAdd.setActionCommand(ADD_COMMAND);
        eAdd.setMnemonic(KeyEvent.VK_A);
        eAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        eAdd.addActionListener(menuEventsHandler);

        eDelete = new JMenuItem("Delete an entry");
        eDelete.setActionCommand(DELETE_COMMAND);
        eDelete.setMnemonic(KeyEvent.VK_D);
        eDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        eDelete.addActionListener(menuEventsHandler);
        
        editor.add(eAdd);
        editor.add(eDelete);
        
        sorter = new JMenu("Sort");
        
        sAtoZ = new JMenuItem("A - Z");
        sAtoZ.setActionCommand(SORT_A_COMMAND);
        sAtoZ.setMnemonic(KeyEvent.VK_A);
        sAtoZ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        sAtoZ.addActionListener(menuEventsHandler);
        
        sZtoA = new JMenuItem("Z - A");
        sZtoA.setActionCommand(SORT_Z_COMMAND);
        sZtoA.setMnemonic(KeyEvent.VK_Z);
        sZtoA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        sZtoA.addActionListener(menuEventsHandler);
        
        sorter.add(sAtoZ);
        sorter.add(sZtoA);
        
        languageToggle = new JToggleButton("ENG->VI");
        languageToggle.setActionCommand(LANG_TOGGLE);
        languageToggle.setMnemonic(KeyEvent.VK_L);
        languageToggle.addActionListener(menuEventsHandler);
        languageToggle.setPreferredSize(new Dimension(100, 0));
        
        favoriteToggle = new JToggleButton(NOFAV);
        favoriteToggle.setActionCommand(FAV_TOGGLE);
        favoriteToggle.setMnemonic(KeyEvent.VK_F);
        favoriteToggle.addActionListener(menuEventsHandler);
        favoriteToggle.setPreferredSize(new Dimension(100, 0));
        
        dataHandler = new JButton("Data");
        dataHandler.setActionCommand(DATA_HANDLE);
        dataHandler.setMnemonic(KeyEvent.VK_D);
        dataHandler.addActionListener(menuEventsHandler); 
        
        searchBar = new PlaceholderTextField();
        searchBar.setPlaceholder("Tìm kiếm");
        searchBar.setEditable(true);
        searchBar.setActionCommand(SEARCH);
        searchBar.addActionListener(menuEventsHandler);
        
        menuBar.add(editor);
        menuBar.add(sorter);
        menuBar.add(languageToggle);
        menuBar.add(favoriteToggle);
        menuBar.add(dataHandler);
        menuBar.add(searchBar);
    }
    
    public void setFavoriteStatus(boolean value) {
        if (value) {
            favoriteToggle.setText(FAV);
        } else {
            favoriteToggle.setText(NOFAV);
        }
    }
    
    public void toggleLanguage() {
        if (languageToggle.getText() == null ? AV == null : languageToggle.getText().equals(AV)) {
            languageToggle.setText(VA);
        } else {
            languageToggle.setText(AV);
        }
    }
    
    public boolean toggleFavoriteStatus() {
        if (favoriteToggle.getText().equals(FAV)) {
            favoriteToggle.setText(NOFAV);
            return false;
        } else {
            favoriteToggle.setText(FAV);
            return true;
        }
    }
    
    public JMenuBar getMenu() {
        return this.menuBar;
    }
    
    public QueryHistory getQH() {
        return qh;
    }
    
    public AppendPrompt getAP() {
        return ap;
    }
    
    public RemovePrompt getRP() {
        return rp;
    }
}