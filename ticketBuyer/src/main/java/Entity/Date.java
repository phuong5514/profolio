package Entity;

import java.io.Serializable;

public class Date implements Serializable{
    private int _day;
    private int _month;
    private int _year;
    
    public Date() {
        _day = 0;
        _month = 0;
        _year = 0;
    } 
    
    public Date(int d, int m, int y) {
        _day = d;
        _month = m;
        _year = y;
    }
    
    public Date(String dateString) {
        setDate(dateString);
    }
    
    public void setDate(int d, int m, int y) {
        _day = d;
        _month = m;
        _year = y;
    }
    
    public void setDate(String dateString) {
        String[] parts = dateString.split("/");
        if (parts.length == 3) {
            try {
                _day = Integer.parseInt(parts[0]);
                _month = Integer.parseInt(parts[1]);
                _year = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                _day = 0;
                _month = 0;
                _year = 0;
            }
        } else {
            _day = 0;
            _month = 0;
            _year = 0;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", _day, _month, _year);
    }
}
