package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ReadyPanel readyPanel;
    private InstructionsPanel instructionsPanel;
    private MazePanel mazePanel;

    public Frame(MazeGame game) {
        super("Maze Game");

        // Window configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        // Set up CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ready Panel (Menu Panel)
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

        // Add focus listener to debug focus changes
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addPropertyChangeListener("focusOwner", evt -> {
                    System.out.println("Focus changed to: " + evt.getNewValue());
                });

        // Add window listener to ensure focus
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("Window activated");
                Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                System.out.println("Current focus owner: " + focusOwner);
            }
        });
    }
}
