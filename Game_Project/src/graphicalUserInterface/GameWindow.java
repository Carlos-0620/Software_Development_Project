package graphicalUserInterface;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import game.MazeGame;



public class GameWindow extends JFrame {
	
	protected MazeGame game; //Imported game logic.
	
	public GameWindow(MazeGame game) {
        this.game = game;

        // Конфигурация окна
        setTitle("GameName"); // Имя игры в заголовке окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Закрытие приложения при выходе
        setResizable(false); // Отключение изменения размера окна
        setSize(1000, 700); // Установка размеров окна
        setVisible(true); // Отображение окна
        setLocationRelativeTo(null); // Центровка окна на экране

        // Настройка внешнего вида окна
        ImageIcon logo = new ImageIcon("logo.png"); // Загрузка иконки
        setIconImage(logo.getImage()); // Установка иконки окна
        getContentPane().setBackground(new Color(64, 64, 64)); // Установка фона
    }
}
