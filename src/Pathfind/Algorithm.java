package Pathfind;

import java.util.HashSet;
import java.util.ArrayList;

import Cells.Cell;
import Cells.CellManager;
import Driver.Config;
import Driver.Game;
import Utilities.Looper;

public abstract class Algorithm {
    protected int steps;
    protected double time;

    public int GetSteps() {
        return steps;
    }

    // Use sets for faster lookup
    public HashSet<Cell> currentLayer = new HashSet<Cell>();
    public HashSet<Cell> nextLayer = new HashSet<Cell>();
    public HashSet<Cell> exploredCells = new HashSet<Cell>();

    // Indexes of the path
    protected ArrayList<Cell> path = new ArrayList<Cell>();

    protected CellManager cells;
    protected Cell start;
    protected Cell end;

    public void startAlgorithm() {
        this.cells = CellManager.Instance;
        this.start = cells.GetCellByIndex(cells.GetStartIndex());
        this.end = cells.GetCellByIndex(cells.GetEndIndex());
    }

    public abstract void Pathfind();

    public void EndFound(Cell cell) {
        System.out.println("End found at: " + cell.GraphPosition());
    }

    public void EndNotFound() {
        Game.Instance.restart();
    }

    public final boolean HandleActionTime() {
        if (Looper.GetTime() / steps > Config.getActionTime()) {
            Config.getAlgorithm().steps++;
            return true;
        }
        return false;
    }

    public void reset() {
        steps = 0;
        currentLayer.clear();
        nextLayer.clear();
        currentLayer
                .add(CellManager.Instance.GetCellByVector(Config.getStartCell()));
    }
}