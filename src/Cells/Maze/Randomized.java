package Cells.Maze;

import Utilities.Vector2;

public class Randomized extends Maze {

    public static final double WALL_CHANCE = 30d / 100d;

    @Override
    public boolean IsWall(Vector2 position) {
        return Math.random() > WALL_CHANCE;
    }

}
