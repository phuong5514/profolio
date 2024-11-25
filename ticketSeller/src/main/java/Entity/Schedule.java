
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class Schedule implements Serializable{
   private ArrayList<Event> _events;
   private Date _day;
   
   public Schedule() {
       _events = new ArrayList<>();
       _day = new Date();
   }
   
   public Schedule(Date day, ArrayList<Event> events) {
       _events = events;
       _day = day;
   }
   
   public void setDay(int d, int m, int y) {
       _day.setDate(d,m,y);
   }
   
   public void addEvent(Event newEvent) {
        Iterator<Event> iterator = _events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.eventId().equals(newEvent.eventId())) {
                return;
            }
        }
       _events.add(newEvent);
   }
   
    public void removeEvent(Event eventToRemove) {
        Iterator<Event> iterator = _events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.eventId().equals(eventToRemove.eventId())) {
                iterator.remove();
                break; // Assuming event IDs are unique, we can stop after finding the match
            }
        }
    }

   
    public Event getEvent(int index) {
        return _events.get(index);
    }

    public ArrayList<Event> getEventList() {
        return _events;
    }
   
    public Date getDay() {
        return _day;
    }

    public void setDay(Date day) {
        _day = day;
    }    
    
    public boolean isEventExist(String id) {
        for (Event e : this._events) {
            if (id.equals(e.eventId())) {
                return true;
            }
        }
        return false;
    }
}
