/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dal;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XML {
    private final DocumentBuilderFactory dbf;
    private final DocumentBuilder db;
    protected static Document documentEN;
    protected static Document documentVI;
    
    protected static String fileEN;
    protected static String fileVI;
    
    protected static final String ENTRY = "record";
    protected static final String WORD = "word";
    protected static final String MEANING = "meaning";
    protected static final String FAVORITE = "fav";
   
    public XML(String filename1, String filename2) throws ParserConfigurationException, SAXException, IOException {
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        
        fileEN = filename1;
        fileVI = filename2;
        
        documentEN = db.parse(new File(filename1)); 
        documentVI = db.parse(new File(filename2)); 
    }
}
