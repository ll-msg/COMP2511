package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Hydra;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Direction;

public class HydraTest {
    @Test
    public void basicMovementTest() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // use random seed for geanerating number [0, 100)
        Hydra hydra = new Hydra(initialX, initialY, d, 0);
        hydra.add();
        // check the health and the attack damage of hydra as it should remain the same
        // as the assumption
        assertEquals(60, hydra.getHealth());
        assertEquals(1, hydra.getAttackDamage());
        // 60 48 29 47 15 53 91
        hydra.move();
        int hydraX = hydra.getX();
        int hydraY = hydra.getY();
        // 60 -> hydra move left
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY, hydraY);
        // 48 -> hydra should move up
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY - 1, hydraY);
        // 29 -> hydra should move up
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY - 2, hydraY);
        // 47 -> hydra should move up
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY - 3, hydraY);
        // 15 -> hydra should move down
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY - 2, hydraY);
        // 53 -> hydra should move left
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 2, hydraX);
        assertEquals(initialY - 2, hydraY);
        // 91 -> hydra should move right
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY - 2, hydraY);
    }

    @Test
    public void movementBlockedByWallsTest() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // seed 0 : 60 48 29 47 15 53 91
        Hydra hydra = new Hydra(initialX, initialY, d, 0);
        hydra.add();
        // block the hydra by wall
        Wall wall = new Wall(1, 2, d);
        wall.add();
        // 60 -> hydra move left
        hydra.move();
        int hydraX = hydra.getX();
        int hydraY = hydra.getY();
        // hydra should not move
        assertEquals(initialX, hydraX);
        assertEquals(initialY, hydraY);
    }

    @Test
    public void movementBlockedByBoulderTest() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // seed 0 : 60 48 29 47 15 53 91
        Hydra hydra = new Hydra(initialX, initialY, d, 0);
        hydra.add();
        // block the hydra by wall
        Boulder boulder = new Boulder(1, 2, d);
        boulder.add();
        // 60 -> hydra move left
        hydra.move();
        int hydraX = hydra.getX();
        int hydraY = hydra.getY();
        // hydra should not move
        assertEquals(initialX, hydraX);
        assertEquals(initialY, hydraY);
    }

    @Test
    public void moveThroughLockedDoorTest() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // seed 0 : 60 48 29 47 15 53 91
        Hydra hydra = new Hydra(initialX, initialY, d, 0);
        hydra.add();
        // door is to the left of the hydra
        Door lockedDoor = new Door(1, 2, d, 1);
        lockedDoor.add();
        // 60 -> hydra move left
        hydra.move();
        int hydraX = hydra.getX();
        int hydraY = hydra.getY();
        // hydra should not move
        assertEquals(initialX, hydraX);
        assertEquals(initialY, hydraY);
    }

    @Test
    public void MovementWhenPlayerInvisibleTest() {
        // test movement when player is invincible
        int hX = 1;
        int hY = 3;
        int pX = 3;
        int pY = 3;
        Dungeon d = new Dungeon("1", "InvincibleMovement");
        Player player = new Player(pX, pY, d);
        player.add();
        d.setPlayer(player);
        // seed 0 : 60 48 29 47 15 53 91
        Hydra hydra = new Hydra(hX, hY, d, 0);
        hydra.add();

        // create an invicinble potion
        ItemUsedStrategy potion = new InvisibilityPotion(4, 4, d);
        player.applyItem(potion);
        player.playerMove(Direction.NONE);
        hydra.move();
        // hydra movement should not be affected
        // 60 -> hydra move left
        assertEquals(hX - 1, hydra.getX());
        assertEquals(hY, hydra.getY());

    }

    @Test
    public void moveThroughOpenDoorTest() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a key in the position of player
        Key key = new Key(1, 1, dungeon, 1);
        key.add();
        // create a lock door next to player
        Door door = new Door(2, 1, dungeon, 1);
        door.add();
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getInventory().getKeyNum());
        // move to the door
        player.playerMove(Direction.RIGHT);
        // let player open the door
        dungeon.tickPlayerAfterMoving();
        assertTrue(door.isOpen());
        // move player to position (2 , 2)
        player.playerMove(Direction.DOWN);
        int initialX = 3;
        int initialY = 1;
        // seed 0 : 60 48 29 47 15 53 91
        Hydra hydra = new Hydra(initialX, initialY, dungeon, 0);
        hydra.add();
        // 60 -> hydra move left
        hydra.move();
        int hydraX = hydra.getX();
        int hydraY = hydra.getY();
        // hydra should move thourgh the open door
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY, hydraY);
    }

    @Test
    public void trappedMovementTest() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // seed 0 : 60 48 29 47 15 53 91
        Hydra hydra = new Hydra(initialX, initialY, d, 0);
        hydra.add();
        // set the movement factor to 2
        SwampTile swampTile = new SwampTile(1, 2, d, 2);
        swampTile.add();
        // 60 -> hydra move left and trapped
        hydra.move();
        int hydraX = hydra.getX();
        int hydraY = hydra.getY();
        // hydra should move onto the swamp tile
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY, hydraY);
        // should not move up
        hydra.move();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY, hydraY);
        // should not move up
        hydra.move();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY, hydraY);
        // 47 -> hydra should move up
        hydra.move();
        hydraX = hydra.getX();
        hydraY = hydra.getY();
        assertEquals(initialX - 1, hydraX);
        assertEquals(initialY - 1, hydraY);
    }
}