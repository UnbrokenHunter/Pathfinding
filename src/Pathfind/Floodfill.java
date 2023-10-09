package Pathfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Cells.Cell;
import Driver.Config;
import Utilities.Vector2;

public class Floodfill extends Algorithm {

    private HashMap<Cell, Cell> predecessorMap = new HashMap<>();

    @Override
    public void Pathfind() {

        if (currentLayer.size() == 0)
            EndNotFound();

        List<Cell> temp = new ArrayList<>(currentLayer);
        currentLayer.clear();
        for (Cell cell : temp) {
            ExploreCell(cell);
        }
        currentLayer.addAll(nextLayer);
        nextLayer.clear();

        // System.out.println("Pathfinding: " + currentLayer + "\n\tNext Layer: " +
        // temp);
    }

    private void ExploreCell(Cell cell) {

        if (cell.IsEnd())
            EndFound(cell);

        exploredCells.add(cell);

        // Search Up
        AddToNextLayer(cell, ValidateDirection(cell, Vector2.up));

        // Search Down
        AddToNextLayer(cell, ValidateDirection(cell, Vector2.down));

        // Search Left
        AddToNextLayer(cell, ValidateDirection(cell, Vector2.left));

        // Search Right
        AddToNextLayer(cell, ValidateDirection(cell, Vector2.right));
    }

    private boolean AddToNextLayer(Cell currentCell, Cell neighboringCell) {
        if (neighboringCell != null
                && !neighboringCell.IsWall()
                && !neighboringCell.IsStart()
                && !nextLayer.contains(neighboringCell)
                && !currentLayer.contains(neighboringCell)) {

            neighboringCell.MarkAsExplored();
            nextLayer.add(neighboringCell);

            predecessorMap.put(neighboringCell, currentCell); // Store the predecessor
            // System.out.println("Mapping " + neighboringCell.GraphPosition() + " to " +
            // currentCell.GraphPosition());

            return true;
        }
        return false;
    }

    private Cell ValidateDirection(Cell cell, Vector2 direction) {
        Vector2 newPosition = cell.GraphPosition().plus(direction);

        // Check if the new position is within the grid boundaries
        if (newPosition.x < 0 || newPosition.x >= Config.getNumCellRows() || newPosition.y < 0
                || newPosition.y >= Config.getNumCellCols()) {
            return null; // Out of bounds
        }

        Cell exploring = cells.GetCellByVector(newPosition);
        if (exploring == null) {
            return null;
        } else if (WasExplored(exploring)) {
            return exploring;
        }
        return null;
    }

    private boolean WasExplored(Cell exploring) {

        if (!exploring.IsExplored()) {
            return exploring.IsPath() || exploring.IsEnd();
        }

        return false;
    }

    private void backtrack(Cell endCell) {
        Cell currentCell = endCell;

        while (currentCell != null && currentCell != start) {
            path.add(0, currentCell);
            currentCell = predecessorMap.get(currentCell);
        }
        path.add(0, start);
    }

    @Override
    public void EndFound(Cell end) {
        System.out.println("End found at: " + end.GraphPosition());

        backtrack(end); // Backtrack from the end cell
        for (Cell c : path) {
            if (c != null) {
                c.MarkAsFastestPath();
            }
        }
    }

}
