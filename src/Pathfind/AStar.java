package Pathfind;

import java.util.ArrayList;
import java.util.HashMap;

import Cells.Cell;
import Cells.CellManager;
import Utilities.Vector2;

public class AStar extends Algorithm {

    /**
     * Represents a cell used in the A* pathfinding algorithm.
     * <p>
     * This class encapsulates the three key components of the A* algorithm for each
     * cell:
     * - G (cost from the start to the current cell)
     * - H (heuristic estimation of the cost from the current cell to the goal)
     * - F (total cost, which is G + H)
     * </p>
     */
    private class AStarCell {

        /** The actual cell this AStarCell is representing. */
        public final Cell cell;

        /** The manager used to fetch details about the cells. */
        private final CellManager manager;

        /** The cost from the start to the current cell. */
        public final double G;

        /** Heuristic estimation of the cost from the current cell to the goal. */
        public final double H;

        /** Total cost, which is G + H. */
        public final double F;

        /**
         * Constructor initializing the AStarCell with its associated Cell.
         * 
         * @param cell The cell to be associated with the AStarCell.
         */
        public AStarCell(Cell cell) {
            this.cell = cell;
            this.manager = CellManager.Instance;
            this.G = Vector2.distance(start.GraphPosition(), cell.GraphPosition());
            this.H = Vector2.distance(end.GraphPosition(), cell.GraphPosition());
            this.F = G + H;
        }

        /**
         * Gets the G value, representing the cost from the start to the current cell.
         * 
         * @return The cost from the start to the current cell.
         */
        public double getG() {
            return G;
        }

        /**
         * Gets the H value, representing the heuristic estimation from the current cell
         * to the goal.
         * 
         * @return The heuristic estimation from the current cell to the goal.
         */
        public double getH() {
            return H;
        }

        /**
         * Gets the F value, representing the total cost, which is G + H.
         * 
         * @return The total cost, which is G + H.
         */
        public double getF() {
            return F;
        }
    }

    private Vector2[] directions = {
            Vector2.up, Vector2.down, Vector2.left, Vector2.right
    };

    private HashMap<Cell, AStarCell> AStarCellMap = new HashMap<Cell, AStarCell>();

    @Override
    public void startAlgorithm() {
        super.startAlgorithm();

        currentLayer.add(start);
    }

    /**
     * 1. Get all cells that are possible to move to
     * 2. Get their F values
     * 3. Chose the best F value
     */

    @Override
    public void Pathfind() {

        if (currentLayer.size() <= 0) {
            EndNotFound();
            return;
        }

    }

    private void CreateAStarCell(Cell cell) {
        if (AStarCellMap.containsKey(cell))
            return;

        AStarCell aStarCell = new AStarCell(cell);

        exploredCells.add(cell);
        cell.MarkAsExplored();

        AStarCellMap.put(cell, aStarCell);
    }

    private ArrayList<Cell> GetNearbyCells(Vector2 location) {

        ArrayList<Cell> nearbyCells = new ArrayList<Cell>();

        for (Vector2 direction : directions) {
            Cell cell = cells.GetCellByVector(location.plus(direction));

            if (!exploredCells.contains(cell)) {
                nearbyCells.add(cell);
            }
        }

        return nearbyCells;
    }

}
