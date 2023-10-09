package Keys;

import java.awt.event.KeyEvent;

public interface KeyListener {
    void onKeyPress(KeyEvent e);

    void onKeyRelease(KeyEvent e);
}
