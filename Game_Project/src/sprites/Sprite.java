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

    public void moveRandomly(Maze maze) {
        String[] directions = {"W", "S", "A", "D"};
        for (int i = 0; i < 4; i++) {
            String direction = directions[random.nextInt(directions.length)];
            if (maze.canMoveSprite(this, direction)) {
                switch (direction) {
                    case "W" -> row--;
                    case "S" -> row++;
                    case "A" -> col--;
                    case "D" -> col++;
                }
                break;
            }
        }
    }
}