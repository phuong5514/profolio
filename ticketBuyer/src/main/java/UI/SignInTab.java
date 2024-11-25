/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Entity.User;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignInTab extends Tab{
    private JTextField nameField;
    private JTextField phoneNumberField;
    private GridContainerWithFooter grid;
    private JButton signInButton;

    public SignInTab() {
        super("Sign in");
        grid = new GridContainerWithFooter(2,2,4,4);
        JLabel nameLabel = new JLabel("Họ tên khách hàng: ");
        JLabel phoneLabel = new JLabel("Số điện thoại: ");
        nameField  = new JTextField();
        phoneNumberField = new JTextField();       
        
        grid.addToGrid(nameLabel);
        grid.addToGrid(nameField);
        grid.addToGrid(phoneLabel);
        grid.addToGrid(phoneNumberField);
        
        signInButton = new JButton("Đăng nhập");
        grid.setFooter(signInButton);
        
        JPanel padding  = new JPanel();
        padding.setBorder(super.margin);
        padding.add(grid);
        super.addToTab(padding, BorderLayout.CENTER);
        
    }
    
    public void setSignIn(Tab home, JPanel window) {
        super.window = window;
        super.homeTab = home;
        signInButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            
            if (name.isBlank() || phoneNumber.isBlank()) {
                super.errorPopup("thiếu thông tin đăng nhập!", "invalid input");
                return;
            }
            
            try {
                int phoneInteger = Integer.parseInt(phoneNumber);
            } catch (NumberFormatException exception) {
                super.errorPopup("Số điện thoại phải là số", "invalid input");
                return;   
            }
         
       
            User newUser = new User(name, phoneNumber);
            
            vm.signIn(newUser);
            
            super.toHome();
        });
    }
    

    
    
    
}
