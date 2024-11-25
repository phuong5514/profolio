package UI;

import Business.ViewModel;
import Entity.Location;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import Entity.Event;
import Entity.Schedule;
import Entity.Time;

public class EventEditor extends Tab {
    private JPanel contentBox;
    private GridContainerWithFooter generalLayout;
    
    private JTextField eventNameField;
    private JTextField eventIdField;
    
    private GridContainerWithFooter sessionList;
    private ArrayList<JTextField> sessionFromHourField;
    private ArrayList<JTextField> sessionFromMinField;
    private ArrayList<JTextField> sessionToHourField;
    private ArrayList<JTextField> sessionToMinField;

    private GridContainerWithFooter locationIdList;
    private ArrayList<JComboBox<String>> locationIds;
    private ArrayList<JTextField> pricePerLocation;
    
   
    
    private ViewModel _vm;
    
    private Schedule currentSchedule;
    private Event currentEvent;

    public EventEditor(ViewModel vm, Event event, Schedule schedule) {
        super("Tùy chỉnh vé");
        
        _vm = vm;
        
        currentEvent = event;
        currentSchedule = schedule;

        generalLayout = new GridContainerWithFooter(4, 2, 10, 10);  
        sessionList = new GridContainerWithFooter(0, 5, 10, 10);
        locationIdList = new GridContainerWithFooter(0, 3, 10, 10);

        
        sessionFromHourField = new ArrayList<>();
        sessionFromMinField = new ArrayList<>();
        sessionToHourField = new ArrayList<>();
        sessionToMinField = new ArrayList<>();
        locationIds = new ArrayList<>();
        pricePerLocation = new ArrayList<>();

        setUpGeneralLayout();
        setUpSessionList();
        setUpLocationIdList(vm);
        
        contentBox = new JPanel(new BorderLayout());
        contentBox.setBorder(super.margin);
        
        contentBox.add(generalLayout, BorderLayout.NORTH);
        contentBox.add(sessionList, BorderLayout.CENTER);
        contentBox.add(locationIdList, BorderLayout.SOUTH);
        super.addToTab(contentBox, BorderLayout.CENTER);
        
        JButton saveButton = new JButton("Lưu");
//        saveButton.setBorder(super.rightMargin);
        
        JButton deleteButton = new JButton("Xóa");
//        deleteButton.setBorder(super.rightMargin);
        
        super.addToHeader(saveButton);
        super.addToHeader(deleteButton);
        
        
        
        saveButton.addActionListener((ActionEvent e) -> {
            String eventName = eventNameField.getText().trim();
            String eventID = eventIdField.getText().trim();
            if (eventName.equals("") || eventID.equals("") || (schedule.isEventExist(eventID) && !currentEvent.eventId().equals(eventID) )) {
                super.errorPopup("event name and/or id is missing, or id have already existed!", "missing parameter(s) or invalid input");
                return;
            }
            
            
            int sessionCount = sessionFromHourField.size();
            int locationCount = locationIds.size();
            if (sessionCount <= 0 || locationCount <= 0) {
                super.errorPopup("no session or locaion selected for event!", "missing input");
                return;
            }
            
            
            Event newEvent = new Event(eventName, eventID);
            for (int i = 0; i < sessionFromHourField.size(); i++) {
                String hfString = sessionFromHourField.get(i).getText().trim();
                String mfString = sessionFromMinField.get(i).getText().trim();
                
                String htString = sessionToHourField.get(i).getText().trim();
                String mtString = sessionToMinField.get(i).getText().trim();
                
                if (hfString.equals("") || mfString.equals("") || htString.equals("") || mtString.equals("")) {
                    super.errorPopup("missing session parameter(s)", "missing parameter(s)");
                    return;
                }
                
                int hf;
                int mf;
                int ht;
                int mt;
                        
                try {
                    hf = Integer.parseInt(sessionFromHourField.get(i).getText());
                    mf = Integer.parseInt(sessionFromMinField.get(i).getText());

                    ht = Integer.parseInt(sessionToHourField.get(i).getText());
                    mt = Integer.parseInt(sessionToMinField.get(i).getText());
                } catch (NumberFormatException exception) {
                    super.errorPopup("error parsing", "invalid input");
                    return;   
                }
                
                
                if (hf >= 24 || hf < 0 || ht > 24 || ht < 0 || mf >= 60 || mf < 0 || mt >= 60 || mt < 0) {
                    super.errorPopup("invalid time", "invalid input");
                    return;
                }
                
                
                Time from = new Time(hf, mf);
                Time to = new Time(ht, mt);
                if (from.after(to)) {
                    super.errorPopup("starting time must be before ending time", "logic error");
                    return;     
                }
                
                boolean isNotOverlapped = newEvent.addSession(from, to);
                if (!isNotOverlapped) {
                    super.errorPopup("two session can not be overlap with each other!", "logic error");
                    return; 
                }
            }
            
            for (int i = 0; i < locationIds.size(); i++) {
                String id = (String) locationIds.get(i).getSelectedItem();
                String priceString = pricePerLocation.get(i).getText().trim();
                
                int price;
                try {
                    price = Integer.parseInt(priceString);
                } catch (NumberFormatException exception) {
                    super.errorPopup("error parsing", "invalid input");
                    return;  
                }
                newEvent.addLocation(id, price);
            }
            
            

            vm.updateEvent(schedule, newEvent, event);
            returnToPreviousTab();
        });
        
        deleteButton.addActionListener((ActionEvent e) -> {
            vm.deleteEvent(schedule, event);
            returnToPreviousTab();
        });
        
        
        
    }
    
    
    
