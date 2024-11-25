/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable{
    // attributes
    private String _eventName;
    private String _eventId;
    private ArrayList<Time> _beginTimes;
    private ArrayList<Time> _endTimes;
    
    private ArrayList<String> _locationIds;
    private ArrayList<Integer> _pricePerLocation;
    
    // constructors
    public Event() {
        _eventName = "";
        _eventId = "";
        _beginTimes = new ArrayList<>();
        _endTimes = new ArrayList<>();
        _locationIds = new ArrayList<>();
        _pricePerLocation = new ArrayList<>();
    }
    
    public Event(String name, String id) {
        this();
        this._eventName = name;
        this._eventId = id;
    }
    
    // getters, setters
    public String eventName() {
        return _eventName;
    }
    
    public String eventId() {
        return _eventId;
    }
    
    public int sessionCount() {
        return _beginTimes.size();
    }
    
    public ArrayList<Time> sessionBegins() {
        return _beginTimes;
    }
    
    public ArrayList<Time> sessionEnds() {
        return _endTimes;
    }
    
    public String getSession(int index) {
        String result = _beginTimes.get(index).toString() + "-" + _endTimes.get(index).toString();
        return result;
    }
    
    public ArrayList<String> durations() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < _beginTimes.size(); i++) {
            result.add(duration(i));
        }
        return result;
    }
    
    public String duration(int index) {
        if (index < 0 || index >= _beginTimes.size()) {
            return "";
        }
        
        Time duration = _beginTimes.get(index).timeDifference(_endTimes.get(index));
        return duration.toString();
    };
    
    public boolean addSession(Time from, Time to) {
//        for (int i = 0; i < _beginTimes.size(); i++) {
//            Time existingFrom = _beginTimes.get(i);
//            Time existingTo = _endTimes.get(i);
//            if (from.before(existingTo) || to.after(existingFrom)) {
//                return false; // Sessions overlap
//            }
//        }
//        
        // Add the new session
        _beginTimes.add(from);
        _endTimes.add(to);
        return true;
    }
    
    
    public void addLocation(String locationId, int price) {
        for (int i = 0; i< _locationIds.size(); i++) {
            if (locationId == null ? _locationIds.get(i) == null : locationId.equals(_locationIds.get(i))) {
                return;
            }
        }
        
        _locationIds.add(locationId);
        _pricePerLocation.add(price);
    }
    

    public String locationId(int index) {
        return _locationIds.get(index);
    }
    
    public int priceOfLocation(int index) {
        return _pricePerLocation.get(index);
    }
    
    public ArrayList<String> locationIds() {
        return _locationIds;
    }
    
    public ArrayList<Integer> priceList() {
        return _pricePerLocation;
    }
    
    
}
