package tests;

import game.MazeGame;
import maze.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprites.Player;
import sprites.Sprite;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MazeGameTest {
    private MazeGame game;
    private Maze maze;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        // Create full game instance to test integrated functionality
        game = new MazeGame();
        maze = game.getMaze();
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
    }

    @Test
    public void testInitialPlayerPositions() {
        // Check player 1 position (top-left)
        assertEquals(0, player1.getRow(), "Player 1 should start at row 0");
        assertEquals(0, player1.getCol(), "Player 1 should start at column 0");

        // Check player 2 position (top-right)
        assertEquals(0, player2.getRow(), "Player 2 should start at row 0");
        assertEquals(maze.getWidth() - 1, player2.getCol(), "Player 2 should start at last column");
    }

    @Test
    public void testExitPositionIsCorrect() {
        // Check if bottom-right is recognized as exit
        Player playerAtExit = new Player(maze.getHeight() - 1, maze.getWidth() - 1);
        assertTrue(maze.isAtExit(playerAtExit), "Player at bottom-right should be at the exit");

        // Check if other positions are not exits
        Player playerNotAtExit = new Player(0, 0);
        assertFalse(maze.isAtExit(playerNotAtExit), "Player at start should not be at exit");
    }

    @Test
    public void testPlayerMovement() {
        // Test movement in all directions
        int initialRow = player1.getRow();
        int initialCol = player1.getCol();

        // Try moving right if possible
        if (maze.canMove(player1, "D")) {
            player1.move(0, 1);
            assertEquals(initialCol + 1, player1.getCol(), "Player should move right");
            assertEquals(initialRow, player1.getRow(), "Row should not change");
        }

        // Reset position
        player1.setPosition(initialRow, initialCol);

        // Try moving down if possible
        if (maze.canMove(player1, "S")) {
            player1.move(1, 0);
            assertEquals(initialRow + 1, player1.getRow(), "Player should move down");
            assertEquals(initialCol, player1.getCol(), "Column should not change");
        }
    }

    @Test
    public void testSprites() {
        List<Sprite> sprites = game.getSprites();
        assertNotNull(sprites, "Sprite list should not be null");
        assertFalse(sprites.isEmpty(), "There should be sprites in the game");

        // Check sprite positions are valid
        for (Sprite sprite : sprites) {
            assertTrue(sprite.getRow() >= 0, "Sprite row should be positive");
            assertTrue(sprite.getCol() >= 0, "Sprite column should be positive");
            assertTrue(sprite.getRow() < maze.getHeight(), "Sprite should be within maze height");
            assertTrue(sprite.getCol() < maze.getWidth(), "Sprite should be within maze width");

            // Check sprites aren't at player starts or exit
            assertFalse((sprite.getRow() == 0 && sprite.getCol() == 0),
                    "Sprite should not be at player 1 start");
            assertFalse((sprite.getRow() == 0 && sprite.getCol() == maze.getWidth() - 1),
                    "Sprite should not be at player 2 start");
            assertFalse((sprite.getRow() == maze.getHeight() - 1 && sprite.getCol() == maze.getWidth() - 1),
                    "Sprite should not be at exit");
        }
    }

    @Test
    public void testMazeGeneration() {
        assertNotNull(maze, "Maze should not be null");
        assertEquals(7, maze.getWidth(), "Maze should have width 7");
        assertEquals(7, maze.getHeight(), "Maze should have height 7");

        // Test that the start positions are accessible
        assertTrue(maze.canMove(player1, "D") || maze.canMove(player1, "S"),
                "Player 1 should have at least one valid move from start");
        assertTrue(maze.canMove(player2, "A") || maze.canMove(player2, "S"),
                "Player 2 should have at least one valid move from start");
    }
}