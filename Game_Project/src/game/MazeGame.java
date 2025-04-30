package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import maze.Maze;
import sprites.Player;
import sprites.Sprite;

public class MazeGame {

	private final Maze maze;
	private final Player player1;
	private final Player player2;
	private final List<Sprite> sprites;

	public MazeGame() {
		maze = new Maze(20, 7);
		maze.generateMaze();

		player1 = new Player(0, 0);  // Player 1 starts top-left
		player2 = new Player(maze.getHeight() - 1, maze.getWidth() - 1);  // Player 2 starts bottom-right

		maze.ensureOpeningsForStartPositions(player1, player2);

		sprites = new ArrayList<>();
		Random random = new Random();
		while (sprites.size() < 3) {
			int r = random.nextInt(maze.getHeight());
			int c = random.nextInt(maze.getWidth());
			if ((r != player1.getRow() || c != player1.getCol()) &&
					(r != player2.getRow() || c != player2.getCol())) {
				sprites.add(new Sprite(r, c));
			}
		}
	}

	public static void main(String[] args) {
		MazeGame game = new MazeGame();
		Scanner scanner = new Scanner(System.in);
		String input;

		game.maze.printMazeWithTwoPlayers(game.player1, game.player2, game.sprites);

		while (true) {
			System.out.print("Player 1 (WASD, Q to quit): ");
			input = scanner.nextLine().toUpperCase();
			if (input.equals("Q")) break;

			if (game.maze.canMove(game.player1, input)) {
				switch (input) {
				case "W" -> game.player1.move(-1, 0);
				case "S" -> game.player1.move(1, 0);
				case "A" -> game.player1.move(0, -1);
				case "D" -> game.player1.move(0, 1);
				}
			} else {
				System.out.println("You hit a wall!");
			}

			for (Sprite sprite : game.sprites) {
				if (sprite.getRow() == game.player1.getRow() && sprite.getCol() == game.player1.getCol()) {
					System.out.println("💀 A spirit caught Player 1! Game Over! 💀");
					return;
				}
			}

			if (playerAtGoal(game.player1, game.maze.getHeight() - 1, game.maze.getWidth() - 1)) {
				System.out.println("🎉 Player 1 reached the exit! 🎉");
				break;
			}

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

			if (!translatedDir.isEmpty() && game.maze.canMove(game.player2, translatedDir)) {
				switch (translatedDir) {
				case "W" -> game.player2.move(-1, 0);
				case "S" -> game.player2.move(1, 0);
				case "A" -> game.player2.move(0, -1);
				case "D" -> game.player2.move(0, 1);
				}
			} else {
				System.out.println("You hit a wall!");
			}

			for (Sprite sprite : game.sprites) {
				if (sprite.getRow() == game.player2.getRow() && sprite.getCol() == game.player2.getCol()) {
					System.out.println("💀 A spirit caught Player 2! Game Over! 💀");
					return;
				}
			}

			if (playerAtGoal(game.player2, 0, 0)) {
				System.out.println("🎉 Player 2 reached the exit! 🎉");
				break;
			}

			for (Sprite sprite : game.sprites) {
				sprite.moveRandomly(game.maze);
			}

			game.maze.printMazeWithTwoPlayers(game.player1, game.player2, game.sprites);
		}

		scanner.close();
		System.out.println("Game Over.");
	}

	private static boolean playerAtGoal(Player player, int goalRow, int goalCol) {
		return player.getRow() == goalRow && player.getCol() == goalCol;
	}

	// Added getters
	public Maze getMaze() {
		return maze;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public List<Sprite> getSprites() {
		return sprites;
	}
}
