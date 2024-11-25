
package UI;

import javax.swing.*;
import java.awt.*;

public class HorizontalContainer extends JPanel {

    public HorizontalContainer() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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