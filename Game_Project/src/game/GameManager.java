package game;

import maze.MazeGenerator;

public class GameManager {

    public static void main(String[] args) {

     MazeGenerator maze = new MazeGenerator(20, 20);
        maze.generateMaze();
        maze.printMaze();
    }

}
