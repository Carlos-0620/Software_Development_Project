package tests;

import maze.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprites.Player;

import static org.junit.jupiter.api.Assertions.*;

public class MazeGameTest {

    private Maze maze;

    @BeforeEach
    public void setUp() {
        maze = new Maze(10, 6);  // Example size, adjust as needed
        maze.generateMaze();
    }

    @Test
    public void testInitialPlayerPositions() {
        Player player1 = new Player(0, 0);                        // top-left
        Player player2 = new Player(0, maze.getWidth() - 1);      // top-right

        assertEquals(0, player1.getRow());
        assertEquals(0, player1.getCol());

        assertEquals(0, player2.getRow());
        assertEquals(maze.getWidth() - 1, player2.getCol());
    }
}
