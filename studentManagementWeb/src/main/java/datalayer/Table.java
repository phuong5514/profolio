
package datalayer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class Table {
    private int _size;
    private int _colSize;
    private ArrayList<Entry> _entries;
    private Entry _header;
    
    public Table() {
        System.out.println("constructing a table");
        _size = 0;
        _entries = new ArrayList();
        _header = new Entry();
        _colSize = 0;
    };
    
    public int parseData(ResultSet rawData) throws SQLException {
        int rowsAffected = 0;
        
        System.out.println("Parsing data...");
        System.out.println("Raw dat: ");
        System.out.println(rawData);
        // Clear existing data
        _entries.clear();
        _size = 0;
        if (rawData == null) {
            return 0;
        }
        ResultSetMetaData metaData = rawData.getMetaData();
       
        
        
        _header.clear();
        _colSize = metaData.getColumnCount();
        for (int i = 1; i <= _colSize; i++) {
            _header.insert(metaData.getColumnLabel(i));
        }
        
        // Extract data from the ResultSet and populate the table
        while (rawData.next()) {
            Entry entry = new Entry();

            for (int i = 1; i <= _colSize; i++) {
                String value = rawData.getString(i);
                entry.insert(value);
            }

            if (insertRow(entry) == 1) {
                rowsAffected++;
            }
        }

        return rowsAffected;
    }
    
    private boolean validate(String key) {
        for (int i = 0; i < _size; i++) {
            if (key == _entries.get(i).get(0)) {
                return false;
            }
        } 
        return true;
    }
    
    
    public int insertRow(Entry newEntry) {
        if (_colSize == newEntry.getSize() && validate(newEntry.get(0))) {
            _entries.add(newEntry);
            _size++;
            return 1;
        }
        
        return 0;
    }
    
    private int insertRow(ArrayList<String> entryStrings) {
        if (_colSize == entryStrings.size()) {
            _entries.add(new Entry(entryStrings));
            _size++;
            return 1;
        }
        
        return 0;
        
        
    }
    
    // broken function?
//    public int removeRow(String rowID) {
//        System.out.println("----");
//        System.out.println(rowID);
//        for (int i = 0; i < _size; i++) {
//            System.out.println(_entries.get(i).get(0));
//            System.out.println(rowID.equals(_entries.get(i).get(0)));
//            System.out.println("----");
//            if (rowID.equals(_entries.get(i).get(0))) {
//                _entries.remove(i);
//                System.out.println("Remove from table");
//                return 1;
//            }
//        }   
//        
//        return 0;
//        
//        
//    }
    
    public int setHeader(Entry header) {
        _header = header;
        if (header.getSize() != _colSize) {
            //flush existing data
            //set new size
            // this is meant to kept the consistency of header's column count to it's entry
            _entries.clear();
            _colSize = header.getSize();
            return 0; 
        }
        
        return 1;
    }
    
    public int setHeader(ArrayList<String> headerString) {
        Entry header = new Entry(headerString);
        return setHeader(header);
    }
    
    public Entry getHeader() {
        return _header;
    }
    
    public Entry getEntry(int index) {
        if (index < 0 || index >= _size) {
            return new Entry();
        }
        
        return _entries.get(index);
    }
    
    public int getLineCount() {
        return this._size;
    }
    
    public int getColCount() {
        return this._colSize;
    }

}
