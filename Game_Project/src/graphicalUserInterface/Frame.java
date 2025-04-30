package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private final ReadyPanel readyPanel;
    private InstructionsPanel instructionsPanel;
    private final MazePanel mazePanel;

    public Frame(MazeGame game) {
        super("Maze Adventure");

        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/assets/logo.png"));
            if (logo.getImage() != null) {
                setIconImage(logo.getImage());
            } else {
                System.err.println("Could not load logo image");
            }
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        // Set up CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ready Panel
        readyPanel = new ReadyPanel(() -> cardLayout.show(cardPanel, "Instructions"));
        cardPanel.add(readyPanel, "Ready");

        // Instructions Panel
        instructionsPanel = new InstructionsPanel(() -> cardLayout.show(cardPanel, "Game"));
        cardPanel.add(instructionsPanel, "Instructions");

        // Maze Panel (Game Panel)
        mazePanel = new MazePanel(game);
        cardPanel.add(mazePanel, "Game");

        // Add the card panel to the frame
        add(cardPanel);

        // Show the ready panel initially
        cardLayout.show(cardPanel, "Ready");
    }

    private void startGame() {
        mazePanel.requestFocusInWindow(); // Ensure MazePanel grabs focus for key input
        cardLayout.show(cardPanel, "Game");
    }
}
