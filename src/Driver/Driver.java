package Driver;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.Timer;

import Cells.CellManager;
import Click.ClickManager;
import Keys.KeyManager;

public class Driver {

	private static JFrame frame;

	public static void main(String[] args) {

		Driver driver = new Driver();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Allows you to close the tab
		frame.setResizable(true);
		frame.setTitle("Pathfinding"); // Title of window

		// Add dynamic resize listener
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				driver.handleResizeDebounced();
			}
		});

		// Create Click Manager
		new ClickManager(frame);

		// Create Click Manager
		new KeyManager(frame);

		Window window = new Window();
		frame.add(window);

		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private Timer resizeTimer;
	private final int DEBOUNCE_DELAY = 200; // 200 milliseconds

	private void handleResizeDebounced() {
		if (resizeTimer != null && resizeTimer.isRunning()) {
			resizeTimer.restart();
		} else {
			resizeTimer = new Timer(DEBOUNCE_DELAY, e -> onResized());
			resizeTimer.setRepeats(false); // Make sure the timer only runs once and doesn't repeat
			resizeTimer.start();
		}
	}

	private void onResized() {
		Dimension newSize = frame.getSize();
		Config.setScreenWidth((int) newSize.getWidth());
		Config.setScreenHeight((int) newSize.getHeight());

		CellManager.Instance.updateScreenPosition();
	}

}
