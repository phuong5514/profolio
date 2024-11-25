/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package bll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.Body;

public class BodyHandler implements ActionListener{
    private static Body mainBody;
    public BodyHandler(Body value) {
        mainBody = value;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "NEXT":
                mainBody.nextPage();
                break;
                
            case "PREV":
                mainBody.prevPage();
                break;
        }
    }

    
}
