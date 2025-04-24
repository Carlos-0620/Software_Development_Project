package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
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

    private final int cellSize = 30;
    private final Set<String> pressedKeys = new HashSet<>();

    public MazePanel(MazeGame game) {
        this.maze = game.getMaze();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.spirits = game.getSprites();

        setPreferredSize(new Dimension(maze.getWidth() * cellSize, maze.getHeight() * cellSize));
        setBackground(Color.WHITE);
        setFocusable(true);

        setupKeyBindings();
        startGameLoop();

        requestFocusInWindow(); // Grab focus to receive key events
    }

    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // Player 1: WASD
        bindKey(im, am, "W", true);  bindKey(im, am, "W", false);
        bindKey(im, am, "A", true);  bindKey(im, am, "A", false);
        bindKey(im, am, "S", true);  bindKey(im, am, "S", false);
        bindKey(im, am, "D", true);  bindKey(im, am, "D", false);

        // Player 2: IJKL
        bindKey(im, am, "I", true);  bindKey(im, am, "I", false);
        bindKey(im, am, "J", true);  bindKey(im, am, "J", false);
        bindKey(im, am, "K", true);  bindKey(im, am, "K", false);
        bindKey(im, am, "L", true);  bindKey(im, am, "L", false);
    }

    private void bindKey(InputMap im, ActionMap am, String key, boolean pressed) {
        String actionKey = key + (pressed ? "_PRESSED" : "_RELEASED");
        KeyStroke keyStroke = pressed ? KeyStroke.getKeyStroke(key) : KeyStroke.getKeyStroke("released " + key);
        im.put(keyStroke, actionKey);
        am.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pressed) pressedKeys.add(key);
                else pressedKeys.remove(key);
            }
        });
    }

    private void startGameLoop() {
        Timer timer = new Timer(80, e -> {
            updatePlayerMovement();
            repaint();
        });
        timer.start();
    }

    private void updatePlayerMovement() {
        // Player 1
        if (pressedKeys.contains("W")) tryMovePlayer(player1, "W");
        if (pressedKeys.contains("A")) tryMovePlayer(player1, "A");
        if (pressedKeys.contains("S")) tryMovePlayer(player1, "S");
        if (pressedKeys.contains("D")) tryMovePlayer(player1, "D");

        // Player 2
        if (pressedKeys.contains("I")) tryMovePlayer(player2, "W");
        if (pressedKeys.contains("J")) tryMovePlayer(player2, "A");
        if (pressedKeys.contains("K")) tryMovePlayer(player2, "S");
        if (pressedKeys.contains("L")) tryMovePlayer(player2, "D");
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                if (maze.hasNorthWall(row, col)) g.drawLine(x, y, x + cellSize, y);
                if (maze.hasWestWall(row, col)) g.drawLine(x, y, x, y + cellSize);
                if (maze.hasSouthWall(row, col)) g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                if (maze.hasEastWall(row, col)) g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
            }
        }

        g.setColor(Color.BLUE);
        g.fillOval(player1.getCol() * cellSize + 5, player1.getRow() * cellSize + 5, cellSize - 10, cellSize - 10);

        g.setColor(Color.RED);
        g.fillOval(player2.getCol() * cellSize + 5, player2.getRow() * cellSize + 5, cellSize - 10, cellSize - 10);

        g.setColor(Color.BLACK);
        for (Sprite spirit : spirits) {
            g.fillRect(spirit.getCol() * cellSize + 10, spirit.getRow() * cellSize + 10, cellSize - 20, cellSize - 20);
        }
    }
}
