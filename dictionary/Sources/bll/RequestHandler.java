/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package bll;

import dal.XMLWriter;
import gui.Body;
import gui.PromptWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

/**
 *
 * @author LENOVO
 */
public class RequestHandler implements ActionListener{
    private final PromptWindow window;
    private static Body mainBody;
    
    public RequestHandler(PromptWindow window, Body mainBody) {
        this.window = window;
        RequestHandler.mainBody = mainBody;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String word = window.getWord();
        String meanings = window.getMeanings();
        boolean lang = window.getLang();
        System.out.println(lang);
        switch (e.getActionCommand()) {
            case "remove" -> {
                try {
                    XMLWriter.deleteWord(word, lang);
                    System.out.println("Da xoa tu " + word);
                    mainBody.reloadDictionary();
                    window.close();
                } catch (TransformerException ex) {
                    Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            case "add" -> {
                try {
                    XMLWriter.addWord(word, meanings, lang);
                    System.out.println("Them tu thanh cong");
                    mainBody.reloadDictionary();
                    window.close();
                } catch (TransformerException ex) {
                    Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
