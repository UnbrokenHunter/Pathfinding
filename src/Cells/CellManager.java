package Cells;

import java.awt.Graphics;
import java.util.Hashtable;

import Driver.Config;
import Utilities.Vector2;

public class CellManager {

	public static CellManager Instance;

	private Hashtable<Integer, Cell> CellIndex = new Hashtable<>();

	private int startIndex = -1;
	private int endIndex = -1;

	public CellManager() {

		if (Instance != null) {
			System.out.println("ERROR - THERE IS MORE THAN ONE CELL MANAGER");
		} else {
			Instance = this;
		}
	}

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

	public void setCellTypes() {
		for (Cell cell : CellIndex.values()) {
			cell.setCellType();
		}
	}

	public void drawCells(Graphics g) {
		for (Cell cell : CellIndex.values()) {
			cell.DrawCell(g);
		}
	}

	public void updateScreenPosition() {
		for (Cell cell : CellIndex.values()) {
			cell.updateScreenPosition();
		}
	}

	public void SetStartIndex(int index) {
		if (startIndex == -1)
			startIndex = index;
	}

	public void SetEndIndex(int index) {
		if (endIndex == -1)
			endIndex = index;
	}

	public int GetStartIndex() {
		return startIndex;
	}

	public int GetEndIndex() {
		return endIndex;
	}

	public Cell GetCellByIndex(int index) {
		Cell cell;
		cell = CellIndex.get(index);
		return cell;
	}

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
