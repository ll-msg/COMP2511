package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dungeonmania.util.Position;

public class RandomDungeonGenerator {
    /**
     * check if a position is in the dungeon generated
     * @param position the position needed to be checked
     * @param maze the 2d boolean list representing the dungeon
     * @return a result of if the position is a valid position
     */
    public static boolean isValidPosition(Position position, Boolean[][] maze) {
        int x = position.getX();
        int y = position.getY();

        if (x >= 49 || x <= 0) {
            return false;
        }

        if (y >= 49 || y <= 0) {
            return false;
        }

        return true;
    }

    /**
     * add an option into options
     * @param options the options list for generating random dungeon
     * @param position the position of target position
     * @param maze the 2D list representing a maze
     */
    public static void addOptions(List<Position> options, Position position, Boolean[][] maze) {
        int x = position.getX();
        int y = position.getY();
        Position up = new Position(x, y + 2);
        Position down = new Position(x, y - 2);
        Position right = new Position(x + 2, y);
        Position left = new Position(x - 2, y);
        if (isValidPosition(up, maze) && maze[up.getX()][up.getY()] == false) {
            options.add(up);
        }
        if (isValidPosition(down, maze) && maze[down.getX()][down.getY()] == false) {
            options.add(down);
        }
        if (isValidPosition(right, maze) && maze[right.getX()][right.getY()] == false) {
            options.add(right);
        }
        if (isValidPosition(left, maze) && maze[left.getX()][left.getY()] == false) {
            options.add(left);
        }
    }

    /**
     * add an neighbour into neighbours
     * @param neighbours the neighbours list for generating random dungeon
     * @param position the position of target position
     * @param maze the 2D list representing a maze
     */
    public static void addNeighbours(List<Position> neighbours, Position position, Boolean[][] maze) {
        int x = position.getX();
        int y = position.getY();
        Position up = new Position(x, y + 2);
        Position down = new Position(x, y - 2);
        Position right = new Position(x + 2, y);
        Position left = new Position(x - 2, y);
        if (isValidPosition(up, maze) && maze[up.getX()][up.getY()] == true) {
            neighbours.add(up);
        }
        if (isValidPosition(down, maze) && maze[down.getX()][down.getY()] == true) {
            neighbours.add(down);
        }
        if (isValidPosition(right, maze) && maze[right.getX()][right.getY()] == true) {
            neighbours.add(right);
        }
        if (isValidPosition(left, maze) && maze[left.getX()][left.getY()] == true) {
            neighbours.add(left);
        }
    }

    /**
     * Randomly generate a 2d list representing a dungeon 
     * @param width the width of the dungeon
     * @param height the height of the dungeon
     * @param xStart the x of the player's position
     * @param yStart the y of the player's position
     * @param xEnd the x of the exit's position
     * @param yEnd the y of the exit's position
     * @return a 2d list representing a dungeon 
     */
    public static Boolean[][] dungeonGenerator(int width, int height, int xStart, int yStart, int xEnd, int yEnd) {
        Random random = new Random();
        Boolean[][] maze = new Boolean[50][50];
        for (Boolean[] arr1 : maze){
            Arrays.fill(arr1, false);
        }
        List<Position> options = new ArrayList<>();
        Position start = new Position(xStart, yStart);
        addOptions(options, start, maze);
        maze[xStart][yStart] = true;
        while (!options.isEmpty()) {
            Position next = options.remove(random.nextInt(options.size()));
            maze[next.getX()][next.getY()] = true;
            List<Position> neighbours = new ArrayList<>();
            addNeighbours(neighbours, next, maze);
            if (!neighbours.isEmpty()) {
                Position neighbour = neighbours.get(random.nextInt(neighbours.size()));
                maze[Math.abs(neighbour.getX()+next.getX())/2][Math.abs(neighbour.getY()+next.getY())/2] = true;
            }
            addOptions(options, next, maze);
        }
        maze[xEnd][yEnd] = true;
        return maze;
    }
}
