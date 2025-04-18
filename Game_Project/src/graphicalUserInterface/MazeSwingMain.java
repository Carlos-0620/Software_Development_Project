package graphicalUserInterface;

import game.MazeGame;


public class MazeSwingMain {
    public static void main(String[] args) {
       
        MazeGame game = new MazeGame();

     
        Frame frame = new Frame(game);

       
        frame.setVisible(true);
    }
}