    @Override
    protected void returnToPreviousTab() {
        if (this.currentEvent.eventId().isBlank() || this.currentEvent.eventId().isEmpty()) {
            this.currentSchedule.removeEvent(currentEvent);
        }
        ScheduleListTab slt = (ScheduleListTab) super.previousTab;
        slt.loadList(_vm);
        super.returnToPreviousTab();
    }

    private void setUpGeneralLayout() {
        generalLayout.addToGrid(new JLabel("Event Name:"));
        eventNameField = new JTextField(currentEvent.eventName());
        generalLayout.addToGrid(eventNameField);

        generalLayout.addToGrid(new JLabel("Event ID:"));
        eventIdField = new JTextField(currentEvent.eventId());
        generalLayout.addToGrid(eventIdField);
    }

    private void setUpSessionList() {
        sessionList.addToGrid(new JLabel("From (Hour):"));
        sessionList.addToGrid(new JLabel("From (Min):"));
        sessionList.addToGrid(new JLabel("To (Hour):"));
        sessionList.addToGrid(new JLabel("To (Min):"));
        sessionList.addToGrid(new JLabel("Remove"));

        JButton addSessionButton = new JButton("Add Session");
        addSessionButton.addActionListener((ActionEvent e) -> addSessionRow());
        
        ArrayList<Time> begins = currentEvent.sessionBegins();
        ArrayList<Time> ends = currentEvent.sessionEnds();
        for (int i = 0; i < currentEvent.sessionCount(); i++) {
            addSessionRow(begins.get(i), ends.get(i));
        }

        sessionList.setFooter(addSessionButton);
    }

    private void addSessionRow() {
        JTextField fromHourField = new JTextField(2);
        JTextField fromMinField = new JTextField(2);
        JTextField toHourField = new JTextField(2);
        JTextField toMinField = new JTextField(2);

        sessionFromHourField.add(fromHourField);
        sessionFromMinField.add(fromMinField);
        sessionToHourField.add(toHourField);
        sessionToMinField.add(toMinField);

        sessionList.addToGrid(fromHourField);
        sessionList.addToGrid(fromMinField);
        sessionList.addToGrid(toHourField);
        sessionList.addToGrid(toMinField);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener((ActionEvent e) -> {
            int index = sessionFromHourField.indexOf(fromHourField);
            sessionFromHourField.remove(index);
            sessionFromMinField.remove(index);
            sessionToHourField.remove(index);
            sessionToMinField.remove(index);

            sessionList.removeRow(index + 1); // +1 to account for header row
        });

        sessionList.addToGrid(removeButton);
    }
    
