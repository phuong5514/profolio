/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UI;

import Business.ViewModel;
import Entity.Date;
import Entity.Event;
import Entity.Schedule;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ScheduleListTab extends Tab {
    private int loadingLimit;
    private ArrayList<DayEventList> lists;
    private HorizontalContainer schedulesContainer;
    private JPanel parentContainer;

    public ScheduleListTab(int limit, JPanel parent) {
        super("Schedules");
        loadingLimit = limit;
        lists = new ArrayList<>();
        
        schedulesContainer = new HorizontalContainer();
        schedulesContainer.setBorder(super.margin);
        
        parentContainer = parent;
        super.addToTab(schedulesContainer, BorderLayout.CENTER);
      
    }
    
    public void setReload(ViewModel vm) {
        JButton reloadButton = new JButton("đọc lại tất cả dữ liệu");
        reloadButton.addActionListener((ActionEvent e) -> {
            vm.loadData();
            loadList(vm);
        });
        super.addToHeader(reloadButton);
    }

    public void initSchedule(ViewModel vm) {
        loadList(vm);
    }

    public void loadList(ViewModel vm) {
        ArrayList<Schedule> scheduleList = vm.schedules();
        
        schedulesContainer.removeAll();
        lists.clear();

        // Create a map from date strings to schedules for quick lookup
        HashMap<String, Schedule> scheduleMap = new HashMap<>();
        for (Schedule schedule : scheduleList) {
            scheduleMap.put(schedule.getDay().toString(), schedule);
        }

        LocalDate today = LocalDate.now();
        int count = 0;

        while (count < loadingLimit) {
            LocalDate currentDay = today.plusDays(count);
            
            int day = currentDay.getDayOfMonth();
            int month = currentDay.getMonthValue();
            int year = currentDay.getYear();
            
            String currentDayString = String.format("%02d/%02d/%d", day, month, year);
            
            Schedule scheduleForDay = scheduleMap.get(currentDayString);
            DayEventList dayEventList;
            if (scheduleForDay != null) {
                dayEventList = new DayEventList(scheduleForDay, vm, this, parentContainer);
            } else {
                Schedule newSchedule = new Schedule();
                newSchedule.setDay(day, month, year);
                
                vm.addSchedule(newSchedule);
                dayEventList = new DayEventList(newSchedule, vm, this, parentContainer);
            }

            lists.add(dayEventList);
            schedulesContainer.addComponent(dayEventList.getContainer());

            count++;
        }
    }
}

class DayEventList {
    private JPanel dayHeader;
    private ScrollableVerticalContainer eventListContainer;
    private JButton addEventButton;
    private JPanel container;

    public DayEventList(Schedule schedule, ViewModel vm, Tab currentTab, JPanel panel) {
        container = new JPanel();
        container.setLayout(new BorderLayout());
        

        dayHeader = new JPanel();
        dayHeader.add(new JLabel(schedule.getDay().toString()));
        container.add(dayHeader, BorderLayout.NORTH);

        eventListContainer = new ScrollableVerticalContainer();
        for (Event event : schedule.getEventList()) {
            // them cac nut event va dieu chinh kich thuoc
            JPanel listItem = new JPanel();
            listItem.setBorder(BorderFactory.createLineBorder(Color.gray));
            listItem.setLayout(new BorderLayout());
            listItem.setMaximumSize(new Dimension(150, 40));
            JButton eventButton = new JButton(event.eventName());
            listItem.add(eventButton, BorderLayout.CENTER);
            eventButton.setPreferredSize(new Dimension(50, 40));

            
            eventButton.addActionListener((ActionEvent e) -> {
                EventEditor newEditor = new EventEditor(vm, event, schedule);
                newEditor.setPreviousTab(currentTab, panel);
                newEditor.setTicketInfos();
                panel.removeAll();
                panel.add(newEditor.getContainer());
                panel.revalidate();
                panel.repaint();
            });
            
            JButton removeButton = new JButton("X");
            removeButton.addActionListener((ActionEvent e) -> {
                schedule.removeEvent(event);
            });
            
//            eventListContainer.addComponent(eventButton);
            eventListContainer.addComponent(listItem);
        }
        container.add(eventListContainer.createScrollPane(150, 400), BorderLayout.CENTER);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener((ActionEvent e) -> {
            Event newEvent = new Event();
            schedule.addEvent(newEvent);
            EventEditor newEditor = new EventEditor(vm, newEvent, schedule);
            newEditor.setPreviousTab(currentTab, panel);
            panel.removeAll();
            panel.add(newEditor.getContainer());
            panel.revalidate();
            panel.repaint();
        });
        container.add(addEventButton, BorderLayout.SOUTH);
        

        
        
    }

    public JPanel getContainer() {
        return container;
    }
    
}
