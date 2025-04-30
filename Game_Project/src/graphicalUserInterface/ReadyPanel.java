package graphicalUserInterface;

import java.awt.*;
import javax.swing.*;

public class ReadyPanel extends JPanel {
    private Runnable onStartGame;
    private Image backgroundImage;

    public ReadyPanel(Runnable onStartGame) {
        this.onStartGame = onStartGame;

        // Load the background image
        backgroundImage = new ImageIcon(getClass().getResource("/assets/rustic_maze.jpg")).getImage();

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Maze Adventure", SwingConstants.CENTER);
        title.setFont(new Font("Papyrus", Font.BOLD, 100)); // Increased font size and bold style.
        title.setForeground(new Color(255, 223, 186)); // Light beige color.
        add(title, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20)); // 2 rows, 1 column
        buttonPanel.setOpaque(false); // Make the panel transparent.
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200)); // Add padding.

        // Play Button
        JButton playButton = new JButton(new ImageIcon(getClass().getResource("/assets/play.png")));
        playButton.setContentAreaFilled(false); // Make the button background transparent.
        playButton.setBorderPainted(false); // Remove the button border.
        playButton.setFocusPainted(false); // Remove focus border.
        playButton.addActionListener(e -> onStartGame.run());

        // Quit Button
        JButton quitButton = new JButton(new ImageIcon(getClass().getResource("/assets/quit.png")));
        quitButton.setContentAreaFilled(false); // Make the background transparent.
        quitButton.setBorderPainted(false); // Remove the button border.
        quitButton.setFocusPainted(false); // Remove focus border.
        quitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        buttonPanel.add(playButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
