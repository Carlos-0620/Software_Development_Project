package game;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import maze.Maze;
import sprites.Player;
import sprites.Sprite;

public class MazeGame {
    public static void main(String[] args) {
        Maze maze = new Maze(10, 7);
        maze.generateMaze();
        Player player = new Player(0, 0);
        Random random = new Random();

        Scanner scanner = new Scanner(System.in);
        String input;

        List<Sprite> spirits = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int randomRow = random.nextInt(maze.getHeight());
            int randomCol = random.nextInt(maze.getWidth());
            spirits.add(new Sprite(randomRow, randomCol));
        }
        while (true) {
            maze.printMaze(player,spirits);
            
            if (maze.isAtExit(player)) {
                System.out.println("🎉 Congratulations! You've reached the exit! 🎉");
                break;
            }
            for (Sprite spirit : spirits) {
                if (spirit.getRow() == player.getRow() && spirit.getCol() == player.getCol()) {
                    System.out.println("💀 A spirit caught you! Game Over! 💀");
                    return;
                }
            }

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
            for (Sprite spirit : spirits) {
                spirit.moveRandomly(maze);
            }
        }

        scanner.close();
        System.out.println("Game Over.");
        }
    }
    