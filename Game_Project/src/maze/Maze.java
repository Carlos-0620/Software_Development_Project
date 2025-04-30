package maze;

import java.util.*;
import sprites.Player;
import sprites.Sprite;

public class Maze {
    private final Cell[][] grid;
    private final int width;
    private final int height;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];

        // Initialize cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col);
            }
        }

        // Generate the maze using Depth-First Search
        generateMaze(0, 0);
    }

    private void generateMaze(int startRow, int startCol) {
        Stack<Cell> stack = new Stack<>();
        Cell current = grid[startRow][startCol];
        current.setVisited(true);
        stack.push(current);

        while (!stack.isEmpty()) {
            current = stack.peek();
            List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(current);

            if (unvisitedNeighbors.isEmpty()) {
                stack.pop();
            } else {
                Cell next = unvisitedNeighbors.get(new Random().nextInt(unvisitedNeighbors.size()));
                removeWallsBetween(current, next);
                next.setVisited(true);
                stack.push(next);
            }
        }

        // Reset visited flags
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col].setVisited(false);
            }
        }
    }

    private List<Cell> getUnvisitedNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();

        // Check north
        if (row > 0 && !grid[row - 1][col].isVisited()) {
            neighbors.add(grid[row - 1][col]);
        }
        // Check south
        if (row < height - 1 && !grid[row + 1][col].isVisited()) {
            neighbors.add(grid[row + 1][col]);
        }
        // Check west
        if (col > 0 && !grid[row][col - 1].isVisited()) {
            neighbors.add(grid[row][col - 1]);
        }
        // Check east
        if (col < width - 1 && !grid[row][col + 1].isVisited()) {
            neighbors.add(grid[row][col + 1]);
        }

        return neighbors;
    }

    private void removeWallsBetween(Cell current, Cell next) {
        int rowDiff = next.getRow() - current.getRow();
        int colDiff = next.getCol() - current.getCol();

        if (rowDiff == -1) { // Next is north
            current.setNorth(false);
            next.setSouth(false);
        } else if (rowDiff == 1) { // Next is south
            current.setSouth(false);
            next.setNorth(false);
        } else if (colDiff == -1) { // Next is west
            current.setWest(false);
            next.setEast(false);
        } else if (colDiff == 1) { // Next is east
            current.setEast(false);
            next.setWest(false);
        }
    }

    public void ensureOpeningsForStartPositions(Player p1, Player p2) {
        Cell start1 = grid[p1.getRow()][p1.getCol()];
        Cell start2 = grid[p2.getRow()][p2.getCol()];
        start1.setSouth(false);
        start1.setEast(false);
        start2.setSouth(false);
        start2.setWest(false);
    }

    public void printMazeWithTwoPlayers(Player p1, Player p2, List<Sprite> spirits) {
        System.out.println("+" + "---+".repeat(width));
        for (int row = 0; row < height; row++) {
            StringBuilder top = new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");
            for (int col = 0; col < width; col++) {
                final int currentRow = row;
                final int currentCol = col;
                Cell cell = grid[currentRow][currentCol];
                boolean isPlayer1 = (p1.getRow() == currentRow && p1.getCol() == currentCol);
                boolean isPlayer2 = (p2.getRow() == currentRow && p2.getCol() == currentCol);
                boolean isExit = (currentRow == height - 1 && currentCol == width - 1);
                boolean isSpirit = spirits.stream().anyMatch(s -> s.getRow() == currentRow && s.getCol() == currentCol);

                String body = isPlayer1 ? "1" : isPlayer2 ? "2" : isSpirit ? "X" : isExit ? "E" : " ";
                top.append(" ").append(body).append(" ").append(cell.hasEastWall() ? "|" : " ");
                bottom.append(cell.hasSouthWall() ? "---" : "   ").append("+");
            }
            System.out.println(top);
            System.out.println(bottom);
        }
    }

    public boolean canMove(Player player, String direction) {
        int currentRow = player.getRow();
        int currentCol = player.getCol();
        Cell currentCell = grid[currentRow][currentCol];

        return switch (direction) {
            case "W" -> !currentCell.hasNorthWall() && currentRow > 0;
            case "S" -> !currentCell.hasSouthWall() && currentRow < grid.length - 1;
            case "A" -> !currentCell.hasWestWall() && currentCol > 0;
            case "D" -> !currentCell.hasEastWall() && currentCol < grid[0].length - 1;
            default -> false;
        };
    }

    public boolean canMoveSprite(Sprite sprite, String direction) {
        Cell cell = grid[sprite.getRow()][sprite.getCol()];
        return switch (direction.toUpperCase()) {
            case "W" -> sprite.getRow() > 0 && !cell.hasNorthWall();
            case "S" -> sprite.getRow() < height - 1 && !cell.hasSouthWall();
            case "A" -> sprite.getCol() > 0 && !cell.hasWestWall();
            case "D" -> sprite.getCol() < width - 1 && !cell.hasEastWall();
            default -> false;
        };
    }

    public boolean isAtExit(Player player) {
        // Exit is at the bottom-right corner
        return player.getRow() == height - 1 && player.getCol() == width - 1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasNorthWall(int row, int col) {
        return grid[row][col].hasNorthWall();
    }

    public boolean hasSouthWall(int row, int col) {
        return grid[row][col].hasSouthWall();
    }

    public boolean hasEastWall(int row, int col) {
        return grid[row][col].hasEastWall();
    }

    public boolean hasWestWall(int row, int col) {
        return grid[row][col].hasWestWall();
    }
}
