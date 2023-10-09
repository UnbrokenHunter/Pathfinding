package Driver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Cells.CellManager;
import Pathfind.PathfindingManager;
import Utilities.Looper;

public class Window extends JPanel {

	public enum ApplicationState {
		MENU,
		PLAY
	}

	private Menu menu;
	private Game game;
	private ApplicationState state = ApplicationState.MENU;

	public Window() {
		initializeSingletons();
		initializeWindow();

		startApp();
	}

	private void initializeSingletons() {
		new CellManager();
		new PathfindingManager();

		menu = new Menu(this);
		game = new Game(this);
	}

	private void initializeWindow() {
		this.setPreferredSize(new Dimension(Config.getScreenWidth(), Config.getScreenHeight()));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}

	private void startApp() {
		switch (state) {
			case MENU:
				System.out.println("Menu");
				break;
			case PLAY:
				game.start();
				break;
		}
		new Looper(this, "loop", Config.FPS);
	}

	public void loop() {
		repaint();
		if (state == ApplicationState.PLAY) {
			game.loop();
		}
	}

	public void startGame() {
		state = ApplicationState.PLAY;
		startApp();
	}

	public void startMenu() {
		state = ApplicationState.MENU;
		menu.show();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch (state) {
			case MENU:
				if (menu != null) {
					menu.drawMenu(g);
				}
				break;
			case PLAY:
				game.render(g);
				break;
		}
	}
}
