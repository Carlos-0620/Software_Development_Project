package game;

import java.util.ArrayList;
import java.util.Scanner;
import maze.Maze;
import sprites.Player;

public class MazeGame {

    public static void main(String[] args) {
        // Maze creation
        Maze maze = new Maze(10, 10);
        maze.generateMaze();

        // Create 2 players:
        Player player1 = new Player(0, 0);       // Player 1 at top-left
        Player player2 = new Player(0, 9);        // Player 2 at bottom-right

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            // Print the current maze state
            maze.printMazeWithTwoPlayers(player1, player2, new ArrayList<>()); // Empty list for now

            // Check if Player 1 reached the exit
            if (maze.isAtExit(player1)) {
                System.out.println("Congratulations Player 1! You've reached the exit! 🏆");
                break;
            }

            // Player 1 turn
            System.out.print("Player 1 (Move WASD, Q to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                break;
            }

            switch (input) {
                case "W":
                case "A":
                case "S":
                case "D":
                    if (maze.canMove(player1, input)) {
                        switch (input) {
                            case "W": player1.move(-1, 0); break;
                            case "S": player1.move(1, 0); break;
                            case "A": player1.move(0, -1); break;
                            case "D": player1.move(0, 1); break;
                        }
                    } else {
                        System.out.println("You hit a wall!");
                    }
                    break;
                default:
                    System.out.println("Invalid command for Player 1!");
            }

            // Check if Player 2 reached the exit
            if (maze.isAtExit(player2)) {
                System.out.println("Congratulations Player 2! You've reached the exit! 🏆");
                break;
            }

            // Player 2 turn
            System.out.print("Player 2 (Move IJKL, T to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("T")) {
                break;
            }

            switch (input) {
                case "I":
                case "J":
                case "K":
                case "L":
                    if (maze.canMove(player2, input)) {
                        switch (input) {
                            case "I": player2.move(-1, 0); break;
                            case "K": player2.move(1, 0); break;
                            case "J": player2.move(0, -1); break;
                            case "L": player2.move(0, 1); break;
                        }
                    } else {
                        System.out.println("You hit a wall!");
                    }
                    break;
                default:
                    System.out.println("Invalid command for Player 2!");
            }
        }

        scanner.close();
        System.out.println("Game Over.");
    }
}
