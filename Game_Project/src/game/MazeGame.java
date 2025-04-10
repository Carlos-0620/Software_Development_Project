package game;

import java.util.Scanner;
import maze.Maze;
import sprites.Player;

public class MazeGame {

    public static void main(String[] args) {
        // Maze creation placeholder
        Maze maze = new Maze(10, 10);
        maze.generateMaze();

        // Creating 2 players:
        // Player 1 starts at (0,0), player 2 — (9,9) (10x10 maze)
        Player player1 = new Player(0, 0);
        Player player2 = new Player(9, 9);

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            // Checking if player 1 reached the exit
            if (maze.isAtExit(player1)) {
                System.out.println("Congratulations Player 1! You've reached the exit! \uD83C\uDFC6");
                break;
            }

            // Player 1 turn
            System.out.print("(Move WASD, Q to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                break; // Player 1 wants to quit
            }

            // Movemonet logic (if canMove == true, than move)
            if (maze.canMove(player1, input)) {
                switch (input) {
                    case "W": player1.move(-1, 0); break;
                    case "S": player1.move(1, 0);  break;
                    case "A": player1.move(0, -1); break;
                    case "D": player1.move(0, 1);  break;
                    default:
                        // Invalid input
                        System.out.println("Invalid command for Player 1!");
                }
            } else {
                System.out.println("You hit a wall!");
            }

            // Checking if player 2 reached the exit
            if (maze.isAtExit(player2)) {
                System.out.println("Congratulations Player 2! You've reached the exit! \uD83C\uDFC6");
                break;
            }

            // Player 2 turn
            System.out.print("(Move IJKL, T to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("T")) {
                break; // Pla yer 2 wants to quit
            }

            // Movemonet logic (if canMove == true, than move)
            if (maze.canMove(player2, input)) {
                switch (input) {
                    case "I": player2.move(-1, 0); break;
                    case "K": player2.move(1, 0);  break;
                    case "J": player2.move(0, -1); break;
                    case "L": player2.move(0, 1);  break;
                    default:
                        System.out.println("Invalid command for Player 2!");
                }
            } else {
                System.out.println("You hit a wall!");
            }
        }

        scanner.close();
        System.out.println("Game Over.");
    }
}