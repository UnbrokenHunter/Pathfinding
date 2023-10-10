package Pathfind;

import java.util.HashSet;
import java.util.ArrayList;
import Cells.Cell;
import Cells.CellManager;
import Driver.Config;
import Driver.Game;
import Utilities.Looper;

/**
 * Represents a generic pathfinding algorithm.
 * <p>
 * The class provides a base structure for different pathfinding algorithms. It
 * contains fields and methods
 * common to many algorithms, like maintaining the set of currently explored
 * cells, keeping track of steps taken,
 * and handling the timing of actions.
 * </p>
 */
public abstract class Algorithm {

    /** Number of steps taken by the algorithm. */
    protected int steps;

    /** Time taken by the algorithm to compute the path. */
    protected double time;

    /**
     * Gets the number of steps taken by the algorithm.
     * 
     * @return Number of steps.
     */
    public int GetSteps() {
        return steps;
    }

    /** Set of cells currently being processed. */
    public HashSet<Cell> currentLayer = new HashSet<Cell>();

    /** Set of cells to be processed in the next iteration. */
    public HashSet<Cell> nextLayer = new HashSet<Cell>();

    /** Set of cells that have already been processed. */
    public HashSet<Cell> exploredCells = new HashSet<Cell>();

    /** List of cell indexes forming the path. */
    protected ArrayList<Cell> path = new ArrayList<Cell>();

    /** Manager to access cells and their details. */
    protected CellManager cells;

    /** Starting cell for the pathfinding algorithm. */
    protected Cell start;

    /** Target or end cell for the pathfinding algorithm. */
    protected Cell end;

    /**
     * Initializes the algorithm by setting the start and end cells.
     */
    public void startAlgorithm() {
        this.cells = CellManager.Instance;
        this.start = cells.GetCellByIndex(cells.GetStartIndex());
        this.end = cells.GetCellByIndex(cells.GetEndIndex());
    }

    /**
     * Abstract method to be implemented by subclasses to provide the actual
     * pathfinding logic.
     */
    public abstract void Pathfind();

    /**
     * Invoked when the target or end cell is found by the algorithm.
     * 
     * @param cell The end cell that was found.
     */
    public void EndFound(Cell cell) {
        System.out.println("End found at: " + cell.GraphPosition());
    }

    /**
     * Invoked when the algorithm cannot find a path to the target cell.
     */
    public void EndNotFound() {
        Game.Instance.restart();
    }

    /**
     * Handles the action timing for the algorithm based on the provided
     * configuration.
     * 
     * @return True if the algorithm should take the next action, otherwise False.
     */
    public final boolean HandleActionTime() {
        if (Looper.getTime() / steps > Config.getActionTime()) {
            Config.getAlgorithm().steps++;
            return true;
        }
        return false;
    }

    /**
     * Resets the algorithm's state, clearing explored cells and resetting steps.
     */
    public void reset() {
        steps = 0;
        currentLayer.clear();
        nextLayer.clear();
        currentLayer.add(CellManager.Instance.GetCellByVector(Config.getStartCell()));
    }
}
