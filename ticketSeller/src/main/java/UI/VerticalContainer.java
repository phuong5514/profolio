
package UI;

import javax.swing.*;
import java.awt.*;


public class VerticalContainer extends JPanel{
    public VerticalContainer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void addComponent(Component component) {
        add(component);
        revalidate();
        repaint();
    }

    void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
