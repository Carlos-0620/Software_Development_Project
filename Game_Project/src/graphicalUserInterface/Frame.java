package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ReadyPanel readyPanel;
    private MazePanel mazePanel;

    public Frame(MazeGame game) {
        super("Maze Game");

        // Window configuration
        ImageIcon logo = new ImageIcon(getClass().getResource("/assets/logo.png"));
        setIconImage(logo.getImage());
        getContentPane().setBackground(new Color(64, 64, 64));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        // Layout and panel setup
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        readyPanel = new ReadyPanel(() -> startGame());
        cardPanel.add(readyPanel, "Ready");

        mazePanel = new MazePanel(game);
        cardPanel.add(mazePanel, "Game");

        add(cardPanel);
        setVisible(true);

        // Show the ready screen initially
        cardLayout.show(cardPanel, "Ready");
    }

    private void startGame() {
        mazePanel.requestFocusInWindow(); // Ensure MazePanel grabs focus for key input.
        cardLayout.show(cardPanel, "Game");
    }

    public void setBackgroundColor(Color color) {
        getContentPane().setBackground(color);
    }
}
