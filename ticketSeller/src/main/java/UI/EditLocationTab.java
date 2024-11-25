/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.ViewModel;
import Entity.Location;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EditLocationTab extends Tab {
    private JPanel contentContainer;
    private ScrollableVerticalContainer locationsList;
    private JPanel editLocationContainer;
    
    private JTextField locationNameField;
    private JTextField locationIdField;
    private JTextField rowCountField;
    private JTextField seatsPerRowField;
    
    private JButton saveButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton cancelButton;

    private Location storedLocation;

    public EditLocationTab(ViewModel vm) {
        super("edit location");

        contentContainer = new JPanel();
        contentContainer.setLayout(new BorderLayout());

        JPanel locationsContainer = new JPanel();
        locationsContainer.setBorder(super.border);
        locationsList = new ScrollableVerticalContainer();
//        locationsList.setBorder(super.margin);
        
        locationsContainer.add(locationsList.createScrollPane(100, 200));

        int rows = 5;
        int cols = 2;
        int hgap = 10;
        int vgap = 10;
        JPanel editLocationContainerBorder = new JPanel();
        editLocationContainerBorder.setBorder(border);
        
        editLocationContainer = new JPanel(new GridLayout(rows, cols, hgap, vgap));
        editLocationContainer.setBorder(super.margin);
        editLocationContainerBorder.add(editLocationContainer);

        editLocationContainer.add(new JLabel("Id:"));
        locationIdField = new JTextField();
        editLocationContainer.add(locationIdField);

        editLocationContainer.add(new JLabel("Name:"));
        locationNameField = new JTextField();
        editLocationContainer.add(locationNameField);

        editLocationContainer.add(new JLabel("Rows:"));
        rowCountField = new JTextField();
        editLocationContainer.add(rowCountField);

        editLocationContainer.add(new JLabel("Seats per row:"));
        seatsPerRowField = new JTextField();
        editLocationContainer.add(seatsPerRowField);

        JPanel footer = new JPanel();
        footer.setBackground(Color.white);
        
        
        addButton = new JButton("Add");
        addButton.addActionListener((ActionEvent e) -> addLocation(vm));
//        addButton.setBorder(super.margin);

        saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) -> saveLocation(vm));
//        saveButton.setBorder(super.margin);
        
        removeButton = new JButton("Delete");
        removeButton.addActionListener((ActionEvent e) -> removeLocation(vm));
//        removeButton.setBorder(super.margin);
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> loadEmptyLocationDetails());
//        cancelButton.setBorder(super.margin);
        
        footer.add(addButton);
        footer.add(saveButton);
        footer.add(removeButton);
        footer.add(cancelButton);
        
        contentContainer.add(footer, BorderLayout.SOUTH);

        contentContainer.add(locationsContainer, BorderLayout.WEST);
        contentContainer.add(editLocationContainerBorder, BorderLayout.CENTER);

        super.mainContainer.add(contentContainer);
        
    }

    public void loadLocationList(ViewModel vm) {
        locationsList.removeAll();
        ArrayList<Location> list = vm.locations();
        for (Location location : list) {
            JPanel listItem = new JPanel();
            listItem.setBorder(BorderFactory.createLineBorder(Color.gray));
            listItem.setLayout(new BorderLayout());
            listItem.setMaximumSize(new Dimension(100, 25));
            
            String id = location.getLocationId();
            JButton locationButton = new JButton(id);
            locationButton.setPreferredSize(new Dimension(50, 25));
            locationButton.addActionListener((ActionEvent e) -> loadLocationDetails(location));
            
            listItem.add(locationButton);
            locationsList.addComponent(listItem);
        }

        loadEmptyLocationDetails();
    }

    private void loadEmptyLocationDetails() {
        locationIdField.setText("");
        locationNameField.setText("");
        rowCountField.setText("");
        seatsPerRowField.setText("");
        storedLocation = null;
    }

    private void loadLocationDetails(Location location) {
        locationIdField.setText(location.getLocationId());
        locationNameField.setText(location.getLocationName());
        rowCountField.setText(String.valueOf(location.getRowCount()));
        seatsPerRowField.setText(String.valueOf(location.getSeatsPerRow()));
        storedLocation = location;
    }

    private void addLocation(ViewModel vm) {
        if (isInputValid()) {
            Location newLocation = new Location();
            newLocation.setLocationId(locationIdField.getText());
            newLocation.setLocationName(locationNameField.getText());
            newLocation.setRowCount(Integer.parseInt(rowCountField.getText()));
            newLocation.setSeatsPerRow(Integer.parseInt(seatsPerRowField.getText()));
            
            for (Location l : vm.locations()) {
                if (l.getLocationId() == null ? newLocation.getLocationId() == null : l.getLocationId().equals(newLocation.getLocationId())) {
                    super.errorPopup("id have already existed!", "invalid input");
                    return;
                }
            }

            // Here you would add the new location to your ViewModel
            vm.addLocation(newLocation);

            loadEmptyLocationDetails();
            // Reload the list of locations after adding a new one
            loadLocationList(vm);
        }
    }

    private void saveLocation(ViewModel vm) {
        if (storedLocation != null && isInputValid()) {
            Location newLocation = new Location();
            newLocation.setLocationId(locationIdField.getText());
            newLocation.setLocationName(locationNameField.getText());
            newLocation.setRowCount(Integer.parseInt(rowCountField.getText()));
            newLocation.setSeatsPerRow(Integer.parseInt(seatsPerRowField.getText()));

            // Here you would update the existing location in your ViewModel
            vm.updateLocation(storedLocation, newLocation);

            loadEmptyLocationDetails();
            // Reload the list of locations after saving the changes
            loadLocationList(vm);
        }
    }
    
    private void removeLocation(ViewModel vm) {
        if (storedLocation != null) {
            vm.deleteLocation(storedLocation);
            loadEmptyLocationDetails();
            loadLocationList(vm);
        }
    }

    private boolean isInputValid() {
        if (locationIdField.getText().isEmpty() ||
            locationNameField.getText().isEmpty() ||
            rowCountField.getText().isEmpty() ||
            seatsPerRowField.getText().isEmpty()) {
            super.errorPopup("All fields must be filled out", "Empty Input");
            
            return false;
        }
        try {
            Integer.valueOf(rowCountField.getText());
            Integer.valueOf(seatsPerRowField.getText());
        } catch (NumberFormatException e) {
            super.errorPopup("invalid seat arrangement", "Invalid input");
            return false;
        }
        return true;
    }
}
