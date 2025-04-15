package sprites;

public class Player {
    private int row;
    private int col;

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void move(int dRow, int dCol) {
        this.row += dRow;
        this.col += dCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
