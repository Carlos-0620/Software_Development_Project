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

            // Ensure players are not trapped at the start
            maze.ensureOpeningsForStartPositions(player1, player2);

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

                // Check for collision with any sprite
                for (Sprite spirit : sprites) {
                    if (spirit.getRow() == player1.getRow() && spirit.getCol() == player1.getCol()) {
                        System.out.println("💀 A spirit caught Player 1! Game Over! 💀");
                        return;
                    }
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

                // Translate IJKL to WASD directions
                String translatedDir = switch (input) {
                    case "I" -> "W";
                    case "K" -> "S";
                    case "J" -> "A";
                    case "L" -> "D";
                    default -> "";
                };

                // Check for collision before moving
                for (Sprite spirit : sprites) {
                    if (spirit.getRow() == player2.getRow() && spirit.getCol() == player2.getCol()) {
                        System.out.println("💀 A spirit caught Player 2! Game Over! 💀");
                        return;
                    }
                }

                if (!translatedDir.isEmpty() && maze.canMove(player2, translatedDir)) {
                    switch (translatedDir) {
                        case "W" -> player2.move(-1, 0);
                        case "S" -> player2.move(1, 0);
                        case "A" -> player2.move(0, -1);
                        case "D" -> player2.move(0, 1);
                    }
                } 
                if (player1.getRow() == player2.getRow() && player1.getCol() == player2.getCol()) {
                    System.out.println("Players collided! Game Over!");
                    return;
                }
                else {
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
                System.out.println("Remaining spirits: " + sprites.size());
                // Print maze after both players and sprites have moved
                maze.printMazeWithTwoPlayers(player1, player2, sprites);
            }

            scanner.close();
            System.out.println("Game Over.");
        }
    }
