package Cells;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Click.ClickListener;
import Click.ClickManager;
import Driver.Config;
import Utilities.ColorInterpolator;
import Utilities.Vector2;

public class Cell {

	private static enum CellType {
		Path,
		Wall,
		Start,
		End
	};

	private CellType type;

	private Vector2 screenPos;
	private final Vector2 graphPos;

	private boolean isFastestRoute = false; // Is this cell part of the fastest route
	private boolean isExplored = false;

	// Explored Color Logic
	private int stepsWhenExplored = 0;

	public final int index;
	private boolean debug = false;

	public Cell(Vector2 graphPos, int index) {

		this.graphPos = graphPos;
		this.index = index;

		updateScreenPosition();

		setCellType();

		// Add Button to Each Cell
		ClickManager.Instance.addClickListener(new ClickListener() {
			@Override
			public void onClick(MouseEvent e) {
				int xOffset = 7;
				int yOffset = 28;
				if (screenPos.x < e.getX() - xOffset && e.getX() - xOffset < screenPos.x + Config.getCellWidth()) {
					if (screenPos.y < e.getY() - yOffset && e.getY() - yOffset < screenPos.y + Config.getCellHeight()) {
						if (!debug)
							System.out.println("DEBUG: " + this +
									"\n\tIndex: " + index +

									"\n\tMouse X: " + e.getX() +
									"\n\tMouse Y: " + (e.getY() - yOffset) +

									"\n\tGX: " + graphPos.x +
									"\n\tGY: " + graphPos.y +

									"\n\tX: " + screenPos.x +
									"\n\tY: " + screenPos.y +
									"\n\tX2: " + (screenPos.x + Config.getCellWidth()) +
									"\n\tY2: " + (screenPos.y + Config.getScreenWidth()) +

									"\n\tType: " + type);
						debug = !debug;
					}
				}
			}
		});
	}

	public void setCellType() {
		// If this cell's Coords are equal to the Starting Cell's Coords
		if (graphPos.equals(Config.getStartCell())) {
			CellManager.Instance.SetStartIndex(index);
			type = CellType.Start;
		}

		// If this cell's Coords are equal to the Starting Cell's Coords
		else if (graphPos.equals(Config.getEndCell())) {
			CellManager.Instance.SetEndIndex(index);
			type = CellType.End;
		}

		// Otherwise, determine if it is a Wall or a Path
		else
			type = Config.getMaze().IsWall(graphPos) ? CellType.Path : CellType.Wall;

	}

	public void updateScreenPosition() {
		screenPos = new Vector2(
				graphPos.x * Config.getCellWidth(),
				graphPos.y * Config.getCellHeight());
	}

	protected void DrawCell(Graphics g) {

		Color cellColor = null;
		if (type == CellType.Path) {

			if (isFastestRoute) {
				cellColor = Config.getColorPalette()[0]; // Bright yellow
			}

			else if (isExplored) {

				int stepsSinceExplored = Config.getAlgorithm().GetSteps() - stepsWhenExplored;

				ColorInterpolator interpolator = new ColorInterpolator(Config.getColorPalette(),
						Config.getNumColorGradient());

				cellColor = interpolator.getColor(stepsSinceExplored);

			}

			else
				cellColor = Color.GRAY;

		}

		else if (type == CellType.Wall)
			cellColor = Color.BLACK;

		else if (type == CellType.Start)
			cellColor = Color.GREEN;

		else if (type == CellType.End)
			cellColor = Color.RED;

		g.setColor(cellColor);
		g.fillRect(
				screenPos.x,
				screenPos.y,
				Config.getCellWidth(),
				Config.getCellHeight());

		if (debug) {
			DrawCellDeveloperMode(g);
		}
	}

	protected void DrawCellDeveloperMode(Graphics g) {

		g.setColor(Color.BLUE);
		g.drawRect(this.screenPos.x, this.screenPos.y, Config.getCellWidth(),
				Config.getCellHeight());

		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.PLAIN, 9));

		int count = 1;
		int increaseY = 8;
		int increaseX = 1;

		// g.drawString("ID: " + this.toString(), this.screenPos.x + increaseX,
		// this.screenPos.y + (increaseY * count));
		// count++;
		g.drawString("Index: " + index, this.screenPos.x + increaseX, this.screenPos.y + (increaseY * count));
		count++;

		g.drawString("G: (" + this.graphPos.x + ", " + this.graphPos.y + ")", this.screenPos.x + increaseX,
				this.screenPos.y + (increaseY * count));
		count++;

		// g.drawString("X1: " + this.screenPos.x, this.screenPos.x + increaseX,
		// this.screenPos.y + (increaseY * count));
		// count++;
		// g.drawString("Y1: " + this.screenPos.y, this.screenPos.x + increaseX,
		// this.screenPos.y + (increaseY * count));
		// count++;
		// g.drawString("X2: " + (this.screenPos.x + Config.CELL_WIDTH),
		// this.screenPos.x + increaseX,
		// this.screenPos.y + (increaseY * count));
		// count++;
		// g.drawString("Y2: " + (this.screenPos.y + Config.CELL_HEIGHT),
		// this.screenPos.x + increaseX,
		// this.screenPos.y + (increaseY * count));
		// count++;

		g.drawString("Type: " + type, this.screenPos.x + increaseX, this.screenPos.y + (increaseY * count));
		count++;

		g.drawString("Status: " + isExplored, this.screenPos.x + increaseX, this.screenPos.y + (increaseY * count));
	}

	public void MarkAsFastestPath() {
		isFastestRoute = true;
	}

	public void MarkAsExplored() {

		if (isExplored == false) {
			stepsWhenExplored = Config.getAlgorithm().GetSteps();
			isExplored = true;
		}
	}

	public boolean IsExplored() {
		return isExplored;
	}

	public boolean IsPath() {
		return type == CellType.Path;
	}

	public boolean IsWall() {
		return type == CellType.Wall;
	}

	public boolean IsStart() {
		return type == CellType.Start;
	}

	public boolean IsEnd() {
		return type == CellType.End;
	}

	public Vector2 GraphPosition() {
		return graphPos;
	}

}