    private void addSessionRow(Time from, Time to) {
        JTextField fromHourField = new JTextField(String.valueOf(from.hour()));
        JTextField fromMinField = new JTextField(String.valueOf(from.minute()));
        JTextField toHourField = new JTextField(String.valueOf(to.hour()));
        JTextField toMinField = new JTextField(String.valueOf(to.minute()));

        sessionFromHourField.add(fromHourField);
        sessionFromMinField.add(fromMinField);
        sessionToHourField.add(toHourField);
        sessionToMinField.add(toMinField);

        sessionList.addToGrid(fromHourField);
        sessionList.addToGrid(fromMinField);
        sessionList.addToGrid(toHourField);
        sessionList.addToGrid(toMinField);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener((ActionEvent e) -> {
            int index = sessionFromHourField.indexOf(fromHourField);
            sessionFromHourField.remove(index);
            sessionFromMinField.remove(index);
            sessionToHourField.remove(index);
            sessionToMinField.remove(index);

            sessionList.removeRow(index + 1); // +1 to account for header row
        });

        sessionList.addToGrid(removeButton);
    }

    private void setUpLocationIdList(ViewModel vm) {
        locationIdList.addToGrid(new JLabel("Location ID:"));
        locationIdList.addToGrid(new JLabel("Price per Location:"));
        locationIdList.addToGrid(new JLabel("Add/Remove"));

        JButton addLocationButton = new JButton("Add Location");
        addLocationButton.addActionListener((ActionEvent e) -> addLocationRow(vm));
        
        ArrayList<String> locationIds = currentEvent.locationIds();
        ArrayList<Integer> pricePerLocations = currentEvent.priceList();
        for (int i = 0; i < locationIds.size(); i++) {
            String LID = locationIds.get(i);
            int price = pricePerLocations.get(i);
            addLocationRow(vm, LID, price);
        }

        locationIdList.setFooter(addLocationButton);
    }

    private void addLocationRow(ViewModel vm) {
        ArrayList<Location> locations = vm.locations();
        ArrayList<String> ids = new ArrayList<>();
        for (Location location : locations) {
            //locationIds
            ids.add(location.getLocationId());
        }
        
        
        String[] idArr = ids.toArray(new String[0]);
        
        JComboBox<String> locationIdComboBox = new JComboBox<>(idArr);
        JTextField priceField = new JTextField(5);

        locationIds.add(locationIdComboBox);
        pricePerLocation.add(priceField);

        locationIdList.addToGrid(locationIdComboBox);
        locationIdList.addToGrid(priceField);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener((ActionEvent e) -> {
            int index = locationIds.indexOf(locationIdComboBox);
            locationIds.remove(index);
            pricePerLocation.remove(index);

            locationIdList.removeRow(index + 1); // +1 to account for header row
        });

        locationIdList.addToGrid(removeButton);
    }
    
    private void addLocationRow(ViewModel vm, String id, int price) {
        ArrayList<Location> locations = vm.locations();
        ArrayList<String> ids = new ArrayList<>();
        for (Location location : locations) {
            ids.add(location.getLocationId());
        }
        
        String[] idArr = ids.toArray(new String[0]);
        
        JComboBox<String> locationIdComboBox = new JComboBox<>(idArr);
        locationIdComboBox.setSelectedItem(id);
        
        JTextField priceField = new JTextField(String.valueOf(price));

        locationIds.add(locationIdComboBox);
        pricePerLocation.add(priceField);

        locationIdList.addToGrid(locationIdComboBox);
        locationIdList.addToGrid(priceField);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener((ActionEvent e) -> {
            int index = locationIds.indexOf(locationIdComboBox);
            locationIds.remove(index);
            pricePerLocation.remove(index);

            locationIdList.removeRow(index + 1); // +1 to account for header row
        });

        locationIdList.addToGrid(removeButton);
    }
    
    public void setTicketInfos(){
        JButton infoButton = new JButton("thông tin vé");
        
        infoButton.addActionListener((ActionEvent e) -> {
            TicketInfosTab infoTab = new TicketInfosTab(currentSchedule.getDay(), currentEvent);
            infoTab.setViewModel(_vm);
            infoTab.setPreviousTab(this, window);
            window.removeAll();
            window.add(infoTab.getContainer());
            window.revalidate();
            window.repaint();
        });
        
        
        super.addToHeader(infoButton);
    }
}
