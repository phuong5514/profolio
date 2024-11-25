/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Entity.Date;
import Entity.Event;
import Entity.Ticket;
import Helpers.Tuple;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public class TicketInfosTab  extends Tab{
    private Date _date;
    private Event _event;
    
    private GridContainerWithFooter _optionsContainer;
    private JComboBox<String> sessionsListCB;
    private JComboBox<String> locationIdsCB;
    
    private GridContainerWithFooter _seatsContainer;
    
    public TicketInfosTab(Date date, Event event) {
        super("Seat infomation");
        _date = date;
        _event = event;
        
        _optionsContainer = new GridContainerWithFooter(2, 2, 4, 4);
        _optionsContainer.setBorder(margin);
        
        JLabel sessionLabel = new JLabel("session");
        sessionsListCB = new JComboBox<>();
        _optionsContainer.addToGrid(sessionLabel);
        _optionsContainer.addToGrid(sessionsListCB);
        
        JLabel locationLabel = new JLabel("location Id");
        locationIdsCB = new JComboBox<>();
        _optionsContainer.addToGrid(locationLabel);
        _optionsContainer.addToGrid(locationIdsCB);
        
        JButton loadSeatsButton = new JButton("confirm");
        loadSeatsButton.addActionListener((ActionEvent e) -> {
            loadSeat();
        });
        
        _optionsContainer.setFooter(loadSeatsButton);
        loadOptions();        
        
        super.addToTab(_optionsContainer, BorderLayout.CENTER);
    }
    
    private void loadOptions() {
        for (int i = 0; i < _event.sessionCount(); i++) {
            sessionsListCB.addItem(_event.getSession(i));
        }

        ArrayList<String> locations = _event.locationIds();
        for (String location : locations) {
            locationIdsCB.addItem(location);
        }
    }

    private void loadSeat() {
        String selectedSession = ((String) sessionsListCB.getSelectedItem()).trim();
        String selectedLocation = ((String) locationIdsCB.getSelectedItem()).trim();

        ArrayList<Ticket> tickets = super.vm.getEventSeatData(_event.eventId(), _date.toString(), selectedSession, selectedLocation);
        ArrayList<Integer> locationSize = super.vm.getLocationSeatSize(selectedLocation);
        int rowCount = locationSize.get(0);
        int colCount = locationSize.get(1);
        int total  =  rowCount * colCount;
        
        _seatsContainer = new GridContainerWithFooter(rowCount, colCount, 4, 4);
        _seatsContainer.setBorder(margin);
        super.addToTab(_seatsContainer, BorderLayout.PAGE_END);
        
        ArrayList<Ticket.Seat> bookedSeats = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ArrayList<Ticket.Seat> seats = ticket.seats();
            for (Ticket.Seat s : seats) {
                bookedSeats.add(s);
            }
        }
        
        int bookedSeatsCount = bookedSeats.size();
        int availableSeats = total - bookedSeatsCount;
        
        JPanel footer = new JPanel();
        JLabel totalLabel = new JLabel("total seats: " + total);
        JLabel bookedSeatsCountLabel = new JLabel("booked seats: " + bookedSeatsCount);
        JLabel availableSeatsCount = new JLabel("available seats: " + availableSeats);
        
        footer.add(totalLabel);
        footer.add(bookedSeatsCountLabel);
        footer.add(availableSeatsCount);
        
        _seatsContainer.setFooter(footer);
        
        
        for (int i = 0; i < total; i++) {
            JButton seatButton = new JButton("Seat " + (i + 1));

            boolean isBooked = false;

            int row = i / colCount + 1;
            int col = i % colCount + 1;


            for (Ticket ticket : tickets) {
                for (Ticket.Seat seat : ticket.seats()) {
                    if (seat.row() == row && seat.column() == col) {
                        isBooked = true;
                        seatButton.setToolTipText("Người đặt vé: " + ticket.customerName() + " ; Số điện thoại: " + ticket.customerPhone());
                    }
                }
            }

            if (!isBooked) {
                seatButton.setEnabled(false);
            }

            _seatsContainer.addToGrid(seatButton);
        }
        
    }
    
    
    
}
