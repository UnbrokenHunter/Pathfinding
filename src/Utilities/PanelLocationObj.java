package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.JPanel;

public class PanelLocationObj extends JPanel implements Runnable {

	private Thread thread;

	public PanelLocationObj() {
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}

	protected void StartThread() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / 60;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (thread != null) {

			repaint();

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);

				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		DrawBox(g);
	}

	private void DrawBox(Graphics g) {

		g.setColor(Color.white);

		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();

		g.drawRect(x, y, 90, 30);
		g.drawString("x: " + x, x + 5, y + 20);
		g.drawString("y: " + y, x + 40, y + 20);

	}

}
