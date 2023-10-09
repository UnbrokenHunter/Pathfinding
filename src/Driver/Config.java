package Driver;

import java.awt.Color;

import Cells.Maze.Maze;
import Cells.Maze.Randomized;
import Cells.Maze.RecursiveBacktracker;
import Cells.Maze.ReverseRecursiveBacktracker;
import Pathfind.AStar;
import Pathfind.Algorithm;
import Pathfind.Floodfill;
import Utilities.ColorInterpolator;
import Utilities.Vector2;

public final class Config {

    // GENERAL SETTINGS
    private static boolean DEVELOPER_MODE = false;
    public static final int FPS = 1000;
    private static Vector2 START_CELL = new Vector2(5, 5);
    private static Vector2 END_CELL = new Vector2(95, 45);

    // ALGORITHM SETTINGS
    private static Algorithm ALGORITHM = new Floodfill();
    public static final Algorithm[] ALGORITHMS = new Algorithm[] {
            new AStar(),
            new Floodfill(),
    };
    private static double ACTION_TIME = 0.05f;

    // CELL SETTINGS
    private static Maze MAZE = new RecursiveBacktracker();
    public static final Maze[] MAZES = new Maze[] {
            new Randomized(),
            new RecursiveBacktracker(),
            new ReverseRecursiveBacktracker(),
    };
    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 400;
    private static int NUM_CELL_ROWS = 100;
    private static int NUM_CELL_COLS = 50;

    // COLOR SETTINGS
    private static Color[] COLOR_PALETTE = (Color[]) ColorInterpolator.getPalettes().values().toArray()[0];
    private static int NUM_COLOR_GRADIENT = 10;

    // Getters
    public static int getNumColorGradient() {
        return NUM_COLOR_GRADIENT;
    }

    public static Color[] getColorPalette() {
        return COLOR_PALETTE;
    }

    public static boolean isDeveloperMode() {
        return DEVELOPER_MODE;
    }

    public static Vector2 getStartCell() {
        return START_CELL;
    }

    public static Vector2 getEndCell() {
        return END_CELL;
    }

    public static Algorithm getAlgorithm() {
        return ALGORITHM;
    }

    public static double getActionTime() {
        return ACTION_TIME;
    }

    public static Maze getMaze() {
        return MAZE;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getNumCellRows() {
        return NUM_CELL_ROWS;
    }

    public static int getNumCellCols() {
        return NUM_CELL_COLS;
    }

    public static int getCellWidth() {
        return (int) (SCREEN_WIDTH / NUM_CELL_ROWS);
    }

    public static int getCellHeight() {
        return (int) (SCREEN_HEIGHT / NUM_CELL_COLS);
    }

    // Setters without restricted access
    public static void setNumColorGradient(int value) {
        NUM_COLOR_GRADIENT = value;
    }

    public static void setColorPalette(Color[] value) {
        COLOR_PALETTE = value;
    }

    public static void setDeveloperMode(boolean value) {
        DEVELOPER_MODE = value;
    }

    public static void setStartCell(Vector2 value) {
        START_CELL = value;
    }

    public static void setEndCell(Vector2 value) {
        END_CELL = value;
    }

    public static void setAlgorithm(Algorithm value) {
        ALGORITHM = value;
    }

    public static void setActionTime(double value) {
        ACTION_TIME = value;
    }

    public static void setMaze(Maze value) {
        MAZE = value;
    }

    public static void setScreenWidth(int value) {
        SCREEN_WIDTH = value;
    }

    public static void setScreenHeight(int value) {
        SCREEN_HEIGHT = value;
    }

    public static void setNumCellRows(int value) {
        NUM_CELL_ROWS = value;
    }

    public static void setNumCellCols(int value) {
        NUM_CELL_COLS = value;
    }

    public static String getConfigState() {
        return "Config Settings: " +
                "\n\tDeveloper Mode: " + DEVELOPER_MODE +
                "\n\tStart Cell: " + START_CELL +
                "\n\tEnd Cell: " + END_CELL +
                "\n\tAlgorithm: " + ALGORITHM +
                "\n\tAction Time: " + ACTION_TIME +
                "\n\tMaze: " + MAZE +
                "\n\tScreen Width: " + SCREEN_WIDTH +
                "\n\tScreen Height: " + SCREEN_HEIGHT +
                "\n\tNumber of Cell Rows: " + NUM_CELL_ROWS +
                "\n\tNumber of Cell Columns: " + NUM_CELL_COLS +
                "\n\tCell Width: " + getCellWidth() +
                "\n\tCell Height: " + getCellHeight();
    }

}
