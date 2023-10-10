package Cells;

import java.awt.Graphics;
import java.util.Hashtable;

import Driver.Config;
import Utilities.Vector2;

/**
 * Manages the creation, rendering, and handling of all Cell instances within
 * the pathfinding simulation.
 * <p>
 * This class is responsible for creating Cell instances based on the
 * configurations specified
 * in the Config class, drawing all cells, and managing cell-specific properties
 * such as
 * determining start and end cells.
 * </p>
 * The class is implemented as a singleton.
 */
public class CellManager {

	/** The static instance of the CellManager. */
	public static CellManager Instance;

	/** A hashtable mapping index values to corresponding Cell instances. */
	private Hashtable<Integer, Cell> CellIndex = new Hashtable<>();

	/** The index of the starting cell. */
	private int startIndex = -1;
	/** The index of the ending cell. */
	private int endIndex = -1;

	/**
	 * Constructor that ensures a single instance of CellManager exists.
	 * If an instance already exists, an error message is printed.
	 */
	public CellManager() {

		if (Instance != null) {
			System.out.println("ERROR - THERE IS MORE THAN ONE CELL MANAGER");
		} else {
			Instance = this;
		}
	}

	/**
	 * Generates Cell instances for each position in the grid, based on specified
	 * configurations.
	 * <p>
	 * Initializes each cell, assigns it an index, and stores it in the CellIndex
	 * hashtable.
	 * </p>
	 */
	public void generateCells() {

		Config.getMaze().StartMaze();

		for (int row = 0; row < Config.getNumCellRows(); row++) {
			for (int col = 0; col < Config.getNumCellCols(); col++) {

				int index = row + (col * Config.getNumCellRows());

				var position = new Vector2(row, col);

				Cell cell = new Cell(position, index);

				CellIndex.put(index, cell);
			}
		}
	}

	/**
	 * Calls each Cell instance to determine and set its type based on its position.
	 */
	public void setCellTypes() {
		for (Cell cell : CellIndex.values()) {
			cell.setCellType();
		}
	}

	/**
	 * Draws all Cell instances.
	 * 
	 * @param g The graphics context to use for drawing.
	 */
	public void drawCells(Graphics g) {
		for (Cell cell : CellIndex.values()) {
			cell.DrawCell(g);
		}
	}

	/**
	 * Updates the screen position of all Cell instances.
	 */
	public void updateScreenPosition() {
		for (Cell cell : CellIndex.values()) {
			cell.updateScreenPosition();
		}
	}

	/**
	 * Sets the starting index if it hasn't been set already.
	 * 
	 * @param index The index to set as the starting index.
	 */
	public void SetStartIndex(int index) {
		if (startIndex == -1)
			startIndex = index;
	}

	/**
	 * Sets the ending index if it hasn't been set already.
	 * 
	 * @param index The index to set as the ending index.
	 */
	public void SetEndIndex(int index) {
		if (endIndex == -1)
			endIndex = index;
	}

	/**
	 * Retrieves the starting index.
	 * 
	 * @return The starting index.
	 */
	public int GetStartIndex() {
		return startIndex;
	}

	/**
	 * Retrieves the ending index.
	 * 
	 * @return The ending index.
	 */
	public int GetEndIndex() {
		return endIndex;
	}

	/**
	 * Retrieves the Cell instance associated with a given index.
	 * 
	 * @param index The index of the desired Cell instance.
	 * @return The associated Cell instance.
	 */
	public Cell GetCellByIndex(int index) {
		Cell cell;
		cell = CellIndex.get(index);
		return cell;
	}

	/**
	 * Retrieves the Cell instance located at the specified Vector2 position.
	 * 
	 * @param vector The Vector2 position of the desired Cell instance.
	 * @return The associated Cell instance.
	 */
	public Cell GetCellByVector(Vector2 vector) {
		int index = vector.x + (vector.y * Config.getNumCellRows());
		Cell cell = GetCellByIndex(index);
		if (cell.GraphPosition().equals(vector)) {
			return GetCellByIndex(index);

		} else {
			System.out.println("Could not find Cell: " + vector);
			return null;
		}
	}

}
