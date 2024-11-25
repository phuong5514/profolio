/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dal;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.xml.sax.SAXException;

public class XMLReader extends XML{
    public XMLReader(String filename1, String filename2) throws ParserConfigurationException, SAXException, IOException {
        super(filename1, filename2);
    }
    
    public ArrayList<DictionaryWord> getList(boolean lang) {
        Document document = lang == true ? documentEN : documentVI;
        
        ArrayList<DictionaryWord> dictionary = new ArrayList<>();
        NodeList list = document.getElementsByTagName(ENTRY);
        for (int i = 0; i < list.getLength(); i++) {
            Element node = (Element) list.item(i);
            NodeList childrens = node.getChildNodes();
            String title = null;
            String meanings = null;
            boolean favoriteStatus = false;
            for (int j = 0; j < childrens.getLength(); j++) {
                Node child = childrens.item(j);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) child;
                    String tagName = childElement.getTagName();
                    
                    if (childElement.hasAttribute(FAVORITE)) {
                        favoriteStatus = true;
                    }
             
                    if (tagName == null ? WORD == null : tagName.equals(WORD)) {
                        title = childElement.getTextContent();
                    } else if (tagName == null ? MEANING == null : tagName.equals(MEANING)) {
                        meanings = childElement.getTextContent();
                    }
                }
            }
            
            String[] lines = meanings.split("\n");
//            System.out.println(title);
//            System.out.println("Meanings");
            for (String line : lines) {
                System.out.println(line);
            }
            dictionary.add(new DictionaryWord(title, new ArrayList<> (Arrays.asList(lines)), favoriteStatus));
        }
        return dictionary;
    }
    

    
    public Document getDocument(boolean lang) {
        return lang == true ? documentEN : documentVI;
    }
}