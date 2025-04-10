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

        if (row > 0 && !grid[row - 1][col].visited) neighbors.add(grid[row - 1][col]);
        if (row < height - 1 && !grid[row + 1][col].visited) neighbors.add(grid[row + 1][col]);
        if (col > 0 && !grid[row][col - 1].visited) neighbors.add(grid[row][col - 1]);
        if (col < width - 1 && !grid[row][col + 1].visited) neighbors.add(grid[row][col + 1]);

        if (neighbors.isEmpty()) return null;
        return neighbors.get(rand.nextInt(neighbors.size()));
    }

    private void removeWalls(Cell a, Cell b) {
        int dRow = a.row - b.row;
        int dCol = a.col - b.col;

        if (dRow == 1) { a.north = false; b.south = false; }
        if (dRow == -1) { a.south = false; b.north = false; }
        if (dCol == 1) { a.west = false; b.east = false; }
        if (dCol == -1) { a.east = false; b.west = false; }
    }

    public void printMazeWithTwoPlayers(Player p1, Player p2, List<Sprite> spirits) {
        System.out.println("+" + "---+".repeat(width));

        for (int row = 0; row < height; row++) {
            StringBuilder top = new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");

            for (int col = 0; col < width; col++) {
                Cell cell = grid[row][col];

                boolean isPlayer1 = (p1.getRow() == row && p1.getCol() == col);
                boolean isPlayer2 = (p2.getRow() == row && p2.getCol() == col);
                boolean isExit = (row == exitRow && col == exitCol);
                boolean isSpirit = false;

                for (Sprite spirit : spirits) {
                    if (spirit.getRow() == row && spirit.getCol() == col) {
                        isSpirit = true;
                        break;
                    }
                }

                String body;
                if (isPlayer1) {
                    body = "1";
                } else if (isPlayer2) {
                    body = "2";
                } else if (isSpirit) {
                    body = "X";
                } else if (isExit) {
                    body = "E";
                } else {
                    body = " ";
                }

                top.append(" ").append(body).append(" ");
                top.append(cell.east ? "|" : " ");

                bottom.append(cell.south ? "---" : "   ");
                bottom.append("+");
            }

            System.out.println(top);
            System.out.println(bottom);
        }
    }

    public boolean canMove(Player player, String direction) {
        int row = player.getRow();
        int col = player.getCol();
        Cell cell = grid[row][col];

        return switch (direction.toUpperCase()) {
            case "W" -> !cell.north;
            case "S" -> !cell.south;
            case "A" -> !cell.west;
            case "D" -> !cell.east;
            default -> false;
        };
    }

    // ✅ NEW METHOD: for Sprite movement
    public boolean canMoveSprite(Sprite sprite, String direction) {
        int row = sprite.getRow();
        int col = sprite.getCol();
        Cell cell = grid[row][col];

        return switch (direction.toUpperCase()) {
            case "W" -> row > 0 && !cell.north;
            case "S" -> row < height - 1 && !cell.south;
            case "A" -> col > 0 && !cell.west;
            case "D" -> col < width - 1 && !cell.east;
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

    private static class Cell {
        int row, col;
        boolean north = true, south = true, east = true, west = true;
        boolean visited = false;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
