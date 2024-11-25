/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Business;

import Entity.Location;
import Entity.Schedule;
import Internal.Config;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LENOVO
 */
public class ScheduleDataXMLHandler extends XML{
    private ScheduleToElement _stoe;
    
    
    public ScheduleDataXMLHandler() {
        _stoe = new ScheduleToElement();
    }
    
    @Override
    public void readFile(String filename) {
        
        try {
            DocumentBuilder db = Config.dbf.newDocumentBuilder();
            Config.ScheduleData = db.parse(new File(filename));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Failed to read the XML file.", e);
        }
        
        
    }

    @Override
    public void addToFile(Object object) {
        if (!(object instanceof Schedule)) {
            throw new IllegalArgumentException("Object must be of type Schedule");
        }
        
        Document doc = Config.ScheduleData;
        Schedule schedule = (Schedule) object;
        
        Element newSchedule = _stoe.toXMLElement(doc, schedule);
        
        doc.getDocumentElement().appendChild(newSchedule);
        
        saveChanges(doc, Config.pathToScheduleData);
    }

    @Override
    public void removeFromFile(Object object) {
        if (!(object instanceof Schedule)) {
            throw new IllegalArgumentException("Object must be of type Schedule");
        }
        
        Schedule removeTarget = (Schedule) object;
        Document doc = Config.ScheduleData;
        
        NodeList schedules = doc.getElementsByTagName("schedule");
        for (int i = 0; i < schedules.getLength(); i++) {
            Element schedule = (Element) schedules.item(i);
            if (schedule.getAttribute("Date").equals(removeTarget.getDay().toString())) {
                schedule.getParentNode().removeChild(schedule);
                break;
            }
        }
        
        saveChanges(doc, Config.pathToScheduleData);
    }
}
