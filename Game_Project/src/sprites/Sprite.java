package sprites;

import java.util.Random;
import maze.Maze;

public class Sprite {
    private int row;
    private int col;
    private final Random random = new Random();

    public Sprite(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void move(int dRow, int dCol) {
        this.row += dRow;
        this.col += dCol;
    }

    /**
     * Move the sprite randomly in one of the four directions if possible.
     * @param maze The maze object to check if movement is valid.
     */
    public void moveRandomly(Maze maze) {
        String[] directions = {"W", "A", "S", "D"};
        int randomIndex = random.nextInt(directions.length);
        String direction = directions[randomIndex];

        if (maze.canMoveSprite(this, direction)) {
            switch (direction) {
                case "W" -> move(-1, 0);
                case "S" -> move(1, 0);
                case "A" -> move(0, -1);
                case "D" -> move(0, 1);
            }
        }
    }
}
