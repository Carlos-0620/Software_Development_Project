package graphicalUserInterface;

import game.MazeGame;
import javax.swing.SwingUtilities;

public class MazeSwingMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the game instance
            MazeGame game = new MazeGame();

            // Create the main frame
            Frame frame = new Frame(game);

            // Pack the frame to fit the maze content
            frame.pack();

            // Center the frame on screen
            frame.setLocationRelativeTo(null);

            // Make the frame visible
            frame.setVisible(true);

            System.out.println("Maze game started successfully.");
        });
    }
}