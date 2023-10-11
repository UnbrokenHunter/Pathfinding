package Pathfind;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import Cells.Cell;
import Driver.Config;
import Utilities.Vector2;

/**
 * AStar provides an iterative implementation of the A* pathfinding algorithm.
 * Designed to visualize the algorithm's progression by processing one cell per
 * loop.
 */
public class AStar extends Algorithm {

    /**
     * AStarCell encapsulates key components for each cell in A*:
     * - G: cost from the start to the current cell
     * - H: heuristic estimate of cost from current cell to goal
     * - F: total cost (G + H)
     */
    private class AStarCell {

        /** The associated Cell for this AStarCell. */
        public final Cell cell;

        /** Cost from the start to this cell. */
        public double G;

        /** Heuristic estimate of cost from this cell to the goal. */
        public double H;

        /** Total cost (G + H). */
        public double F;

        /**
         * Constructs an AStarCell with its associated Cell and optional parent.
         * 
         * @param cell   The cell to be associated with.
         * @param parent The parent AStarCell from which this cell is reached.
         */
        public AStarCell(Cell cell, AStarCell parent) {
            this.cell = cell;

            if (parent == null) {
                // Starting cell has a G-value of 0.
                this.G = 0;
            } else {
                this.G = parent.getG() + 1; // Assuming each move has a cost of 1.
            }

            this.H = Vector2.distance(end.GraphPosition(), cell.GraphPosition());
            this.F = G + H;

            if (this.cell == null)
                System.err.println("Cell is Null: " + this);
        }

        public double getG() {
            return G;
        }

        public double getH() {
            return H;
        }

        public double getF() {
            return F;
        }
    }

    private Vector2[] directions = { Vector2.up, Vector2.down, Vector2.left, Vector2.right };

    private PriorityQueue<AStarCell> openList;
    private HashMap<Cell, AStarCell> closedList;
    private AStarCell currentAStarCell;
    private HashMap<Cell, AStarCell> openListLookup = new HashMap<>();

    @Override
    public void startAlgorithm() {
        super.startAlgorithm();

        openList = new PriorityQueue<>(Comparator.comparingDouble(AStarCell::getF));
        closedList = new HashMap<>();
        openListLookup = new HashMap<>();

        // Initializing with the start cell
        currentAStarCell = new AStarCell(start, null); // Start cell doesn't have a parent.
        openList.add(currentAStarCell);
        openListLookup.put(start, currentAStarCell);
    }

    @Override
    public void Pathfind() {

        // Just makes it run 5 times per execution (Speeds up the animation)
        for (int i = 0; i < 5; i++) {

            if (openList.isEmpty()) {
                EndNotFound();
                return;
            }

            if (!handleCurrentCell()) {
                return;
            }

            fetchNextCell();
            processNeighborCells();
        }
    }

    private boolean handleCurrentCell() {
        if (currentAStarCell == null) {
            return false;
        }

        if (currentAStarCell.cell.IsEnd() && path.isEmpty()) {
            reconstructPath(currentAStarCell.cell);
            EndFound(currentAStarCell.cell);
            return false;
        }

        closedList.put(currentAStarCell.cell, currentAStarCell);
        currentAStarCell.cell.MarkAsExplored();
        return true;
    }

    private void processNeighborCells() {
        for (Vector2 direction : directions) {

            Vector2 nextPosition = currentAStarCell.cell.GraphPosition().plus(direction);
            Cell nextCell = cells.GetCellByVector(nextPosition);

            if (isValidNeighbor(nextCell)) {
                updateNeighborData(nextCell);
            }
        }
    }

    private boolean isValidNeighbor(Cell nextCell) {
        return nextCell != null &&
                !closedList.containsKey(nextCell) &&
                !nextCell.IsWall() &&
                !nextCell.IsStart() &&
                0 <= nextCell.GraphPosition().x && nextCell.GraphPosition().x <= Config.getNumCellRows()
                && 0 <= nextCell.GraphPosition().y && nextCell.GraphPosition().y <= Config.getNumCellCols();
    }

    private void updateNeighborData(Cell nextCell) {
        AStarCell nextAStarCell = openListLookup.get(nextCell);

        if (nextAStarCell == null) {
            nextAStarCell = new AStarCell(nextCell, currentAStarCell);
            openList.add(nextAStarCell);
            openListLookup.put(nextCell, nextAStarCell);
        }

        double potentialNewG = currentAStarCell.getG() + 1;
        if (potentialNewG < nextAStarCell.getG()) {
            nextAStarCell.G = potentialNewG;
            nextAStarCell.F = nextAStarCell.G + nextAStarCell.getH();
        }

        predecessorMap.put(nextCell, currentAStarCell.cell);
    }

    private void fetchNextCell() {
        currentAStarCell = openList.poll();
        if (currentAStarCell != null) {
            openListLookup.remove(currentAStarCell.cell);
        } else {
            EndNotFound(); // Destination unreachable.
        }
    }

    private void reconstructPath(Cell current) {

        if (!path.isEmpty())
            return;

        Cell cell = current;

        while (predecessorMap.get(cell) != null) {
            cell = predecessorMap.get(cell);
            System.out.println(cell);
            path.add(0, cell); // Adds to the beginning to reverse the path
            cell.MarkAsFastestPath();
        }
    }
}
