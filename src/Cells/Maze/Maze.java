package Cells.Maze;

import java.awt.Graphics;

import Cells.CellManager;
import Utilities.Vector2;

public abstract class Maze {

    public boolean computationComplete = false;

    public void StartMaze() {
        System.out.println("Maze needs no setup");
    }

    public void ComputeMaze() {
        System.out.println("Maze needs no computation");
        computationComplete = true;
        ComputationCompleted();
    }

    public void DrawDebug(Graphics g) {

    }

    public void reset() {
        computationComplete = false;
    }

    // Used for the rendering. This is not a helper class. It is used by the
    // renderer to determine if each sqare is a wall or path
    public abstract boolean IsWall(Vector2 position);

    protected final void ComputationCompleted() {
        CellManager.Instance.setCellTypes();
    }

}
