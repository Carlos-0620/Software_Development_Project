package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import maze.Maze;
import sprites.Player;
import sprites.Sprite;

public class MazeGame {

    public static void main(String[] args) {
        Maze maze = new Maze(20, 7);
        maze.generateMaze();

        Player player1 = new Player(0, 0);  // Player 1 starts top-left
        Player player2 = new Player(maze.getHeight() - 1, maze.getWidth() - 1);  // Player 2 starts bottom-right

        maze.ensureOpeningsForStartPositions(player1, player2);

        List<Sprite> sprites = new ArrayList<>();
        Random random = new Random();
        while (sprites.size() < 3) {
            int r = random.nextInt(maze.getHeight());
            int c = random.nextInt(maze.getWidth());
            if ((r != player1.getRow() || c != player1.getCol()) &&
                (r != player2.getRow() || c != player2.getCol())) {
                sprites.add(new Sprite(r, c));
            }
        }

        Scanner scanner = new Scanner(System.in);
        String input;

        maze.printMazeWithTwoPlayers(player1, player2, sprites);

        while (true) {
            // --- Player 1 turn ---
            System.out.print("Player 1 (WASD, Q to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) break;

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

            for (Sprite sprite : sprites) {
                if (sprite.getRow() == player1.getRow() && sprite.getCol() == player1.getCol()) {
                    System.out.println("💀 A spirit caught Player 1! Game Over! 💀");
                    return;
                }
            }

            if (playerAtGoal(player1, maze.getHeight() - 1, maze.getWidth() - 1)) {
                System.out.println("🎉 Player 1 reached the exit! 🎉");
                break;
            }

            // --- Player 2 turn ---
            System.out.print("Player 2 (IJKL, T to quit): ");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("T")) break;

            String translatedDir = switch (input) {
                case "I" -> "W";
                case "K" -> "S";
                case "J" -> "A";
                case "L" -> "D";
                default -> "";
            };

            if (!translatedDir.isEmpty() && maze.canMove(player2, translatedDir)) {
                switch (translatedDir) {
                    case "W" -> player2.move(-1, 0);
                    case "S" -> player2.move(1, 0);
                    case "A" -> player2.move(0, -1);
                    case "D" -> player2.move(0, 1);
                }
            } else {
                System.out.println("You hit a wall!");
            }

            for (Sprite sprite : sprites) {
                if (sprite.getRow() == player2.getRow() && sprite.getCol() == player2.getCol()) {
                    System.out.println("💀 A spirit caught Player 2! Game Over! 💀");
                    return;
                }
            }

            if (playerAtGoal(player2, 0, 0)) {
                System.out.println("🎉 Player 2 reached the exit! 🎉");
                break;
            }

            for (Sprite sprite : sprites) {
                sprite.moveRandomly(maze);
            }

            maze.printMazeWithTwoPlayers(player1, player2, sprites);
        }

        scanner.close();
        System.out.println("Game Over.");
    }

    // Helper method for goal checking
    private static boolean playerAtGoal(Player player, int goalRow, int goalCol) {
        return player.getRow() == goalRow && player.getCol() == goalCol;
    }
}
