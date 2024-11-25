/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.ViewModel;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


class Body {
    private ViewModel vm;
    
    private JPanel mainBody;
    private int currentPage = 1;
    private int maxPage = 0;
    
    
    public Body() {
        vm = new ViewModel();
        mainBody = new JPanel();
    }
    
    public JPanel body() {
        return mainBody;
    }
    
}
