package graphicalUserInterface;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class InstructionsPanel extends JPanel {
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private JLabel player1Status;
    private JLabel player2Status;
    private Runnable onBothReady;
    private Image backgroundImage;

    public InstructionsPanel(Runnable onBothReady) {
        this.onBothReady = onBothReady;

        // Load the background image
        backgroundImage = new ImageIcon(getClass().getResource("/assets/wall.png")).getImage();

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Instructions", SwingConstants.CENTER);
        title.setFont(new Font("Papyrus", Font.BOLD, 60)); // Larger and bolder title.
        title.setForeground(new Color(255, 223, 186)); // Light beige color.
        add(title, BorderLayout.NORTH);

        // Instructions Text
        JTextArea instructions = new JTextArea(
                "Player 1 controls: WASD\n" +
                        "Player 2 controls: Arrow keys\n\n" +
                        "The first player to reach the end goal wins!\n" +
                        "Be careful of the spirits in the maze!");
        instructions.setFont(new Font("Papyrus", Font.BOLD, 28)); // Bigger and bolder font.
        instructions.setForeground(new Color(255, 140, 0)); // Deeper orange color.
        instructions.setBackground(new Color(0, 0, 0, 0)); // Transparent background.
        instructions.setEditable(false);
        instructions.setFocusable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        add(instructions, BorderLayout.CENTER);

        // Status Panel
        JPanel statusPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        statusPanel.setOpaque(false); // Transparent panel.

        player1Status = new JLabel("Player 1 not ready (press WASD)", SwingConstants.CENTER);
        player1Status.setFont(new Font("Papyrus", Font.BOLD, 24)); // Bolder font for readiness status.
        player1Status.setForeground(Color.BLACK); // Black when not ready.

        player2Status = new JLabel("Player 2 not ready (press arrow keys)", SwingConstants.CENTER);
        player2Status.setFont(new Font("Papyrus", Font.BOLD, 24)); // Bolder font for readiness status.
        player2Status.setForeground(Color.BLACK); // Black when not ready.

        statusPanel.add(player1Status);
        statusPanel.add(player2Status);
        add(statusPanel, BorderLayout.SOUTH);

        // Key Listener for Player Readiness
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode()); // Debugging key press.
                handleKeyPress(e);
            }
        });
    }

    private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!player1Ready && (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_S
                || keyCode == KeyEvent.VK_D)) {
            player1Ready = true;
            player1Status.setText("Player 1 is ready!");
            player1Status.setForeground(new Color(0, 100, 0)); // Dark green when ready.
            System.out.println("Player 1 is ready!"); // Debugging readiness.
            checkReadyStatus();
        } else if (!player2Ready && (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)) {
            player2Ready = true;
            player2Status.setText("Player 2 is ready!");
            player2Status.setForeground(new Color(0, 100, 0)); // Dark green when ready.
            System.out.println("Player 2 is ready!"); // Debugging readiness.
            checkReadyStatus();
        }
    }

    private void checkReadyStatus() {
        if (player1Ready && player2Ready) {
            System.out.println("Both players are ready!"); // Debugging readiness.
            onBothReady.run();
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow(); // Request focus when the panel is displayed.
        System.out.println("InstructionsPanel has focus: " + hasFocus()); // Debugging focus.
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