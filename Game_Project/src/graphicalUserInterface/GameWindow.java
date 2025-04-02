package graphicalUserInterface;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GameWindow {

	public static void main(String[] args) {
		
	JFrame frame = new JFrame(); //Creates a frame.
	frame.setTitle("GameName"); //Sets name of the game on the frame.
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits out of the application.
	frame.setResizable(false); //Prevents frame form being resized.
	frame.setSize(1000,700); //Sets the x and y dimensions of the frame.
	frame.setVisible(true); //Makes frame visible.
	
	ImageIcon logo = new ImageIcon("logo.png"); //Creates an ImageIcon.
	frame.setIconImage(logo.getImage()); //Changes icon of frame.
	}
}
