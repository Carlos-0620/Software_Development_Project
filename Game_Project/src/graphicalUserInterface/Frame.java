package graphicalUserInterface;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//Child class for GameWindow.java

public class Frame extends JFrame{

	Frame(){

		this.setTitle("GameName"); //Sets name of the game on the this.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits out of the application.
		this.setResizable(false); //Prevents this form being resized.
		this.setSize(1000,700); //Sets the x and y dimensions of the this.
		this.setVisible(true); //Makes this visible.

		ImageIcon logo = new ImageIcon("logo.png"); //Creates an ImageIcon.
		this.setIconImage(logo.getImage()); //Changes icon of this.
		this.getContentPane().setBackground(new Color(64, 64, 64)); //Changes colour of the background. 

	}
}
