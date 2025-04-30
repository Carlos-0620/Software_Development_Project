package graphicalUserInterface;

import game.MazeGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Frame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ReadyPanel readyPanel;
    private MazePanel mazePanel;

    public Frame(MazeGame game) {
        super("Maze Game");

        //Window configuration.
        ImageIcon logo = new ImageIcon(getClass().getResource("/assets/logo.png"));
        setIconImage(logo.getImage());
        getContentPane().setBackground(new Color(64, 64, 64));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        //Add menu bar.
        createMenuBar();

        //Layout and panel setup.
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        readyPanel = new ReadyPanel(() -> startGame());
        cardPanel.add(readyPanel, "Ready");

        mazePanel = new MazePanel(game);
        cardPanel.add(mazePanel, "Game");

        add(cardPanel);
        setVisible(true);

        //Show the ready screen initially.
        cardLayout.show(cardPanel, "Ready");
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Game menu.
        JMenu gameMenu = new JMenu("Game");
        JMenuItem startMenuItem = new JMenuItem("Start Game");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        //Add action listeners.
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameMenu.add(startMenuItem);
        gameMenu.add(exitMenuItem);

        //Help menu.
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Frame.this, 
                    "Maze Game\nVersion 1.0\nDeveloped by C.D.M.G ", 
                    "About", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        helpMenu.add(aboutMenuItem);

        //Add menus to the menu bar.
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        //Set the menu bar.
        setJMenuBar(menuBar);
    }

    private void startGame() {
        mazePanel.requestFocusInWindow(); //Ensure MazePanel grabs focus for key input.
        cardLayout.show(cardPanel, "Game");
    }

    public void setBackgroundColor(Color color) {
        getContentPane().setBackground(color);
    }
}
