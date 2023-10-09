package Pathfind;

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

    public ArrayList<Cell> currentLayer = new ArrayList<Cell>();
    public ArrayList<Cell> nextLayer = new ArrayList<Cell>();

    public ArrayList<Cell> exploredCells = new ArrayList<Cell>();

    // Indicies of the path
    protected ArrayList<Cell> path = new ArrayList<Cell>();

    protected CellManager cells = CellManager.Instance;

    protected Cell start = cells.GetCellByIndex(cells.GetStartIndex());
    protected Cell end = cells.GetCellByIndex(cells.GetEndIndex());

    public abstract void Pathfind();

    public void EndFound(Cell cell) {
        System.out.println("End found at: " + cell.GraphPosition());
    }

    public void EndNotFound() {
        Game.Instance.restart();
    }

    public boolean HandleActionTime() {
        if (Looper.GetTime() / steps > Config.getActionTime()) {

            Config.getAlgorithm().steps++;
            return true;
        }
        return false;
    }

    public void reset() {

    }

}
