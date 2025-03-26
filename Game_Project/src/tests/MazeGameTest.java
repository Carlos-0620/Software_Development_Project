package tests;

import maze.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprites.Player;

import static org.junit.jupiter.api.Assertions.*;

public class MazeGameTest {

    private Maze maze;
    private Player player;

    @BeforeEach
    public void setUp() {
        maze = new Maze(5, 5);
        maze.generateMaze();
        player = new Player(0, 0);
    }

    @Test
    public void testInitialPlayerPosition() {
        assertEquals(0, player.getRow());
        assertEquals(0, player.getCol());
    }

    @Test
    public void testPlayerValidMove() {
        // Try moving right if no wall
        if (maze.canMove(player, "D")) {
            player.move(0, 1);
            assertEquals(0, player.getRow());
            assertEquals(1, player.getCol());
        }
    }

    @Test
    public void testPlayerBlockedByWall() {
        // Save position
        int startRow = player.getRow();
        int startCol = player.getCol();

        // Try to move left where there's always a wall (outside bounds)
        if (!maze.canMove(player, "A")) {
            player.move(0, -1); // Should not move
            assertEquals(startRow, player.getRow());
            assertEquals(startCol, player.getCol());
        }
    }
}
