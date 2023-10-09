package Driver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Cells.CellManager;
import Click.ClickListener;
import Click.ClickManager;
import Pathfind.PathfindingManager;
import Utilities.Looper;

public class Game {

    public static Game Instance;

    private boolean setupComplete = false;

    private Window window;

    public Game(Window window) {
        this.window = window;
    }

    public void start() {

        if (Instance != null) {
            System.out.println("ERROR - THERE IS MORE THAN ONE GAME");
        } else {
            Instance = this;
        }

        startPathfinding();
        computeMaze();

        CreateButton();
    }

    private void startPathfinding() {
        CellManager.Instance.generateCells();
        PathfindingManager.Instance.startPathfinder();
        setupComplete = true;
    }

    private void computeMaze() {
        if (Config.isDeveloperMode()) {
            if (!Config.getMaze().computationComplete) {
                Config.getMaze().ComputeMaze();
            }
        } else {
            while (!Config.getMaze().computationComplete) {
                Config.getMaze().ComputeMaze();
            }
        }

    }

    public void loop() {
        if (setupComplete) {
            PathfindingManager.Instance.pathfind();
        }
    }

    public void render(Graphics g) {

        if (!Config.getMaze().computationComplete && Config.isDeveloperMode()) {
            Config.getMaze().DrawDebug(g);
        }

        else if (setupComplete) {
            CellManager.Instance.drawCells(g);
            PathfindingManager.Instance.drawDebug(g);
        }

        // Draw Menu Button
        DrawMenu(g);
    }

    public void restart() {
        Looper.ResetTime();
        ClickManager.Instance.reset();
        CreateButton();
        Config.getMaze().reset();
        startPathfinding();
        computeMaze();
    }

    private void CreateButton() {
        // Add Button to Each Cell
        ClickManager.Instance.addClickListener(new ClickListener() {
            @Override
            public void onClick(MouseEvent e) {
                int xOffset = 7;
                int yOffset = 28;
                if (Config.getScreenWidth() - (int) (menuButtonSize * 1.65) < e.getX() - xOffset && e.getX()
                        - xOffset < Config.getScreenWidth() - (int) (menuButtonSize * 1.65) + menuButtonSize) {
                    if (0 < e.getY() - yOffset && e.getY() - yOffset < menuButtonSize) {
                        System.out.println("Return to Menu");
                        window.startMenu();
                    }
                }
            }
        });
    }

    private int menuButtonSize = 20;

    private void DrawMenu(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(Config.getScreenWidth() - (int) (menuButtonSize * 1.65), 0, menuButtonSize, menuButtonSize);

        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.PLAIN, (menuButtonSize - 5)));
        g.drawString("M", Config.getScreenWidth() - (int) (menuButtonSize * 1.5),
                (int) (menuButtonSize * 0.75));

    }
}
