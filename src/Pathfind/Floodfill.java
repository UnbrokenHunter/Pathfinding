package Pathfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Cells.Cell;
import Driver.Config;
import Utilities.Vector2;

public class Floodfill extends Algorithm {

    // Linked List to backtrack from
    private HashMap<Cell, Cell> predecessorMap = new HashMap<>();

    private Vector2[] directions = {
            Vector2.up, Vector2.down, Vector2.left, Vector2.right
    };

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
    }

    private void ExploreCell(Cell cell) {
        if (cell.IsEnd())
            EndFound(cell);

        exploredCells.add(cell);

        for (Vector2 direction : directions) {
            AddToNextLayer(cell, ValidateDirection(cell, direction));
        }
    }

    private boolean AddToNextLayer(Cell currentCell, Cell neighboringCell) {
        if (neighboringCell == null || neighboringCell.IsWall() || neighboringCell.IsStart()) {
            return false;
        }

        if (!nextLayer.contains(neighboringCell) && !currentLayer.contains(neighboringCell)) {
            neighboringCell.MarkAsExplored();
            nextLayer.add(neighboringCell); // assuming nextLayer is a HashSet or similar
            predecessorMap.put(neighboringCell, currentCell);
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
