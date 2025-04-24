package maze;

public class Cell {
    private final int row;
    private final int col;
    private boolean visited = false;
    private boolean north = true, south = true, east = true, west = true;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean hasNorthWall() {
        return north;
    }

    public boolean hasSouthWall() {
        return south;
    }

    public boolean hasEastWall() {
        return east;
    }

    public boolean hasWestWall() {
        return west;
    }

    // Setters
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public void setWest(boolean west) {
        this.west = west;
    }
}


