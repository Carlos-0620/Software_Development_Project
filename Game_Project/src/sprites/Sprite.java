package sprites;

public class Sprite {
    private int row;
    private int col;

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
}

