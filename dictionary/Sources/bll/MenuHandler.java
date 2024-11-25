
package bll;

import dal.HistoryWriter;
import gui.AppendPrompt;
import gui.Body;
import gui.Menu;
import gui.RemovePrompt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.xml.transform.TransformerException;


public class MenuHandler implements ActionListener {
    private static Body mainBody;
    private static Menu mainMenu;
    public MenuHandler(Body body, Menu menu) {
        mainBody = body;
        mainMenu = menu;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean lang = mainBody.getLanguage();
        System.out.println(lang);
        switch (e.getActionCommand()) {
            case "ADD" -> {
                AppendPrompt ap = mainMenu.getAP();
                ap.setLang(lang);
                ap.createSubWindow();
            }

            case "DELETE" -> {
                RemovePrompt rp = mainMenu.getRP();
                rp.setLang(lang);
                rp.createSubWindow();
            }
                
            case "AZ" -> {
                if (mainBody.inverted()) {
                    mainBody.toggleOrder();
                }
                if (mainBody.isSpecial()) {
                    mainBody.loadSpecializedContent(2);
                } else {
                    mainBody.loadContent(2);
                }  
                mainBody.prevPage();
            }
                
            case "ZA" -> {
                if (!mainBody.inverted()) {
                    mainBody.toggleOrder();
                }
                if (mainBody.isSpecial()) {
                    mainBody.loadSpecializedContent(2);
                } else {
                    mainBody.loadContent(2);
                }  
                mainBody.prevPage();
            }
            
            case "LANGTOGGLE" -> {
                mainMenu.toggleLanguage();
                mainBody.changeLanguage();
                mainMenu.setFavoriteStatus(false);
            }
                
            case "FAVTOGGLE" -> {
                boolean status = mainMenu.toggleFavoriteStatus();
                if (status) {
                    mainBody.loadFavoriteDictionary();
                    mainBody.loadSpecializedContent(1);
                } else {
                    mainBody.revert();
                    mainBody.loadContent(1);
                }
            }
                
            case "DATAHANDLE" -> mainMenu.getQH().createSubWindow();
                
            case "SEARCH" -> {
                
                JTextField source = (JTextField) e.getSource();
                String query = source.getText(); 
                if ("".equals(query))   {
                    if (mainBody.getFavoriteStatus() == true) {
                        mainBody.loadFavoriteDictionary();
                        mainBody.loadSpecializedContent(1);
                    } else {
                        mainBody.revert();
                        mainBody.loadContent(1);    
                    }
                    return;
                }
                {
                    
                    try {
                        HistoryWriter.saveSearch(query);
                        HistoryWriter.saveFile();
                    } catch (TransformerException ex) {
                        Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mainBody.loadSearchDictionary(query);
                mainBody.loadSpecializedContent(1);
            }

        }
    }

    
}

