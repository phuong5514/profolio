package Business;

import Entity.Event;
import Entity.Location;
import Entity.Schedule;
import Entity.Ticket;
import Helpers.Tuple;
import Internal.Config;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class ViewModel {
    private static ScheduleDataXMLHandler scheduleDataHandler;
    private static ScheduleParser scheduleParser;
    private ArrayList<Schedule> storedSchedules;
    
    private static LocationXMLHandler locationHandler;
    private static LocationParser locationParser;
    private ArrayList<Location> locationDetails;
    
    
    private static TicketsDataXMLHandler ticketHandler;
    private static TicketParser ticketParser;
    private HashMap<String, HashMap<String, ArrayList<Ticket>>> ticketStorage;

    private volatile boolean shouldStop = false;

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }
    
    public boolean shouldStop() {
        return shouldStop;
    }
    
    
    public ViewModel() {
        if (scheduleDataHandler == null) {
            scheduleDataHandler = new ScheduleDataXMLHandler();
        } 
        
        if (scheduleParser == null) {
            scheduleParser = new ScheduleParser();
        }
            
        if (locationHandler == null) {
            locationHandler = new LocationXMLHandler();
        }
        
        if (locationParser == null) {
            locationParser = new LocationParser();
        }
        
        if (ticketHandler == null) {
            ticketHandler = new TicketsDataXMLHandler();
        } 
        
        if (ticketParser == null) {
            ticketParser = new TicketParser();
        }
        
        loadData();
        
        System.out.println("tickets keys");
        System.out.println(ticketStorage.keySet());
    }
    
    public void loadData() {
        readScheduleList();
        parseSchedules();
        
        readLocationList();
        parseLocations();
        
        readTicketData();
        parseTicketData();
    }
    
    public void readScheduleList() {
        System.out.print(Config.pathToScheduleData);
        scheduleDataHandler.readFile(Config.pathToScheduleData);
    }
    
    public void parseSchedules() {
        storedSchedules = (ArrayList<Schedule>) scheduleParser.parse(Config.ScheduleData);
    }
    
    public void addSchedule(Schedule newSchedule) {
//        storedSchedules.add(newSchedule);
        scheduleDataHandler.addToFile(newSchedule);
        readScheduleList();
        parseSchedules();
    }
    
    public void updateSchedule(Schedule oldSchedule, Schedule newSchedule) {
        deleteSchedule(oldSchedule);
        addSchedule(newSchedule);
    }
    
    public void deleteSchedule(Schedule oldSchedule) {
        for (int i = 0; i < storedSchedules.size(); i++) {
            Schedule schedule = storedSchedules.get(i);
            String oldScheduleId = oldSchedule.getDay().toString();
            String comparingScheduleId = schedule.getDay().toString();
            if (oldScheduleId == null ? comparingScheduleId == null : oldScheduleId.equals(comparingScheduleId)) {
//                storedSchedules.remove(i);
                
                scheduleDataHandler.removeFromFile(schedule);
                readScheduleList();
                parseSchedules();
            }
        }
    }
    
    public void addEvent(Schedule schedule ,Event newEvent) {
        schedule.addEvent(newEvent);
        updateSchedule(schedule, schedule);
    }
    
    public void updateEvent(Schedule schedule ,Event newEvent, Event oldEvent) {
        schedule.removeEvent(oldEvent);
        schedule.addEvent(newEvent);
        updateSchedule(schedule, schedule);
    }
    
    public void deleteEvent(Schedule schedule ,Event oldEvent) {
        schedule.removeEvent(oldEvent);
        updateSchedule(schedule, schedule);
    }
    
    public void readLocationList() {
        locationHandler.readFile(Config.pathToLocations);
        
    }
    
    public void addLocation(Location newLocation) {
//        locationDetails.add(newLocation);
        locationHandler.addToFile(newLocation);
        
        readLocationList();
        parseLocations();
        
    }
    
    public void updateLocation(Location oldLocation, Location newLocation) {
        deleteLocation(oldLocation);
        addLocation(newLocation);
    }
    
    public void deleteLocation(Location oldLocation) {
        for (int i = 0; i < locationDetails.size(); i++) {
            Location location = locationDetails.get(i);
            String oldLocationId = oldLocation.getLocationId();
            String comparingLocationId = location.getLocationId();
            
            if (oldLocationId == null ? comparingLocationId == null : oldLocationId.equals(comparingLocationId)) {
//                locationDetails.remove(i);
                locationHandler.removeFromFile(location);
                readLocationList();
                parseLocations();
            }
        }
    }
    
    public void parseLocations() {
        locationDetails = (ArrayList<Location>) locationParser.parse(Config.Locations);
    }
   
    public ArrayList<Schedule> schedules() {
        return storedSchedules;
    }
    
    
    public ArrayList<Location> locations() {
        return locationDetails;
    }
    
    public ArrayList<String> locationIds() {
        ArrayList<String> result = new ArrayList<>();
        for (Location location : locationDetails) {
            result.add(location.getLocationId());
        }
        return result;
    }
    
    public ArrayList<Schedule> schedulesForNextXDays() {
        // x = 0: only today
        // x = 1: today, tomorow
        // Create a map from date strings to schedules for quick lookup
        int x = Config.dayLimit;
        HashMap<String, Schedule> scheduleMap = new HashMap<>();
        for (Schedule schedule : this.storedSchedules) {
            scheduleMap.put(schedule.getDay().toString(), schedule);
        }

        LocalDate today = LocalDate.now();
        int count = 0;
        
        ArrayList<Schedule> result = new ArrayList<>();

        while (count <= x) {
            LocalDate currentDay = today.plusDays(count);
            
            int day = currentDay.getDayOfMonth();
            int month = currentDay.getMonthValue();
            int year = currentDay.getYear();
            
            String currentDayString = String.format("%02d/%02d/%d", day, month, year);
            
            Schedule scheduleForDay = scheduleMap.get(currentDayString);
            if (scheduleForDay != null) {
                result.add(scheduleForDay);
            } 
            count++;
        }
        return result;
    }
    
    public void readTicketData() {
        ticketHandler.readFile(Config.pathToTicketData);
    }
    
    public void parseTicketData() {
        ticketStorage = (HashMap<String, HashMap<String, ArrayList<Ticket>>>) ticketParser.parse(Config.TicketData);
    }
    
    public ArrayList<Ticket> getEventSeatData(String eventId, String date, String sessionTime, String locationId) {
        ArrayList<Ticket> eventTickets = getTicketsArray(date, eventId);
        if (eventTickets == null) {
            return new ArrayList<>();
        }
        ArrayList<Ticket> filteredTickets = new ArrayList<>();
        
        for (Ticket ticket : eventTickets) {
            String ticketLocation = ticket.locationId().trim();
            String ticketSession = ticket.session().trim();

            if (ticketLocation.equals(locationId.trim()) && ticketSession.equals(sessionTime.trim())) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }
    
    public ArrayList<Integer> getLocationSeatSize(String locationId) {
        ArrayList<Integer> sizes = new ArrayList<>();
        for (Location location : locationDetails) {
            if (location.getLocationId().trim().equals(locationId.trim())) {
                sizes.add(location.getRowCount());
                sizes.add(location.getSeatsPerRow());

            }
        }
        
        return sizes;
    }
    
    public ArrayList<Ticket> getTicketsArray(String date, String eventID) {
        HashMap<String, ArrayList<Ticket>> scheduleTickets = ticketStorage.get(date);
        if (scheduleTickets != null) {
            return scheduleTickets.get(eventID);
        } else {
            return null;
        }
        
    }
    
    public void addTicket(String name, String phone, String date, String eventId, String locationId, String session, String price, ArrayList<Tuple<String, String>> seats) {
        ArrayList<Ticket> eventTickets = getTicketsArray(date, eventId);
        Ticket newTicket = new Ticket(name, phone, date, eventId, session, locationId, Float.valueOf(price));
        for (Tuple seat : seats) {
            newTicket.addSeat(Integer.valueOf((String) seat.x), Integer.valueOf((String) seat.y));
        }
        
        if (eventTickets != null) {
            eventTickets.add(newTicket);
        } else {
            eventTickets = new ArrayList<>();
            eventTickets.add(newTicket);
            
            HashMap<String, ArrayList<Ticket>> TicketsDateContainer = ticketStorage.get(date);
            if (TicketsDateContainer != null) {
                TicketsDateContainer.put(eventId, eventTickets);
            } else {
                TicketsDateContainer = new HashMap<>();
                TicketsDateContainer.put(eventId, eventTickets);
                ticketStorage.put(date, TicketsDateContainer);
            }
        }
        
        ticketHandler.addToFile(newTicket);
    }
      
}
