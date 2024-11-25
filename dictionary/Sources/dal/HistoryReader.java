
package dal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LENOVO
 */
public class HistoryReader extends History{
    
    public ArrayList<DictionaryWord> getList(String start, String end) {
        // format dd/mm/yyyy
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        
        LocalDate startDate = LocalDate.parse( start, f ) ;
        System.out.println(startDate);
        
        LocalDate endDate = LocalDate.parse( end , f ) ;
        System.out.println(endDate);
//        boolean isBefore = startDate.isBefore( endDate ) ;
//        
//        if (isBefore == false) {
//            return new ArrayList<>();
//        }
        
        ArrayList<DictionaryWord> dictionary = new ArrayList<>();
        HashMap<String, Integer> wordQuery = new HashMap<>();
        
        NodeList list = document.getElementsByTagName(ENTRY);
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            
            LocalDate date = LocalDate.parse(element.getAttribute(DATE), f);
            if (date.isBefore(startDate) || date.isAfter(endDate)) {
                continue;
            }
            
            NodeList entryList = element.getElementsByTagName(WORD);
        
            for (int j = 0; j < entryList.getLength(); j++) {
                Element wordNode = (Element) entryList.item(j);
                String word = wordNode.getTextContent();
                int count  = Integer.parseInt(wordNode.getAttribute(COUNT));
                
                if (wordQuery.containsKey(word)) {
                    wordQuery.put(word, count + wordQuery.get(word));
                    System.out.println(word);
                } else {
                    wordQuery.put(word, count);
                }
            }
            
        }
        
        for (String word : wordQuery.keySet()) {
            dictionary.add(new DictionaryWord(word, wordQuery.get(word)));
        }
        return dictionary;
        
    }
    
    
    public HistoryReader(String filename) throws ParserConfigurationException, SAXException, IOException {
        super(filename);
    }
    
}
