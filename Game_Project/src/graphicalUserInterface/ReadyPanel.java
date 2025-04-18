package graphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ReadyPanel extends JPanel {
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private JLabel player1Status;
    private JLabel player2Status;
    private Runnable onBothReady;

    public ReadyPanel(Runnable onBothReady) {
        this.onBothReady = onBothReady;

        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Maze Game", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1));
        statusPanel.setBackground(Color.DARK_GRAY);

        player1Status = new JLabel("Player 1 not ready (press WASD)", SwingConstants.CENTER);
        player1Status.setFont(new Font("Arial", Font.PLAIN, 24));
        player1Status.setForeground(Color.LIGHT_GRAY);

        player2Status = new JLabel("Player 2 not ready (press arrow keys)", SwingConstants.CENTER);
        player2Status.setFont(new Font("Arial", Font.PLAIN, 24));
        player2Status.setForeground(Color.LIGHT_GRAY);

        statusPanel.add(player1Status);
        statusPanel.add(player2Status);

        add(statusPanel, BorderLayout.CENTER);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (!player1Ready && (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_A ||
                        keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_D)) {
                    player1Ready = true;
                    player1Status.setText("Player 1 is ready");
                    checkReadyStatus();
                }

                if (!player2Ready && (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||
                        keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)) {
                    player2Ready = true;
                    player2Status.setText("Player 2 is ready!");
                    checkReadyStatus();
                }
            }
        });
    }

    private void checkReadyStatus() {
        if (player1Ready && player2Ready) {
            onBothReady.run(); 
        }
    }
}