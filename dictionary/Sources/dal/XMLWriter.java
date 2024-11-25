
package dal;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Locale;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLWriter extends XML{
    public XMLWriter(String f1, String f2) throws ParserConfigurationException, SAXException, IOException {
        super(f1,f2);
    }
    
    public static void modifyLike(String word, boolean lang) throws TransformerConfigurationException, TransformerException {
        Document document = lang == true ? documentEN : documentVI;
        String file = lang == true ? fileEN : fileVI;
        NodeList list = document.getElementsByTagName(ENTRY);
        for (int i = 0; i < list.getLength(); i++) {
            Element node = (Element) list.item(i);
            NodeList childrens = node.getChildNodes();
            String title;
            for (int j = 0; j < childrens.getLength(); j++) {
                Node child = childrens.item(j);
                if (child.getNodeType() == Node.ELEMENT_NODE) {

                    Element childElement = (Element) child;
                    String tagName = childElement.getTagName();
                    
                    if (tagName == null ? WORD == null : tagName.equals(WORD)) {   

                        title = childElement.getTextContent();
                        
                        if (title == null ? word == null : title.equals(word)) {
                            
                            if (!childElement.hasAttribute(FAVORITE)) {
                                
                                childElement.setAttribute(FAVORITE, "true");
                                System.out.println("word is now like in xml");
                            } else {
                                
                                childElement.removeAttribute(FAVORITE);
                                System.out.println("word is now unlike in xml");
                            }
                            
                        }
                    }
                }
            }
        }
        
        saveFile(document, file);
    }
    
    public static void deleteWord(String word, boolean lang) throws TransformerException {
        Document document = lang ? documentEN : documentVI;
        String file = lang ? fileEN : fileVI;
        NodeList list = document.getElementsByTagName(ENTRY);
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList childrens = element.getChildNodes();
                String title = null;
                for (int j = 0; j < childrens.getLength(); j++) {
                    Node child = childrens.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE && WORD.equals(child.getNodeName())) {
                        title = child.getTextContent();
                        break;
                    }
                }
                if (title != null && title.equals(word)) {
                    node.getParentNode().removeChild(node);
                    break; 
                }
            }
        }

        saveFile(document, file);
    }
    
    public static void addWord(String word, String meanings, boolean lang) throws TransformerException {
        if ("".equals(word) || "".equals(meanings)) {
            System.out.println("du lieu khong hop le, them tu khong thanh cong");
            return;
        } 
        
        
        Document document = lang ? documentEN : documentVI;
        String file = lang ? fileEN : fileVI;
        NodeList list = document.getElementsByTagName(ENTRY);
        
        Element newRecord = document.createElement(ENTRY);
        Element newWord = document.createElement(WORD);
        newWord.setTextContent(word);
        Element newMeaning = document.createElement(MEANING);
        newMeaning.setTextContent(meanings);
        newRecord.appendChild(newWord);
        newRecord.appendChild(newMeaning);
        
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String existingWord = element.getElementsByTagName("word").item(0).getTextContent();
                
                Locale vietnameseLocale = new Locale("vi", "VN");
                Collator collator = Collator.getInstance(vietnameseLocale);
                
                int result = collator.compare(word, existingWord);
                
                if (result < 0) {
                    element.getParentNode().insertBefore(newRecord, element);
                    saveFile(document, file);
                    return; // Inserted the new word, so exit the method
                } else {
                }
            }
        }
        
        document.getDocumentElement().appendChild(newRecord);
        saveFile(document, file);
    }
    
    public static void saveFile(Document document, String file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(file));
        transformer.transform(source, result);
    }
}
