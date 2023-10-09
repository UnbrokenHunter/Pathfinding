package Click;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class ClickManager {

    public static ClickManager Instance;

    private List<ClickListener> listeners = new ArrayList<>();

    public ClickManager(JFrame frame) {

        if (Instance != null) {
            System.out.println("ERROR - THERE IS MORE THAN ONE CLICK MANAGER");
        } else {
            Instance = this;
        }

        frame.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifyClick(e);
            }
        });
    }

    // Allow listeners to register
    public void addClickListener(ClickListener listener) {
        listeners.add(listener);
    }

    // Notify all registered listeners of a click event
    protected void notifyClick(MouseEvent e) {
        for (ClickListener listener : listeners) {
            listener.onClick(e);
        }
    }

    public void reset() {
        listeners.clear();
    }

}
