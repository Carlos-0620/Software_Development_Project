// MazePanel.java
package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import maze.Maze;
import sprites.Player;
import sprites.Sprite;

public class MazePanel extends JPanel {
    private final Maze maze;
    private final Player player1;
    private final Player player2;
    private final List<Sprite> spirits;

    private final int cellSize = 35;  // Adjusted for the larger maze
    private final Set<String> pressedKeys = new HashSet<>();
    private final Map<Player, Long> frozenUntil = new HashMap<>();

    private final Image player1Image;
    private final Image player2Image;
    private final Image spiritImage;
    private final Image wallImage;
    private final Image floorImage;

    public MazePanel(MazeGame game) {
        this.maze = game.getMaze();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.spirits = game.getSprites();

        // Load images
        player1Image = new ImageIcon(getClass().getResource("/assets/player1.png")).getImage();
        player2Image = new ImageIcon(getClass().getResource("/assets/player2.png")).getImage();
        spiritImage = new ImageIcon(getClass().getResource("/assets/spirit.png")).getImage();
        wallImage = new ImageIcon(getClass().getResource("/assets/maze_wall.jpg")).getImage();
        floorImage = new ImageIcon(getClass().getResource("/assets/floor.png")).getImage();

        setPreferredSize(new Dimension(maze.getWidth() * cellSize, maze.getHeight() * cellSize));
        setBackground(Color.WHITE);
        setFocusable(true);

        setupKeyBindings();

        startGameLoop();

        startSpriteMovementLoop();

        requestFocusInWindow();

    }

    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // Player 1 controls (WASD)
        bindKey(im, am, "W", true);
        bindKey(im, am, "W", false);
        bindKey(im, am, "A", true);
        bindKey(im, am, "A", false);
        bindKey(im, am, "S", true);
        bindKey(im, am, "S", false);
        bindKey(im, am, "D", true);
        bindKey(im, am, "D", false);

