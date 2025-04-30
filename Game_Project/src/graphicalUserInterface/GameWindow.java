package graphicalUserInterface;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import game.MazeGame;



public class GameWindow extends JFrame {
	
	protected MazeGame game; //Imported game logic.
	
	public GameWindow(MazeGame game) {
		this.game = game;
		
    //Window Configuration.
	setTitle("GameName"); //Sets name of the game on the frame.
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits out of the application.
	setResizable(false); //Prevents frame form being resized.
	setSize(1000,700); //Sets the x and y dimensions of the 
	setVisible(true); //Makes frame visible.
	setLocationRelativeTo(null); //Center the window.
	
	//Window appearance.
	ImageIcon logo = new ImageIcon("logo.png"); //Creates an ImageIcon.
	setIconImage(logo.getImage()); //Changes icon of frame.
	getContentPane().setBackground(new Color(64, 64, 64)); //Changes colour of the background. 

	}
}
