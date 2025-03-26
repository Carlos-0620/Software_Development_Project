package game;

import java.util.Scanner;
import maze.Maze;
import sprites.Player;

public class MazeGame {
    public static void main(String[] args) {
        Maze maze = new Maze(10, 10);
        maze.generateMaze();
        Player player = new Player(0, 0);

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            maze.printMaze(player);
            System.out.print("Move (WASD, Q to quit): ");
            input = scanner.nextLine().toUpperCase();

            if (input.equals("Q")) break;

            if (maze.canMove(player, input)) {
                switch (input) {
                    case "W" -> player.move(-1, 0);
                    case "S" -> player.move(1, 0);
                    case "A" -> player.move(0, -1);
                    case "D" -> player.move(0, 1);
                }
            } else {
                System.out.println("You hit a wall!");
            }
        }
        scanner.close();
        System.out.println("Game Over.");
    }
}
