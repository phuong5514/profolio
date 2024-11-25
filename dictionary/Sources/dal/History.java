
package dal;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class History {
    private final DocumentBuilderFactory dbf;
    private final DocumentBuilder db;
    protected static Document document;
    
    protected static String file;
    
    protected static final String ENTRY = "entry";
    protected static final String DATE = "date";
    protected static final String RECORD = "record";
    protected static final String COUNT = "count";
    protected static final String WORD = "word";

    
    public History(String filename) throws ParserConfigurationException, SAXException, IOException {
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();

        file = filename;
        
        document = db.parse(new File(filename));
    }
}
