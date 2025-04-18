package graphicalUserInterface;

import game.MazeGame;

import java.awt.*;
import javax.swing.*;
//Child class for GameWindow.java

public class Frame extends GameWindow{
	  private CardLayout cardLayout;
    private JPanel cardPanel;
    
    private MazePanel mazePanel;

	public Frame(MazeGame game){
		super(game); //Calls for parents constructor with game reference,

		//Customisation of window settings
		setTitle("GameName"); //Sets name of the game on the
		ImageIcon logo = new ImageIcon("logo.png"); //Creates an ImageIcon.
		setIconImage(logo.getImage()); //Changes icon of frame.
		getContentPane().setBackground(new Color(64, 64, 64)); //Changes colour of the background.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1080, 720);
		setLocationRelativeTo(null);
		setVisible(true);

		initializeGameUI();

	
		cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

 
        
        mazePanel = new MazePanel(game);
        cardPanel.add(mazePanel, "Game");

        add(cardPanel);
        setVisible(true);

    
        cardLayout.show(cardPanel, "Ready");
	}
	private void startGame(MazeGame game) {
   
        mazePanel.requestFocusInWindow();
        cardLayout.show(cardPanel, "Game");
    }
	private void initializeGameUI() {
		
	}
	private void setBackgroundColor(Color color) {
		getContentPane().setBackground(color);
	}
}
