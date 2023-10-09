package Keys;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class KeyManager {

    public static KeyManager Instance;

    private List<Keys.KeyListener> listeners = new ArrayList<>();

    public KeyManager(JFrame frame) {

        if (Instance != null) {
            System.out.println("ERROR - THERE IS MORE THAN ONE KEY MANAGER");
        } else {
            Instance = this;
        }

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                notifyKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                notifyKeyRelease(e);
            }
        });
    }

    // Allow listeners to register
    public void addKeyListener(Keys.KeyListener listener) {
        listeners.add(listener);
        System.out.println("Key Input Listener Added: " + listener);
    }

    // Notify all registered listeners of a key press event
    protected void notifyKeyPress(KeyEvent e) {
        for (Keys.KeyListener listener : listeners) {
            listener.onKeyPress(e);
        }
    }

    // Notify all registered listeners of a key release event
    protected void notifyKeyRelease(KeyEvent e) {
        for (Keys.KeyListener listener : listeners) {
            listener.onKeyRelease(e);
        }
    }
}
