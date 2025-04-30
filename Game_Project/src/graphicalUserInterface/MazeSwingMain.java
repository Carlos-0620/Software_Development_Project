package graphicalUserInterface;

import game.MazeGame;

// Run game from this class.
public class MazeSwingMain {
    public static void main(String[] args) {
        // Create the game instance
        MazeGame game = new MazeGame();

        // Create the main frame
        Frame frame = new Frame(game);

        // Make the frame visible
        frame.setVisible(true);

        // Debugging message to confirm the application is running
        System.out.println("Maze game started successfully.");
    }
}