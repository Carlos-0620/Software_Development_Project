package tests;

import maze.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprites.Player;

import static org.junit.jupiter.api.Assertions.*;

public class MazeGameTest {

    private Maze maze;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        maze = new Maze(10, 6);  // Example size, even width for symmetry
        maze.generateMaze();
        player1 = new Player(0, 0);                       // Top-left
        player2 = new Player(0, maze.getWidth() - 1);     // Top-right
        maze.ensureOpeningsForStartPositions(player1, player2);
    }

    @Test
    public void testInitialPlayerPositions() {
        assertEquals(0, player1.getRow());
        assertEquals(0, player1.getCol());

        assertEquals(0, player2.getRow());
        assertEquals(maze.getWidth() - 1, player2.getCol());
    }

    @Test
    public void testExitPositionIsCorrect() {
        Player playerAtExit = new Player(maze.getHeight() - 1, maze.getWidth() - 1);
        assertTrue(maze.isAtExit(playerAtExit), "Player at bottom-right should be at the exit");
    }

    @Test
    public void testPlayer1CanMoveEastIfPathIsOpen() {
        if (maze.canMove(player1, "D")) {
            player1.move(0, 1);
            assertEquals(0, player1.getRow());
            assertEquals(1, player1.getCol());
        } else {
            System.out.println("Wall blocks right move — test skipped");
        }
    }
}
