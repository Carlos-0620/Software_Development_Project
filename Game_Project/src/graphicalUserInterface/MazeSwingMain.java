package graphicalUserInterface;

import game.MazeGame;
import javax.swing.SwingUtilities;

// Run game from this class.
public class MazeSwingMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Starting Maze Game...");

                // Create the game instance
                MazeGame game = new MazeGame();
                System.out.println("Game instance created");

                // Create the main frame
                Frame frame = new Frame(game);
                System.out.println("Frame created");

                // Make the frame visible
                frame.setVisible(true);
                System.out.println("Frame made visible");

                // Debugging message to confirm the application is running
                System.out.println("Maze game started successfully.");
            } catch (Exception e) {
                System.err.println("Error starting game: ");
                e.printStackTrace();
            }
        });
    }
}