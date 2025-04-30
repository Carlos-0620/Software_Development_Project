// MazePanel.java
package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    private int cellSize; // Dynamically calculated cell size
    private final Image wallImage;
    private final Image floorImage;
    private final Image player1Image;
    private final Image player2Image;
    private final Image spiritImage;

    private Runnable onGameEnd; // Callback for when the game ends
    private boolean gameEnded = false;

    public MazePanel(MazeGame game) {
        this.maze = game.getMaze();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.spirits = game.getSprites();

        // Load decorative images
        wallImage = new ImageIcon(getClass().getResource("/assets/maze_wall.jpg")).getImage();
        floorImage = new ImageIcon(getClass().getResource("/assets/floor.png")).getImage();
        player1Image = new ImageIcon(getClass().getResource("/assets/player1.png")).getImage();
        player2Image = new ImageIcon(getClass().getResource("/assets/player2.png")).getImage();
        spiritImage = new ImageIcon(getClass().getResource("/assets/spirit.png")).getImage();

        setBackground(Color.BLACK);
        setFocusable(true);

        // Add KeyListener for player movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    public void setOnGameEnd(Runnable onGameEnd) {
        this.onGameEnd = onGameEnd;
    }

    public void resetGame() {
        gameEnded = false;
        // Add any other reset logic needed
        repaint();
    }

    private void handleKeyPress(KeyEvent e) {
        System.out.println("MazePanel received key event: " + KeyEvent.getKeyText(e.getKeyCode()));

        // If game has ended, ignore key presses
        if (gameEnded) {
            System.out.println("Game has ended - ignoring key press");
            return;
        }

        int keyCode = e.getKeyCode();
        boolean moved = false;

        // Player 1 movement (WASD)
        if (keyCode == KeyEvent.VK_W && maze.canMove(player1, "W")) {
            System.out.println("Player 1 moving UP");
            player1.move(-1, 0);
            moved = true;
        }
        if (keyCode == KeyEvent.VK_S && maze.canMove(player1, "S")) {
            player1.move(1, 0);
            moved = true;
        }
        if (keyCode == KeyEvent.VK_A && maze.canMove(player1, "A")) {
            player1.move(0, -1);
            moved = true;
        }
        if (keyCode == KeyEvent.VK_D && maze.canMove(player1, "D")) {
            player1.move(0, 1);
            moved = true;
        }

        // Player 2 movement (Arrow keys)
        if (keyCode == KeyEvent.VK_UP && maze.canMove(player2, "W")) {
            player2.move(-1, 0);
            moved = true;
        }
        if (keyCode == KeyEvent.VK_DOWN && maze.canMove(player2, "S")) {
            player2.move(1, 0);
            moved = true;
        }
        if (keyCode == KeyEvent.VK_LEFT && maze.canMove(player2, "A")) {
            player2.move(0, -1);
            moved = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT && maze.canMove(player2, "D")) {
            player2.move(0, 1);
            moved = true;
        }

        // Only check win condition and repaint if a player moved
        if (moved) {
            System.out.println("Player 1 position: (" + player1.getRow() + "," + player1.getCol() + ")");
            System.out.println("Player 2 position: (" + player2.getRow() + "," + player2.getCol() + ")");
            System.out.println("Exit position: (" + (maze.getHeight() - 1) + "," + (maze.getWidth() - 1) + ")");

            // Check for end of game
            if (maze.isAtExit(player1)) {
                System.out.println("Player 1 wins!");
                gameEnded = true;
                JOptionPane.showMessageDialog(this, "Player 1 wins!");
                if (onGameEnd != null) {
                    onGameEnd.run();
                }
            } else if (maze.isAtExit(player2)) {
                System.out.println("Player 2 wins!");
                gameEnded = true;
                JOptionPane.showMessageDialog(this, "Player 2 wins!");
                if (onGameEnd != null) {
                    onGameEnd.run();
                }
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate the cell size dynamically to fit the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        cellSize = Math.min(panelWidth / maze.getWidth(), panelHeight / maze.getHeight());

        // Center the maze in the panel
        int xOffset = (panelWidth - (maze.getWidth() * cellSize)) / 2;
        int yOffset = (panelHeight - (maze.getHeight() * cellSize)) / 2;

        // Draw the maze grid
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                int x = xOffset + col * cellSize;
                int y = yOffset + row * cellSize;

                // Draw floor
                g.drawImage(floorImage, x, y, cellSize, cellSize, this);

                // Draw walls
                if (maze.hasNorthWall(row, col)) {
                    g.drawImage(wallImage, x, y, cellSize, 5, this);
                }
                if (maze.hasWestWall(row, col)) {
                    g.drawImage(wallImage, x, y, 5, cellSize, this);
                }
                if (maze.hasSouthWall(row, col)) {
                    g.drawImage(wallImage, x, y + cellSize - 5, cellSize, 5, this);
                }
                if (maze.hasEastWall(row, col)) {
                    g.drawImage(wallImage, x + cellSize - 5, y, 5, cellSize, this);
                }
            }
        }

        // Draw players
        g.drawImage(player1Image, xOffset + player1.getCol() * cellSize + 5, yOffset + player1.getRow() * cellSize + 5,
                cellSize - 10, cellSize - 10, this);
        g.drawImage(player2Image, xOffset + player2.getCol() * cellSize + 5, yOffset + player2.getRow() * cellSize + 5,
                cellSize - 10, cellSize - 10, this);

        // Draw spirits
        for (Sprite spirit : spirits) {
            g.drawImage(spiritImage, xOffset + spirit.getCol() * cellSize + 10,
                    yOffset + spirit.getRow() * cellSize + 10, cellSize - 20, cellSize - 20, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Dynamically calculate the preferred size based on the maze dimensions
        return new Dimension(maze.getWidth() * cellSize, maze.getHeight() * cellSize);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(() -> {
            boolean focusGained = requestFocusInWindow();
            System.out.println("MazePanel focus requested: " + focusGained);
            System.out.println("MazePanel has focus: " + hasFocus());
        });
    }
}
