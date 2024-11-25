/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package gui;

import bll.RequestHandler;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class PromptWindow {
    protected JFrame frame;
    protected JPanel mainBody;
    protected static boolean lang;
    protected RequestHandler rh;
   
    public abstract String getWord();

    public abstract String getMeanings();
    
    public boolean getLang() {
        return lang;
    }
    
    public void setLang(boolean l) {
        lang = l;
    }
    
    public void close() {
        frame.dispose();
    }
}
