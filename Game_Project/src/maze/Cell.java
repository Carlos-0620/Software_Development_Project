package maze;

public class Cell {
    public int row, col;
    public boolean visited = false;
    public boolean north = true, south = true, east = true, west = true;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

