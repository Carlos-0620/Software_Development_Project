package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private final ReadyPanel readyPanel;
    private InstructionsPanel instructionsPanel;
    private final MazePanel mazePanel;

    public Frame(MazeGame game) {
        super("Maze Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Make window fullscreen

        // Window configuration
        ImageIcon logo = new ImageIcon(getClass().getResource("/assets/logo.png"));
        setIconImage(logo.getImage());
        getContentPane().setBackground(new Color(64, 64, 64));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1080, 720); // Keep existing size
        setLocationRelativeTo(null);

        // Layout and panel setup
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ready Panel
        readyPanel = new ReadyPanel(() -> {
            cardLayout.show(cardPanel, "Instructions");

            // 🔧 Ensure instructions panel receives keyboard focus
            SwingUtilities.invokeLater(() -> {
                instructionsPanel.requestFocusInWindow();
                System.out.println("Focus requested for InstructionsPanel.");
            });
        });
        cardPanel.add(readyPanel, "Ready");

        // Instructions Panel
        instructionsPanel = new InstructionsPanel(() -> startGame());
        cardPanel.add(instructionsPanel, "Instructions");

        // Maze Panel
        mazePanel = new MazePanel(game);
        cardPanel.add(mazePanel, "Game");

        add(cardPanel);
        setVisible(true);

        // Show the ready screen initially
        cardLayout.show(cardPanel, "Ready");
    }

    private void startGame() {
        mazePanel.requestFocusInWindow(); // Make sure key input works for game
        cardLayout.show(cardPanel, "Game");
    }
}
