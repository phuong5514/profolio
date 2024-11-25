package Entity;

import java.io.Serializable;

public class Location implements Serializable{
    // attributes
    private int _rowCount;
    private int _seatsPerRow;
    private String _locationName;
    private String _locationId;
    
   
    
    public Location() {
        _rowCount = 0;
        _seatsPerRow = 0;
        _locationName = "";
        _locationId = "";
    }
   
    
    public Location(int rowCount, int seatsPerRow, String locationName, String locationId) {
        _rowCount = rowCount;
        _seatsPerRow = seatsPerRow;
        _locationName = locationName;
        _locationId = locationId;
    }
    
    public int capacity() {
        return _rowCount * _seatsPerRow;
    }
    
    
        public int getRowCount() {
        return _rowCount;
    }

    public int getSeatsPerRow() {
        return _seatsPerRow;
    }

    public String getLocationName() {
        return _locationName;
    } 
    
    public String getLocationId() {
        return _locationId;
    }

    public void setRowCount(int parseInt) {
        _rowCount = parseInt;
    }

    public void setSeatsPerRow(int parseInt) {
        _seatsPerRow = parseInt;
    }

    public void setLocationName(String value) {
        _locationName = value;
    }
    
    public void setLocationId(String value) {
        _locationId = value;
    }
}
