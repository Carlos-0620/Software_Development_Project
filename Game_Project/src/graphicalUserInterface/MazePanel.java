package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import maze.Maze;
import sprites.Player;
import sprites.Sprite;

public class MazePanel extends JPanel {
    private final Maze maze;
    private final Player player1;
    private final Player player2;
    private final List<Sprite> spirits;

    private final int cellSize = 30;

    public MazePanel(MazeGame game) {
        this.maze = game.getMaze();       // Add public getters in MazeGame if not present
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.spirits = game.getSprites();

        setPreferredSize(new Dimension(maze.getWidth() * cellSize, maze.getHeight() * cellSize));
        setBackground(Color.WHITE);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw maze walls
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                if (maze.hasNorthWall(row, col)) {
                    g.drawLine(x, y, x + cellSize, y);
                }
                if (maze.hasWestWall(row, col)) {
                    g.drawLine(x, y, x, y + cellSize);
                }
                if (maze.hasSouthWall(row, col)) {
                    g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                }
                if (maze.hasEastWall(row, col)) {
                    g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
                }
            }
        }

        // Draw Player 1 (blue)
        g.setColor(Color.BLUE);
        g.fillOval(player1.getCol() * cellSize + 5, player1.getRow() * cellSize + 5, cellSize - 10, cellSize - 10);

        // Draw Player 2 (red)
        g.setColor(Color.RED);
        g.fillOval(player2.getCol() * cellSize + 5, player2.getRow() * cellSize + 5, cellSize - 10, cellSize - 10);

        // Draw Spirits (black)
        g.setColor(Color.BLACK);
        for (Sprite spirit : spirits) {
            g.fillRect(spirit.getCol() * cellSize + 10, spirit.getRow() * cellSize + 10, cellSize - 20, cellSize - 20);
        }
    }
}
