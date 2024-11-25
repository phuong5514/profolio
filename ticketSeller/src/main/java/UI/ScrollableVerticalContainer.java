
package UI;

import javax.swing.*;
import java.awt.*;

public class ScrollableVerticalContainer extends JPanel {
    public ScrollableVerticalContainer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }
    
    public JScrollPane createScrollPane(int width, int height) {
        JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(width, height)); // Adjust the size as needed
        return scrollPane;
    }

    public void addComponent(Component component) {
        add(component);
        revalidate();
        repaint();
    }
}