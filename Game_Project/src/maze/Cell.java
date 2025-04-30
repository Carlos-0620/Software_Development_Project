package maze;

public class Cell {
    private final int row;
    private final int col;
    private boolean northWall = true;
    private boolean southWall = true;
    private boolean eastWall = true;
    private boolean westWall = true;
    private boolean visited = false;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasNorthWall() {
        return northWall;
    }

    public void setNorth(boolean northWall) {
        this.northWall = northWall;
    }

    public boolean hasSouthWall() {
        return southWall;
    }

    public void setSouth(boolean southWall) {
        this.southWall = southWall;
    }

    public boolean hasEastWall() {
        return eastWall;
    }

    public void setEast(boolean eastWall) {
        this.eastWall = eastWall;
    }

    public boolean hasWestWall() {
        return westWall;
    }

    public void setWest(boolean westWall) {
        this.westWall = westWall;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Add the isWall method
    public boolean isWall() {
        return northWall && southWall && eastWall && westWall;
    }
}
