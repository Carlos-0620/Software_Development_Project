package graphicalUserInterface;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class ReadyPanel extends JPanel {
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private JLabel player1Status;
    private JLabel player2Status;
    private Runnable onBothReady;
    private Image backgroundImage;

    public ReadyPanel(Runnable onBothReady) {
        this.onBothReady = onBothReady;

        // Load the background image
        backgroundImage = new ImageIcon(getClass().getResource("/assets/rustic_maze.jpg")).getImage();

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Maze Adventure", SwingConstants.CENTER);
        title.setFont(new Font("Papyrus", Font.BOLD, 48)); // Rustic-style font.
        title.setForeground(new Color(255, 223, 186)); // Light beige color.
        add(title, BorderLayout.NORTH);

        // Status Panel
        JPanel statusPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        statusPanel.setOpaque(false); // Make the panel transparent.

        player1Status = new JLabel("Player 1 not ready (press WASD)", SwingConstants.CENTER);
        player1Status.setFont(new Font("Serif", Font.PLAIN, 24));
        player1Status.setForeground(new Color(255, 223, 186)); // Light beige color.

        player2Status = new JLabel("Player 2 not ready (press arrow keys)", SwingConstants.CENTER);
        player2Status.setFont(new Font("Serif", Font.PLAIN, 24));
        player2Status.setForeground(new Color(255, 223, 186)); // Light beige color.

        statusPanel.add(player1Status);
        statusPanel.add(player2Status);
        add(statusPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false); // Make the panel transparent.
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding.

        // Start Button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Papyrus", Font.BOLD, 24));
        startButton.setBackground(new Color(85, 107, 47)); // Olive green.
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> onBothReady.run());

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Papyrus", Font.BOLD, 24));
        quitButton.setBackground(new Color(139, 0, 0)); // Dark red.
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Key Listener for Player Readiness
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!player1Ready && (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_S
                || keyCode == KeyEvent.VK_D)) {
            player1Ready = true;
            player1Status.setText("Player 1 is ready!");
            checkReadyStatus();
        } else if (!player2Ready && (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)) {
            player2Ready = true;
            player2Status.setText("Player 2 is ready!");
            checkReadyStatus();
        }
    }

    private void checkReadyStatus() {
        if (player1Ready && player2Ready) {
            onBothReady.run();
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
