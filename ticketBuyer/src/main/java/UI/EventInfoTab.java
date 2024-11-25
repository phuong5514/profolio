/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.ViewModel;
import Entity.Date;
import Entity.Event;
import Entity.Location;
import Entity.Schedule;
import Entity.Ticket;
import Entity.Time;
import Helper.Tuple;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EventInfoTab extends Tab {
    private GridContainerWithFooter generalLayout;

    private JLabel eventName;
    private JComboBox<String> sessionsListCB;
    private JComboBox<String> locationIdsCB;

    private GridContainerWithFooter seatSelector;

    private Event currentEvent;
    private Date eventDate;
    
    private int seatsInCart = 0;
    private int pricePerSeat = 0;
    private JLabel totalPriceDisplay;

    public EventInfoTab(Event event, Date date, ViewModel vm) {
        super("Order ticket");

        currentEvent = event;
        eventDate = date;
        super.setViewModel(vm);

        generalLayout = new GridContainerWithFooter(4, 2, 4, 4);
        generalLayout.setBorder(margin);

        eventName = new JLabel(event.eventName());
        generalLayout.addToGrid(new JLabel("Tên sự kiện:"));
        generalLayout.addToGrid(eventName);


        generalLayout.addToGrid(new JLabel("Buổi:"));
        sessionsListCB = new JComboBox<>();
        generalLayout.addToGrid(sessionsListCB);

        generalLayout.addToGrid(new JLabel("Địa điểm:"));
        locationIdsCB = new JComboBox<>();
        generalLayout.addToGrid(locationIdsCB);

        JButton loadSeatsButton = new JButton("Chọn chỗ ngồi");
        loadSeatsButton.addActionListener((ActionEvent e) -> loadSeats());
        generalLayout.addToGrid(loadSeatsButton);

        
        
        super.addToTab(generalLayout, BorderLayout.CENTER);

        loadOptions();
    }

    private void loadOptions() {
        // Populate the session combo box
        for (int i = 0; i < currentEvent.sessionCount(); i++) {
            sessionsListCB.addItem(currentEvent.getSession(i));
        }

        // Populate the location combo box
        ArrayList<String> locations = currentEvent.locationIds();
        for (String location : locations) {
            locationIdsCB.addItem(location);
        }
    }

    private void loadSeats() {
        ArrayList<Ticket> tickets;

        ArrayList<Tuple<Integer, Integer>> chosenSeats = new ArrayList<>();
        
        int rowCount = 0;
        int colCount = 0;
        int total = 0;
        
        String selectedSession = ((String) sessionsListCB.getSelectedItem()).trim();
        String selectedLocation = ((String) locationIdsCB.getSelectedItem()).trim();
        
        
        
        for (int i = 0; i < currentEvent.locationIds().size(); i++) {
            String id = currentEvent.locationIds().get(i).trim();
            if (selectedLocation.equals(id)) {
                pricePerSeat = currentEvent.priceOfLocation(i);
            }
        }
        
        
        try {
            tickets = vm.sendLocationSeatStatesRequest(eventDate, currentEvent.eventId(), selectedSession , selectedLocation);
            ArrayList<Integer> seatSizes = vm.sendLocationSeatSizeRequest((String) locationIdsCB.getSelectedItem());
            System.out.println("Tickets:");
            System.out.println(tickets.size());
            rowCount = seatSizes.get(0);
            colCount = seatSizes.get(1);
            total = rowCount * colCount;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EventInfoTab.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        
        


        if (selectedSession != null && selectedLocation != null) {
            

            if (seatSelector != null) {
                generalLayout.remove(seatSelector);
            }
            

            JPanel footerContainer = new JPanel();
            totalPriceDisplay = new JLabel("0");
            totalPriceDisplay.setBorder(super.rightMargin);
            JButton purchaseButton = new JButton("Đặt vé");
            
            footerContainer.add(totalPriceDisplay);
            footerContainer.add(purchaseButton);
            
            seatSelector = new GridContainerWithFooter(rowCount, colCount, 4, 4);
            for (int i = 0; i < total; i++) { 
                JButton seatButton = new JButton("Seat " + (i + 1));
                // limit size
                boolean isBooked = false;
                
                int row = i / colCount + 1;
                int col = i % colCount + 1;
//                System.out.println("CurrentSeat");
//                System.out.println(i);
//                System.out.println(row);
//                System.out.println(col);
                
                for (Ticket ticket : tickets) {
                    for (Ticket.Seat seat : ticket.seats()) {
//                        System.out.println("Banned:");
//                        System.out.println(seat.row());
//                        System.out.println(seat.column());
                        if (seat.row() == row && seat.column() == col) {
                            isBooked = true;
                        }
                    }
                }
                
                if (!isBooked) {
                    Tuple<Integer, Integer> seat = new Tuple(row, col);
                    seatButton.addActionListener((ActionEvent e) -> {
                        if (seatButton.getBackground() != Color.GREEN) {
                            seatButton.setBackground(Color.GREEN);
                            chosenSeats.add(seat);
                            seatsInCart++;
                            
                        } else {
                            seatButton.setBackground(null);
                            seatsInCart--;
                            chosenSeats.remove(seat);
                        }
                        totalPriceDisplay.setText(String.valueOf(seatsInCart * pricePerSeat));
                    });
                } else {
                    seatButton.setEnabled(false);
                }

                seatSelector.addToGrid(seatButton);
            }
            
            
            purchaseButton.addActionListener((ActionEvent e) -> {
                if (seatsInCart <= 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "No seats selected. Ticket booking unsuccessful.",
                            "Booking Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                Ticket generatedTicket = new Ticket();
                generatedTicket.setCustomerName(vm.getUsername());
                generatedTicket.setCustomerPhone(vm.getPhoneNumber());
                generatedTicket.setDateString(eventDate.toString());
                generatedTicket.setEventId(this.currentEvent.eventId());
                generatedTicket.setLocationId((String) locationIdsCB.getSelectedItem());
                generatedTicket.setSession((String) sessionsListCB.getSelectedItem());
                generatedTicket.setTicketPrice(seatsInCart * pricePerSeat);
                ArrayList<Ticket.Seat> seats = new ArrayList<>();
                for (Tuple t : chosenSeats) {
                    seats.add(new Ticket.Seat((Integer) t.x, (Integer) t.y));
                }
                generatedTicket.setSeats(seats);
                try {
                    if (vm.sendBuyTicketRequest(generatedTicket)) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Tickets successfully booked!",
                            "Booking Success",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        super.returnToPreviousTab();
                    } else {
                        JOptionPane.showMessageDialog(
                            null,
                            "Ticket booking unsuccessful.",
                            "Booking Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    };
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(EventInfoTab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }); 
            
            seatSelector.setFooter(footerContainer);
            generalLayout.setFooter(seatSelector);
        }
    }
}

