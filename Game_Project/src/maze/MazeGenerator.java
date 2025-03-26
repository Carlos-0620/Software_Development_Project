package maze;
import java.util.*;

public class MazeGenerator {
    private final int width;
    private final int height;
    private final Cell[][] grid;
    private final Random rand = new Random();

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];

        // Initialize cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col);
            }
        }
    }

    public void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell start = grid[0][0];
        start.visited = true;
        stack.push(start);

        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            Cell next = getRandomUnvisitedNeighbor(current);

            if (next != null) {
                removeWalls(current, next);
                next.visited = true;
                stack.push(next);
            } else {
                stack.pop();
            }
        }
    }

    private Cell getRandomUnvisitedNeighbor(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();

        int row = cell.row;
        int col = cell.col;

        if (row > 0 && !grid[row - 1][col].visited) neighbors.add(grid[row - 1][col]); // North
        if (row < height - 1 && !grid[row + 1][col].visited) neighbors.add(grid[row + 1][col]); // South
        if (col > 0 && !grid[row][col - 1].visited) neighbors.add(grid[row][col - 1]); // West
        if (col < width - 1 && !grid[row][col + 1].visited) neighbors.add(grid[row][col + 1]); // East

        if (neighbors.isEmpty()) return null;
        return neighbors.get(rand.nextInt(neighbors.size()));
    }

    private void removeWalls(Cell a, Cell b) {
        int dRow = a.row - b.row;
        int dCol = a.col - b.col;

        if (dRow == 1) { a.north = false; b.south = false; } // b is north of a
        if (dRow == -1) { a.south = false; b.north = false; } // b is south of a
        if (dCol == 1) { a.west = false; b.east = false; }   // b is west of a
        if (dCol == -1) { a.east = false; b.west = false; }  // b is east of a
    }

    public void printMaze() {
        System.out.println("+" + "---+".repeat(width));

        for (int row = 0; row < height; row++) {
            StringBuilder top = new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");

            for (int col = 0; col < width; col++) {
                Cell cell = grid[row][col];
                top.append("   ");
                top.append(cell.east ? "|" : " ");

                bottom.append(cell.south ? "---" : "   ");
                bottom.append("+");
            }

            System.out.println(top);
            System.out.println(bottom);
        }
    }

    static class Cell {
        int row, col;
        boolean visited = false;
        boolean north = true, south = true, east = true, west = true;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
