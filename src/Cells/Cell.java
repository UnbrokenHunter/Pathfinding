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

/**
 * Represents a cell in a grid-based pathfinding simulation.
 * <p>
 * Each cell can be of one of the following types:
 * - Path
 * - Wall
 * - Start
 * - End
 * </p>
 * Additionally, the cell has the ability to listen to click events, mark itself
 * as explored, and be part of the fastest route.
 */
public class Cell {

	/**
	 * Enum representing the various types of cells.
	 */
	private static enum CellType {
		Path,
		Wall,
		Start,
		End
	};

	/** The type of this cell. */
	private CellType type;

	/** Position of the cell on screen. */
	private Vector2 screenPos;
	/** Position of the cell in the grid graph. */
	private final Vector2 graphPos;

	/** Indicates if this cell is part of the fastest route. */
	private boolean isFastestRoute = false;

	/** Indicates if this cell has been explored. */
	private boolean isExplored = false;

	/** Records the step count when this cell was explored. */
	private int stepsWhenExplored = 0;

	/** Index of this cell. */
	public final int index;

	/** Flag for debug mode. */
	private boolean debug = false;

	/**
	 * Constructs a cell with a given graph position and index.
	 *
	 * @param graphPos The position of the cell in the grid graph.
	 * @param index    The index of the cell.
	 */

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

						if (type == CellType.Wall)
							type = CellType.Path;
						else if (type == CellType.Path)
							type = CellType.Wall;
					}
				}
			}
		});
	}

	/**
	 * Determines and sets the type of this cell based on its position and the
	 * game's configuration.
	 */
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

	/**
	 * Updates the screen position of the cell based on its graph position and
	 * configuration.
	 */
	public void updateScreenPosition() {
		screenPos = new Vector2(
				graphPos.x * Config.getCellWidth(),
				graphPos.y * Config.getCellHeight());
	}

	/**
	 * Renders the cell using the given Graphics object.
	 *
	 * @param g The graphics context to use for drawing.
	 */
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

		if (debug && Config.isDeveloperMode()) {
			DrawCellDeveloperMode(g);
		}
	}

	/**
	 * Draws additional developer-specific information for the cell.
	 * This is activated when the application is in developer mode.
	 *
	 * @param g The graphics context to use for drawing.
	 */
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

	/**
	 * Marks this cell as being part of the fastest path.
	 */
	public void MarkAsFastestPath() {
		isFastestRoute = true;
	}

	/**
	 * Marks this cell as explored and records the current algorithm step.
	 */
	public void MarkAsExplored() {

		if (isExplored == false) {
			stepsWhenExplored = Config.getAlgorithm().GetSteps();
			isExplored = true;
		}
	}

	/**
	 * Checks if this cell has been explored.
	 * 
	 * @return True if the cell has been explored, otherwise false.
	 */
	public boolean IsExplored() {
		return isExplored;
	}

	/**
	 * Checks if this cell is of type Path.
	 * 
	 * @return True if the cell is of type Path, otherwise false.
	 */
	public boolean IsPath() {
		return type == CellType.Path;
	}

	/**
	 * Checks if this cell is of type Wall.
	 * 
	 * @return True if the cell is of type Wall, otherwise false.
	 */
	public boolean IsWall() {
		return type == CellType.Wall;
	}

	/**
	 * Checks if this cell is of type Start.
	 * 
	 * @return True if the cell is of type Start, otherwise false.
	 */
	public boolean IsStart() {
		return type == CellType.Start;
	}

	/**
	 * Checks if this cell is of type End.
	 * 
	 * @return True if the cell is of type End, otherwise false.
	 */
	public boolean IsEnd() {
		return type == CellType.End;
	}

	/**
	 * Returns the position of the cell in the grid graph.
	 * 
	 * @return The graph position of the cell.
	 */
	public Vector2 GraphPosition() {
		return graphPos;
	}

}
