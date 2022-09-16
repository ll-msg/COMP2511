package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.HealthPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Direction;

/**
 * This is a test file that mainly focuses on Player
 * 
 * @author Sikui Tang
 */
public class PlayerTest {
    @Test
    public void simpleMovementTest() {
        // primary movement test
        // set up
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        // move the player down by one cell
        player.playerMove(Direction.DOWN);
        assertEquals(player.getX(), initialX);
        assertEquals(player.getY(), initialY + 1);

        // move the player up by one cell
        player.playerMove(Direction.UP);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());

        // move the player one cell to the left
        player.playerMove(Direction.LEFT);
        assertEquals(initialX - 1, player.getX());
        assertEquals(initialY, player.getY());

        // move the player one cell to the right
        player.playerMove(Direction.RIGHT);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());

        // hold the player at the current position
        player.playerMove(Direction.NONE);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());

    }

    @Test
    public void movementBlockByWallTest() {
        // set up character and block entity
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        // generate a wall near the player
        Wall wall = new Wall(2, 1, d);
        wall.add();
        // move the player one cell to the right
        player.playerMove(Direction.RIGHT);
        // player should be blocked by the wall
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());
    }

    @Test
    public void pushBoulderTest() {
        // set up character and boulder entity
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        Boulder boulder = new Boulder(2, 1, d);
        boulder.add();
        // move the player one cell to the right
        player.playerMove(Direction.RIGHT);
        // check position of player
        assertEquals(initialX + 1, player.getX());
        assertEquals(initialY, player.getY());
        // check position of boulder
        // boulder should be pushed to the right of player
        assertEquals(3, boulder.getX());
        assertEquals(1, boulder.getY());
    }

    @Test
    public void pushBoulderButBlockedTest() {
        // set up
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        Boulder boulder = new Boulder(2, 1, d);
        boulder.add();
        // generate a wall to the right of the boulder
        Wall wall = new Wall(3, 1, d);
        wall.add();
        // move the player one cell to the right
        player.playerMove(Direction.RIGHT);
        // the position of the player should be unchanged since the boulder is blocked
        // by the wall
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());
        // check the position of the boulder, should be unchanged too
        assertEquals(2, boulder.getX());
        assertEquals(1, boulder.getY());

    }

    @Test
    public void drinkHealthPotionTest() {
        // Primary class method functional test
        // set up the player
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        // damage the player
        player.receiveDamage(50);
        // create a HealthPotion
        ItemUsedStrategy potion = new HealthPotion(2, 2, d);
        // health potion is used by player
        player.applyItem(potion);
        // check the health of player
        int fullHealth = 100;
        assertEquals(fullHealth, player.getHealth());

    }

    @Test
    public void moveThroughSwampTile() {
        // set up
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        player.add();
        d.setPlayer(player);
        // create a swampTile at 1, 2
        SwampTile swampTile = new SwampTile(1, 2, d, 1);
        swampTile.add();
        // move the player down by one cell
        player.playerMove(Direction.DOWN);
        assertEquals(player.getX(), initialX);
        assertEquals(player.getY(), initialY + 1);
        // player should not be affected by the swampTile
        // move the player down by one cell
        player.playerMove(Direction.DOWN);
        assertEquals(player.getX(), initialX);
        assertEquals(player.getY(), initialY + 2);

    }

}
