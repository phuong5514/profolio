 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Internal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

public class Config {
    // tạo document
    public static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
    // link
    public static String pathToLocations = "Resource\\Locations.xml";
    public static String pathToScheduleData = "Resource\\ScheduleData.xml";
    public static String pathToTicketData = "Resource\\TicketsData.xml";
    public static String pathToUserData = "Resource\\UserData.xml";
    
    // lưu trữ
    public static Document Locations;
    public static Document ScheduleData;
    public static Document TicketData;
    public static Document UserData;
    
    // configs / keywords
    public static int columnsPerPage = 4;
    public static int rowsPerContainer = 6;
    
    public static String messageKeywordSeparator = "_";
    public static String messageMultipartsSeparator = "-";
    public static String messageListItemSeparator = ",";
    
    public static String locationsRequest = "get_locations"; // get location's id list
    public static String eventsRequest = "get_events";
    public static String seatDetailsRequest = "get_seat_details";
    public static String seatSizeRequest = "get_seat_size";
    public static String closeConnectionRequest = "quit";
    public static String buyTicketRequest = "send_ticket";
    
    public static int maxConnection = 10;
    public static int port = 2732;
    
    public static int dayLimit = 7;
}