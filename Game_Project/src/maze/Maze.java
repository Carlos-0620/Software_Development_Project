package maze;

import java.util.*;
import sprites.Player;
import sprites.Sprite;

public class Maze {
    private final Random rand = new Random();
    private final int width;
    private final int height;
    private final Cell[][] grid;
    private final int exitRow;
    private final int exitCol;

    public Maze(int width, int height) {
        if (width % 2 != 0) width++; // Ensure even width for symmetry
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col);
            }
        }

        this.exitRow = height - 1;
        this.exitCol = width - 1;
    }

    public void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell start = grid[0][0];
        start.setVisited(true);
        stack.push(start);

        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            Cell next = getRandomUnvisitedNeighbor(current);

            if (next != null) {
                removeWalls(current, next);
                next.setVisited(true);
                stack.push(next);
            } else {
                stack.pop();
            }
        }
    }

    private Cell getRandomUnvisitedNeighbor(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();

        if (row > 0 && !grid[row - 1][col].isVisited()) neighbors.add(grid[row - 1][col]);
        if (row < height - 1 && !grid[row + 1][col].isVisited()) neighbors.add(grid[row + 1][col]);
        if (col > 0 && !grid[row][col - 1].isVisited()) neighbors.add(grid[row][col - 1]);
        if (col < width - 1 && !grid[row][col + 1].isVisited()) neighbors.add(grid[row][col + 1]);

        if (neighbors.isEmpty()) return null;
        return neighbors.get(rand.nextInt(neighbors.size()));
    }

    private void removeWalls(Cell a, Cell b) {
        int dRow = a.getRow() - b.getRow();
        int dCol = a.getCol() - b.getCol();

        if (dRow == 1) { a.setNorth(false); b.setSouth(false); }
        if (dRow == -1) { a.setSouth(false); b.setNorth(false); }
        if (dCol == 1) { a.setWest(false); b.setEast(false); }
        if (dCol == -1) { a.setEast(false); b.setWest(false); }
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
                boolean isExit = (currentRow == exitRow && currentCol == exitCol);
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
        Cell cell = grid[player.getRow()][player.getCol()];
        return switch (direction.toUpperCase()) {
            case "W" -> !cell.hasNorthWall();
            case "S" -> !cell.hasSouthWall();
            case "A" -> !cell.hasWestWall();
            case "D" -> !cell.hasEastWall();
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
        return player.getRow() == exitRow && player.getCol() == exitCol;
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
