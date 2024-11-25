
package bll;

import dal.XMLWriter;
import dal.DictionaryWord;
import gui.LikeButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;



public class LikeHandler implements ActionListener{
    private static ArrayList<DictionaryWord> dictionaryAV;
    private static ArrayList<DictionaryWord> dictionaryVA;
    private boolean language;
    
    public LikeHandler(ArrayList<DictionaryWord> AV, ArrayList<DictionaryWord> VA, boolean lang) {
        dictionaryAV = AV;
        dictionaryVA = VA;
        language = lang;
    }
    
    public void setLang(boolean value) {
        language = value;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        LikeButton lb = (LikeButton) e.getSource();
        String word = lb.getActionCommand();
//        System.out.println(word);
        int res = lb.toggleLike();
//        System.out.println(res);
        
//        System.out.println(language);
        ArrayList<DictionaryWord> dictionary = language == true ? dictionaryAV : dictionaryVA;
        
        for (int i = 0; i < dictionary.size(); i++) {
            DictionaryWord entry = dictionary.get(i);
//            System.out.println(entry.getWord() + " || " + word);
            if (entry.getWord() == null ? word == null : entry.getWord().equals(word)) {
                entry.setFavorite(res);
            }
        }      
        try {
            //
            XMLWriter.modifyLike(word, language);
        } catch (TransformerException ex) {
            Logger.getLogger(LikeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
