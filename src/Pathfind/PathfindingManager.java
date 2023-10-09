package Pathfind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Cells.CellManager;
import Driver.Config;
import Utilities.Looper;

public class PathfindingManager {

	public static PathfindingManager Instance;

	public PathfindingManager() {

		if (Instance != null) {
			System.out.println("ERROR - THERE IS MORE THAN ONE PATHFINDING MANAGER");
		} else {
			Instance = this;
		}
	}

	public void startPathfinder() {
		Config.getAlgorithm().steps = 0;

		Config.getAlgorithm().currentLayer
				.add(CellManager.Instance.GetCellByIndex(CellManager.Instance.GetStartIndex()));
	}

	public void pathfind() {

		if (Config.getAlgorithm().HandleActionTime()) {

			Config.getAlgorithm().Pathfind();
		}
	}

	public void drawDebug(Graphics g) {

		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.PLAIN, 26));

		g.drawString("Time: " + Looper.GetTime(), 0, 20);
		g.drawString("Steps: " + Config.getAlgorithm().steps, 0, 40);

	}

}
