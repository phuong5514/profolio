/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

public class User {
    private String _name;
    private String _phoneNumber;
    
    public User() {
        _name = "";
        _phoneNumber = "";
    }
    
    public User(String name, String phoneNumber) {
        _name  = name;
        _phoneNumber = phoneNumber;
    }
    
    public void setName(String name) {
        _name  = name;
    }
    
    public void setPhoneNumber(String number) {
        _phoneNumber = number;
    }
    
    public String name() {
        return _name;
    }
    
    public String phoneNumber() {
        return _phoneNumber;
    }
    

}
