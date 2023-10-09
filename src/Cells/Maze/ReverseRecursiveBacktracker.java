package Cells.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Driver.Config;
import Utilities.Vector2;

/**
 * RecursiveBacktracker is a maze generation algorithm.
 * 
 * Logic/Progression:
 * 1. Start at an initial cell.
 * 2. Push the cell to the stack.
 * 3. Explore a random unvisited neighbor, moving 2 cells over in a chosen
 * direction (skipping over the "wall" cell).
 * 4. If the destination cell hasn't been visited, carve a path by marking it
 * and the wall cell as paths.
 * 5. If no unvisited neighbors are found, backtrack by popping from the stack
 * until a cell with unvisited neighbors is found.
 * 6. Repeat until the stack is empty.
 */
public class ReverseRecursiveBacktracker extends Maze {

    // A stack to hold cells for backtracking.
    private final ArrayList<Vector2> stack = new ArrayList<>();

    // A map to store the status of each cell: whether it's a path, wall, start, or
    // end.
    private final HashMap<Vector2, CellType> vectorMap = new HashMap<>();

    // Random number generator for random selection of directions.
    private final Random random = new Random();

    // Enum to define cell types.
    private enum CellType {
        Path, Wall, Start, End
    }

    // This method initializes the maze with walls and starts the algorithm at the
    // starting cell.
    @Override
    public void StartMaze() {
        for (int y = 0; y < Config.getNumCellCols(); y++) {
            for (int x = 0; x < Config.getNumCellRows(); x++) {
                vectorMap.put(new Vector2(x, y), CellType.Wall);
            }
        }

        Vector2 startCell = Config.getStartCell();
        stack.add(startCell);
        vectorMap.put(startCell, CellType.Path); // Change this line to Path instead of Start
    }

    // Core computation of the maze. If the stack is not empty, visit the next cell.
    // If empty, the maze is complete.
    @Override
    public void ComputeMaze() {
        if (!stack.isEmpty()) {
            VisitCellOnStack();
        } else {
            computationComplete = true;
            ComputationCompleted();
            System.out.println("Maze Computation Complete");
        }
    }

    // Visit the top cell on the stack. If it has unvisited neighbors, move to one,
    // else backtrack.
    public void VisitCellOnStack() {
        Vector2 current = stack.get(0);
        ArrayList<Vector2> directions = new ArrayList<>(List.of(Vector2.up, Vector2.down, Vector2.left, Vector2.right));

        Vector2 next = GetNextVector(directions, current);

        if (next != null) {
            stack.add(0, next);
            Vector2 wall = current.plus(next).div(2);
            vectorMap.put(next, CellType.Path);
            vectorMap.put(wall, CellType.Path);
        } else {
            stack.remove(0);
        }
    }

    // Given a list of potential directions, select a random direction and compute
    // the resulting position.
    // If that position is valid and is a wall (unvisited), return it. Else, try
    // another direction.
    private Vector2 GetNextVector(ArrayList<Vector2> options, Vector2 vector) {
        if (options.isEmpty())
            return null;

        if (vector == null)
            return null;

        Vector2 direction = options.get(random.nextInt(options.size()));
        Vector2 newPosition = vector.plus(direction.mult(2)); // Move 2 cells in the chosen direction.

        if (isPositionValid(newPosition) && vectorMap.get(newPosition) == CellType.Wall) {
            return newPosition;
        }

        options.remove(direction);
        return GetNextVector(options, vector);
    }

    // Check if a given position is within the maze boundaries and not the starting
    // cell.
    private boolean isPositionValid(Vector2 position) {
        return 0 <= position.x && position.x <= Config.getNumCellRows()
                && 0 <= position.y && position.y <= Config.getNumCellCols()
                && !position.equals(Config.getStartCell());
    }

    // Check if a given position is a wall. Used by renderer to determine cell type.
    @Override
    public boolean IsWall(Vector2 position) {
        return vectorMap.get(position) == CellType.Wall;
    }

    // Debug drawing method to visualize the maze and its generation process.
    @Override
    public void DrawDebug(Graphics g) {

        // Start Cell
        g.setColor(Color.GREEN);
        g.fillRect(Config.getStartCell().x * Config.getCellWidth(), Config.getStartCell().y * Config.getCellHeight(),
                Config.getCellWidth(), Config.getCellHeight());

        // Stack
        g.setColor(Color.CYAN);
        for (Vector2 stackElement : stack) {
            g.drawRect(stackElement.x * Config.getCellWidth() + 3, stackElement.y * Config.getCellHeight() + 3,
                    Config.getCellWidth() - 6, Config.getCellHeight() - 6);
        }

        // Map
        for (Vector2 mapElement : vectorMap.keySet()) {

            CellType type = vectorMap.get(mapElement);
            if (type == CellType.Path)
                g.setColor(Color.blue);
            else if (type == CellType.Wall)
                g.setColor(Color.red);

            g.drawRect(mapElement.x * Config.getCellWidth(), mapElement.y * Config.getCellHeight(),
                    Config.getCellWidth(), Config.getCellHeight());
        }
    }
}