        // Player 2 controls (IJKL)
        bindKey(im, am, "I", true);
        bindKey(im, am, "I", false);
        bindKey(im, am, "J", true);
        bindKey(im, am, "J", false);
        bindKey(im, am, "K", true);
        bindKey(im, am, "K", false);
        bindKey(im, am, "L", true);
        bindKey(im, am, "L", false);
    }

    private void bindKey(InputMap im, ActionMap am, String key, boolean pressed) {
        String actionKey = key + (pressed ? "_PRESSED" : "_RELEASED");
        KeyStroke keyStroke = pressed ? KeyStroke.getKeyStroke(key) : KeyStroke.getKeyStroke("released " + key);
        im.put(keyStroke, actionKey);
        am.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pressed)
                    pressedKeys.add(key);
                else
                    pressedKeys.remove(key);
            }
        });
    }

    private void startGameLoop() {
        Timer timer = new Timer(80, e -> {
            updatePlayerMovement();
            checkSpriteCollisions();
            checkWinConditions();
            repaint();
        });
        timer.start();
    }

    private void startSpriteMovementLoop() {
        Timer spriteTimer = new Timer(1000, e -> {
            for (Sprite sprite : spirits) {
                sprite.moveRandomly(maze);
            }
        });

        spriteTimer.start();

    }

    private void updatePlayerMovement() {
        if (!isFrozen(player1)) {
            if (pressedKeys.contains("W"))
                tryMovePlayer(player1, "W");
            if (pressedKeys.contains("A"))
                tryMovePlayer(player1, "A");
            if (pressedKeys.contains("S"))
                tryMovePlayer(player1, "S");
            if (pressedKeys.contains("D"))
                tryMovePlayer(player1, "D");
        }

        if (!isFrozen(player2)) {
            if (pressedKeys.contains("I"))
                tryMovePlayer(player2, "W");
            if (pressedKeys.contains("J"))
                tryMovePlayer(player2, "A");
            if (pressedKeys.contains("K"))
                tryMovePlayer(player2, "S");
            if (pressedKeys.contains("L"))
                tryMovePlayer(player2, "D");
        }
    }

    private boolean isFrozen(Player player) {
        Long until = frozenUntil.getOrDefault(player, 0L);
        return System.currentTimeMillis() < until;
    }

    private void freezePlayer(Player player) {
        frozenUntil.put(player, System.currentTimeMillis() + 5000); // freeze 5 seconds
    }

    private void tryMovePlayer(Player player, String direction) {
        if (maze.canMove(player, direction)) {
            switch (direction) {
                case "W" -> player.move(-1, 0);
                case "S" -> player.move(1, 0);
                case "A" -> player.move(0, -1);
                case "D" -> player.move(0, 1);
            }
        }
    }

    private void checkSpriteCollisions() {
        for (Sprite sprite : spirits) {
            if (sprite.getRow() == player1.getRow() && sprite.getCol() == player1.getCol()) {
                freezePlayer(player1);
            }
            if (sprite.getRow() == player2.getRow() && sprite.getCol() == player2.getCol()) {
                freezePlayer(player2);
            }
        }
    }

    private void checkWinConditions() {
        if (player1.getRow() == maze.getHeight() - 1 && player1.getCol() == maze.getWidth() - 1) {
            JOptionPane.showMessageDialog(this, "🎉 Player 1 wins! 🎉");
            System.exit(0);
        }
        if (player2.getRow() == 0 && player2.getCol() == 0) {
            JOptionPane.showMessageDialog(this, "🎉 Player 2 wins! 🎉");
            System.exit(0);

        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw maze
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                // Draw floor
                g.drawImage(floorImage, x, y, cellSize, cellSize, this);

                // Draw walls
                if (maze.hasNorthWall(row, col)) {
                    g.drawImage(wallImage, x, y, cellSize, cellSize / 8, this);
                }
                if (maze.hasWestWall(row, col)) {
                    g.drawImage(wallImage, x, y, cellSize / 8, cellSize, this);
                }
                if (maze.hasSouthWall(row, col)) {
                    g.drawImage(wallImage, x, y + cellSize - cellSize / 8, cellSize, cellSize / 8, this);
                }
                if (maze.hasEastWall(row, col)) {
                    g.drawImage(wallImage, x + cellSize - cellSize / 8, y, cellSize / 8, cellSize, this);
                }
            }
        }

        // Draw players
        int spriteMargin = cellSize / 10;
        if (!isFrozen(player1)) {
            g.drawImage(player1Image,
                    player1.getCol() * cellSize + spriteMargin,
                    player1.getRow() * cellSize + spriteMargin,
                    cellSize - 2 * spriteMargin,
                    cellSize - 2 * spriteMargin,
                    this);
        } else {
            // Draw frozen player1 with transparency
            Graphics2D g2d = (Graphics2D) g;
            Composite oldComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(player1Image,
                    player1.getCol() * cellSize + spriteMargin,
                    player1.getRow() * cellSize + spriteMargin,
                    cellSize - 2 * spriteMargin,
                    cellSize - 2 * spriteMargin,
                    this);
            g2d.setComposite(oldComposite);
        }

        if (!isFrozen(player2)) {
            g.drawImage(player2Image,
                    player2.getCol() * cellSize + spriteMargin,
                    player2.getRow() * cellSize + spriteMargin,
                    cellSize - 2 * spriteMargin,
                    cellSize - 2 * spriteMargin,
                    this);
        } else {
            // Draw frozen player2 with transparency
            Graphics2D g2d = (Graphics2D) g;
            Composite oldComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(player2Image,
                    player2.getCol() * cellSize + spriteMargin,
                    player2.getRow() * cellSize + spriteMargin,
                    cellSize - 2 * spriteMargin,
                    cellSize - 2 * spriteMargin,
                    this);
            g2d.setComposite(oldComposite);
        }

        // Draw spirits
        for (Sprite spirit : spirits) {
            g.drawImage(spiritImage,
                    spirit.getCol() * cellSize + spriteMargin,
                    spirit.getRow() * cellSize + spriteMargin,
                    cellSize - 2 * spriteMargin,
                    cellSize - 2 * spriteMargin,
                    this);
        }
    }
}
