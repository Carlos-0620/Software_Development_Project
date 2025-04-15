package graphicalUserInterface;

import game.MazeGame;

//MazeSwingMain is a test class to launch the Swing version of the maze game.
public class MazeSwingMain {
    public static void main(String[] args) {
        // Create an instance of MazeGame
        MazeGame game = new MazeGame();

        // Create the main game frame passing the game logic
        Frame frame = new Frame(game);

        // Set the frame visible to start the GUI
        frame.setVisible(true);
    }
}