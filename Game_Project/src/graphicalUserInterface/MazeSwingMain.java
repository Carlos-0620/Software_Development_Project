package graphicalUserInterface;

import game.MazeGame;

//Run game from this class.
public class MazeSwingMain {
    public static void main(String[] args) {
       
        MazeGame game = new MazeGame();

     
        Frame frame = new Frame(game);

       
        frame.setVisible(true);
    }
}