package graphicalUserInterface;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.MazeGame;


public class MazePanel extends JPanel {
    private MazeGame game; // Reference to the game logic, including maze, players, and sprites

    public MazePanel(MazeGame game) {
        this.game = game;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                System.out.println("Key pressed: " + keyCode);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Maze Panel", 50, 50);
    }
}
