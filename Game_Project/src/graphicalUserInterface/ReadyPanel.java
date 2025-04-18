package graphicalUserInterface;

import java.awt.*;
import javax.swing.*;

public class ReadyPanel extends JPanel {
    private Runnable onReady;

    public ReadyPanel(Runnable onReady) {
        this.onReady = onReady;

        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Maze Game - Ready Screen", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setBackground(Color.DARK_GRAY);
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));

        JLabel instructions = new JLabel("Press 'Start' when both players are ready!", SwingConstants.CENTER);
        instructions.setFont(new Font("Arial", Font.PLAIN, 24));
        instructions.setForeground(Color.LIGHT_GRAY);
        instructionsPanel.add(instructions);

        add(instructionsPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(Color.GREEN);
        startButton.addActionListener(e -> onReady.run());
        add(startButton, BorderLayout.SOUTH);
    }
}