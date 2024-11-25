
package datalayer;

import java.util.ArrayList;

public class Entry {
    private int _size;
    private ArrayList<String> _row;

    public Entry() {
        _size = 0;
        _row = new ArrayList();
    }
    
    public Entry(ArrayList<String> entryString) {
        _row = entryString;
        _size = entryString.size();
    }

    public void insert(String entry) {
        _row.add(entry);
        _size++;
    }
    
    public int getSize() {
        return _size;
    }
    
    public ArrayList<String> row() {
        return _row;
    }
    
    public String get(int index) {
        return _row.get(index);
    }
    
    public String parse() {
        if (_size == 0) {
            return "()"; // Return empty parentheses if _row is empty
        }

        StringBuilder res = new StringBuilder("(");
        for (int i = 0; i < _size; i++) {
            res.append(_row.get(i));
            if (i < _size - 1) {
                res.append(",");
            } else {
                res.append(")");
            }
        }
        return res.toString();
    }
    
    public String pop() {
        if (_size >= 1) {
            String res = _row.getLast();
            _row.removeLast();
            _size--;
            return res;
        } else {
            return "";
        }
        
    }
    
    
    public void clear() {
        _size = 0;
        _row.clear();
    }
}
