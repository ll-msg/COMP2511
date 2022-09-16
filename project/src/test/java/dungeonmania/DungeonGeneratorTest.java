package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;

public class DungeonGeneratorTest {
    @Test
    public void testGenerateNoError() {
        Boolean[][] randomGenerator = RandomDungeonGenerator.dungeonGenerator(50, 50, 3, 3, 10, 10);
        assertEquals(true, randomGenerator[3][3]);
        assertEquals(true, randomGenerator[10][10]);
    }
    @Test
    public void TestHasAPathUsingBFS() {
        //prove there is a path between player and exit using bfs
        Boolean[][] randomGenerator = RandomDungeonGenerator.dungeonGenerator(50, 50, 3, 3, 10, 10);
        Boolean foundExit = false;
        Queue<Position> queue = new ArrayDeque<>();
        queue.add(new Position(3, 3));
    
        Position currentPosition;
        List<Position> visited = new ArrayList<>();
        while (!queue.isEmpty()) {
            currentPosition = queue.remove();
            visited.add(currentPosition);
            if (currentPosition.getX()== 10 && currentPosition.getY() == 10) {
                foundExit = true;
            } else {
                if (randomGenerator[currentPosition.getX() - 1][currentPosition.getY()] != false ) {
                    queue.add(new Position(currentPosition.getX() - 1, currentPosition.getY()));
                }
                if (randomGenerator[currentPosition.getX()][currentPosition.getY()-1] != false) {
                    queue.add(new Position(currentPosition.getX(), currentPosition.getY()-1));
                }
                if (randomGenerator[currentPosition.getX()+ 1][currentPosition.getY()] != false) {
                    queue.add(new Position(currentPosition.getX()+ 1, currentPosition.getY()));
                }
                if (randomGenerator[currentPosition.getX() ][currentPosition.getY() + 1] != false) {
                    queue.add(new Position(currentPosition.getX(), currentPosition.getY() + 1));
                } 
                queue.removeAll(visited);
            }
        }
        assertTrue(foundExit);
    }
}
