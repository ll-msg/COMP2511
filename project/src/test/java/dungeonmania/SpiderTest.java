package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.NonPlayer;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.Spider;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * This test file mainly tests the movement of spider
 * 
 * @author Yanran Wang
 */

public class SpiderTest {
    @Test
    public void basicMoveTest() {
        // test basic movement
        // without any obstacle
        Dungeon dungeon = new Dungeon("1", "BasicMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        spider.move();
        // spider moves directly one cell up
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        // and start circling around spawn point
        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy, spider.getY());
    }

    @Test
    public void originMoveTest() {
        // test basic movement when the spider is the origin spider
        // without any obstacle
        Dungeon dungeon = new Dungeon("1", "BasicMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, true);
        spider.add();

        int Sx = 5;
        int Sy = 6;

        spider.move();
        // spider moves directly one cell up
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        // and start circling around spawn point
        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy, spider.getY());
    }

    @Test
    public void invisibleMoveTest() {
        // test movement when player is invisible
        // still normal movement
        Dungeon dungeon = new Dungeon("1", "InvisibleMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        // assert spider health
        assertEquals(20, spider.getHealth());
        // assert attack damage
        assertEquals(1, spider.getAttackDamage());
        spider.add();

        // create an invisible potion
        ItemUsedStrategy potion = new InvisibilityPotion(15, 26, dungeon);
        player.applyItem(potion);

        int Sx = spider.getX();
        int Sy = spider.getY();

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy - 1, spider.getY());
    }

    @Test
    public void invincibleMoveTest() {
        // test movement when player is invincible
        // can break the movement loop
        Dungeon dungeon = new Dungeon("1", "InvincibleMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(25, 58, dungeon, true);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        // create an invincible potion
        ItemUsedStrategy potion = new InvincibilityPotion(15, 26, dungeon);
        player.applyItem(potion);
        spider.move();

        // the spider will break the loop
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy, spider.getY());

        player.playerMove(Direction.NONE);
        spider.move();

        assertEquals(Sx + 2, spider.getX());
        assertEquals(Sy, spider.getY());

        // after 10 moves
        // the invincibility potion effect will gone
        // the spider start a new loop at current position
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();

        Sx = spider.getX();
        Sy = spider.getY();

        player.playerMove(Direction.NONE);
        spider.move();
        player.playerMove(Direction.NONE);
        spider.move();

        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy - 1, spider.getY());
    }

    @Test
    public void wallBlockTest() {
        // test movement when spider reaches the wall
        // should not be influenced
        Dungeon dungeon = new Dungeon("1", "WallBlockMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        Wall wall = new Wall(Sx, Sy - 1, dungeon);
        wall.add();

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());
    }

    @Test
    public void doorBlockTest() {
        // test movement when spider reaches the door
        // should not be influenced
        Dungeon dungeon = new Dungeon("1", "DoorBlockMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        Door door = new Door(Sx, Sy - 1, dungeon, 0);
        door.add();

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());
    }

    @Test
    public void boulderBlockTest() {
        // test movement when spider blocked by the boulder
        // right after the spider spawn
        // reverse the direction
        Dungeon dungeon = new Dungeon("1", "BoulderBlockMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        // set the boulder one cell up of the spider
        Boulder boulder = new Boulder(Sx, Sy - 1, dungeon);
        boulder.add();

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy, spider.getY());

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy + 1, spider.getY());
    }

    @Test
    public void boulderBlockTest2() {
        // test movement when spider blocked by the boulder
        // on the way the spider move
        // reverse the direction
        Dungeon dungeon = new Dungeon("1", "BoulderBlockMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        // set the boulder one cell bottom left of the spider
        Boulder boulder = new Boulder(Sx - 1, Sy + 1, dungeon);
        boulder.add();

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy + 1, spider.getY());

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy + 1, spider.getY());

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy + 1, spider.getY());

        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy + 1, spider.getY());

    }

    @Test
    public void SpawnOnBoulderTest() {
        // test spider spawn on the boulder
        // stay in the same position until the boulder is pushed
        Dungeon dungeon = new Dungeon("1", "BoulderSpawnMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        NonPlayer spider = new Spider(5, 6, dungeon, false);
        spider.add();

        int Sx = spider.getX();
        int Sy = spider.getY();

        // set the boulder on the spawn point of the spider
        Boulder boulder = new Boulder(Sx, Sy, dungeon);
        boulder.add();

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy, spider.getY());

        spider.move();
        assertEquals(Sx, spider.getX());
        assertEquals(Sy, spider.getY());
    }

    @Test
    public void RandomSpawnTest() {
        // test spider spawn randomly
        Dungeon dungeon = new Dungeon("1", "BasicMovement");
        Player player = new Player(15, 26, dungeon);
        dungeon.setPlayer(player);
        long seed = 3;
        NonPlayer spider = new Spider(5, 6, dungeon, false, seed);
        spider.add();

        int Sx = 17;
        int Sy = 28;

        spider.move();
        // spider moves directly one cell up
        assertEquals(Sx, spider.getX());
        assertEquals(Sy - 1, spider.getY());

        // and start circling around spawn point
        spider.move();
        assertEquals(Sx + 1, spider.getX());
        assertEquals(Sy - 1, spider.getY());

    }

    @Test
    public void portalTeleportSpiderTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        Portal Portal0 = new Portal(1, 1, dungeon, "Blue");
        Portal Portal1 = new Portal(10, 11, dungeon, "Blue");
        Spider spider = new Spider(1, 1, dungeon, true);
        Position position = new Position(10, 11, 4);
        Portal0.add();
        Portal1.add();
        spider.add();
        spider.teleport(Portal0);
        assertEquals(position, spider.getPosition());
    }
}
