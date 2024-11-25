/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dal;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LENOVO
 */
public class HistoryWriter extends History{ 
    public HistoryWriter(String filename) throws ParserConfigurationException, SAXException, IOException {
        super(filename);
    }
    
    public static void saveFile() throws TransformerConfigurationException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(file));
        transformer.transform(source, result);
    }
    
    public static void saveSearch(String word) throws TransformerConfigurationException, TransformerException {
        LocalDate currentDate = LocalDate.now();
        NodeList list = document.getElementsByTagName(ENTRY);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            LocalDate entryDate = LocalDate.parse(element.getAttribute(DATE), f);
            if (currentDate.equals(entryDate)) {
                NodeList records = element.getElementsByTagName(WORD);
                for (int j = 0; j < records.getLength(); j++) {
                    Element record = (Element) records.item(j);
                    if (word == null ? record.getTextContent() == null : word.equals(record.getTextContent())) {
                        int count = Integer.parseInt(record.getAttribute(COUNT));
                        count++;
                        record.setAttribute(COUNT, String.valueOf(count));
                        return;
                    }
                }
                
                Element newRecord = document.createElement(WORD);
                newRecord.setTextContent(word);
                newRecord.setAttribute(COUNT, "1");
                element.appendChild(newRecord);
                return;
                
            }
        }
        
        Element newDateEntry = document.createElement(ENTRY);
        newDateEntry.setAttribute(DATE, currentDate.format(f)); // format dd/mm/yyyy
        document.getDocumentElement().appendChild(newDateEntry);
        Element newRecord = document.createElement(WORD);
        newRecord.setTextContent(word);
        newRecord.setAttribute(COUNT, "1");
        newDateEntry.appendChild(newRecord);
    }
    
    
}
