package graphicalUserInterface;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//Child class for GameWindow.java

public class Frame extends GameWindow{

	public Frame(MazeGame game){
		super(game); //Calls for parents constructor with game reference,

		//Customisation of window settings
		setTitle("GameName"); //Sets name of the game on the 
		ImageIcon logo = new ImageIcon("logo.png"); //Creates an ImageIcon.
		setIconImage(logo.getImage()); //Changes icon of frame.
		getContentPane().setBackground(new Color(64, 64, 64)); //Changes colour of the background. 

		intializeGameUI();
	}
	
	private void initializeGameUI() {
		
	}
	private void setBackgroundColor(Color color) {
		getContentPane().setBackground(color);
	}
}
