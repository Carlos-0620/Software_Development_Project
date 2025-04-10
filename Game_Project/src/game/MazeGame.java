package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import maze.Maze;
import sprites.Player;
import sprites.Sprite;

public class MazeGame {

    public static void main(String[] args) {
        // Create a 10x10 maze
        Maze maze = new Maze(10, 10);
        maze.generateMaze();

        // Create players
        Player player1 = new Player(0, 0);       // Player 1 at top-left
        Player player2 = new Player(0, 9);       // Player 2 at top-right

        // Add sprites
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(new Sprite(3, 3));
        sprites.add(new Sprite(5, 5));

        Scanner scanner = new Scanner(System.in);
        String input;

        // Initial maze display
        maze.printMazeWithTwoPlayers(player1, player2, sprites);

        while (true) {
            // Player 1 turn
            System.out.print("Player 1 (Move WASD, Q to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                break;
            }

            if (maze.canMove(player1, input)) {
                switch (input) {
                    case "W" -> player1.move(-1, 0);
                    case "S" -> player1.move(1, 0);
                    case "A" -> player1.move(0, -1);
                    case "D" -> player1.move(0, 1);
                }
            } else {
                System.out.println("You hit a wall!");
            }

            if (maze.isAtExit(player1)) {
                System.out.println("Congratulations Player 1! You've reached the exit! 🏆");
                break;
            }

            // Player 2 turn
            System.out.print("Player 2 (Move IJKL, T to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("T")) {
                break;
            }

            if (maze.canMove(player2, input)) {
                switch (input) {
                    case "I" -> player2.move(-1, 0);
                    case "K" -> player2.move(1, 0);
                    case "J" -> player2.move(0, -1);
                    case "L" -> player2.move(0, 1);
                }
            } else {
                System.out.println("You hit a wall!");
            }

            if (maze.isAtExit(player2)) {
                System.out.println("Congratulations Player 2! You've reached the exit! 🏆");
                break;
            }

            // Move all sprites randomly
            for (Sprite sprite : sprites) {
                sprite.moveRandomly(maze);
            }

            // Print maze after both players and sprites have moved
            maze.printMazeWithTwoPlayers(player1, player2, sprites);
        }

        scanner.close();
        System.out.println("Game Over.");
    }
}
