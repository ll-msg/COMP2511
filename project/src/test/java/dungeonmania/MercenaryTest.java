package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.movingentity.NonPlayer;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Mercenary;

/**
 * This test file mainly tests the movement of mercenary
 * 
 * @author Yanran Wang
 */

public class MercenaryTest {
    @Test
    public void basicMoveTest() {
        // test basic movement
        // without any obstacle
        int Mx = 1;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "BasicMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon);
        // assert mercenary health
        assertEquals(80, mercenary.getHealth());
        // assert damage
        assertEquals(1, mercenary.getAttackDamage());
        mercenary.add();

        mercenary.move();
        // mercenary moves towards to the player
        assertEquals(Mx + 1, mercenary.getX());
        assertEquals(My, mercenary.getY());
        
        mercenary.move();
        assertEquals(Mx + 2, mercenary.getX());
        assertEquals(My, mercenary.getY());

    }

    @Test
    public void invisibleMoveTest() {
        // test movement when player is invisible
        int Mx = 1;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "InvisibleMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon);
        mercenary.add();

        // create an invisible potion
        ItemUsedStrategy potion = new InvisibilityPotion(2, 2, dungeon);
        player.applyItem(potion);

        mercenary.move();
        // mercenary cannot find the player
        // stay in the same place
        assertEquals(Mx, mercenary.getX());
        assertEquals(My, mercenary.getY());
    }

    @Test
    public void invincibleMoveTest() {
        // test movement when player is invincible
        int Mx = 1;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "InvincibleMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon);
        mercenary.add();

        // create an invicinble potion
        ItemUsedStrategy potion = new InvincibilityPotion(2, 2, dungeon);
        player.applyItem(potion);

        mercenary.move();
        // mercenary run away from the player
        assertEquals(Mx, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());
    }

    @Test
    public void wallBlockTest() {
        // test movement when mercenary blocked by the wall
        // should change a new direction
        int Mx = 5;
        int My = 3;
        int Px = 2;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "WallBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon);
        mercenary.add();
        Wall wall = new Wall(3, 3, dungeon);
        wall.add();

        mercenary.move();
        assertEquals(Mx, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 1, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 2, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 3, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Px, mercenary.getX());
        assertEquals(Py, mercenary.getY());
    }

    @Test
    public void boulderBlockTest() {
        // test movement when mercenary blocked by the boulder
        // should change a new direction
        int Mx = 5;
        int My = 3;
        int Px = 2;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "BoulderBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon);
        mercenary.add();
        Boulder boulder = new Boulder(3, 3, dungeon);
        boulder.add();

        mercenary.move();
        assertEquals(Mx, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 1, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 2, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 3, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Px, mercenary.getX());
        assertEquals(Py, mercenary.getY());
    }

    @Test
    public void DoorBlockTest() {
        // test movement when mercenary blocked by the unopened door
        // should change a new direction
        int Mx = 5;
        int My = 3;
        int Px = 2;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "DoorBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon);
        mercenary.add();
        Door door = new Door(3, 3, dungeon, 0);
        door.add();

        mercenary.move();
        assertEquals(Mx, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 1, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 2, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Mx - 3, mercenary.getX());
        assertEquals(My - 1, mercenary.getY());

        mercenary.move();
        assertEquals(Px, mercenary.getX());
        assertEquals(Py, mercenary.getY());
    }

    @Test
    public void randomArmourTest() {
        // test mercenary has armour randomly
        int Mx = 5;
        int My = 3;
        int Px = 3;
        int Py = 3;
        long seed = 3;
        Dungeon dungeon = new Dungeon("1", "DoorBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer mercenary = new Mercenary(Mx, My, dungeon, seed);
        mercenary.add();

        assertFalse(mercenary.getArmour() != null);

    }

    @Test
    public void NoSwampMovementTest() {
        // test mercenary will not move into the swamp tile
        // due to the bfs searching
        Dungeon dungeon = new Dungeon("1", "name");
        Player player = new Player(2, 0, dungeon);
        dungeon.setPlayer(player);
        Mercenary Mercenary = new Mercenary(2, 2, dungeon, 0);
        Mercenary.add();
        // set the movement factor to 10
        SwampTile swampTile = new SwampTile(2, 1, dungeon, 10);
        swampTile.add();
        // add an obstacle
        Door door = new Door(1, 2, dungeon, 0);
        door.add();
        
        Mercenary.move();
        int MercenaryX = Mercenary.getX();
        int MercenaryY = Mercenary.getY();
        assertEquals(3, MercenaryX);
        assertEquals(2, MercenaryY);

        Mercenary.move();
        MercenaryX = Mercenary.getX();
        MercenaryY = Mercenary.getY();
        assertEquals(3, MercenaryX);
        assertEquals(1, MercenaryY);

        Mercenary.move();
        MercenaryX = Mercenary.getX();
        MercenaryY = Mercenary.getY();
        assertEquals(3, MercenaryX);
        assertEquals(0, MercenaryY);

        Mercenary.move();
        MercenaryX = Mercenary.getX();
        MercenaryY = Mercenary.getY();
        assertEquals(2, MercenaryX);
        assertEquals(0, MercenaryY);
    }

    @Test
    public void SwampMoveTest() {
        // test the situation when the mercenary moves into the swamp tile
        Dungeon dungeon = new Dungeon("1", "name");
        Player player = new Player(3, 3, dungeon);
        dungeon.setPlayer(player);
        Mercenary Mercenary = new Mercenary(3, 1, dungeon, 0);
        Mercenary.add();
        // set the movement factor to 3
        SwampTile swampTile = new SwampTile(2, 1, dungeon, 11);
        swampTile.add();

        // add an obstacle
        Door door = new Door(0, 0, dungeon, 0);
        door.add();
        Door door1 = new Door(0, 2, dungeon, 1);
        door1.add();
        Door door2 = new Door(1, 0, dungeon, 2);
        door2.add();
        Door door3 = new Door(1, 2, dungeon, 3);
        door3.add();
        Door door4 = new Door(2, 0, dungeon, 4);
        door4.add();
        Door door5 = new Door(2, 2, dungeon, 5);
        door5.add();
        Door door6 = new Door(3, 0, dungeon, 6);
        door6.add();
        Door door7 = new Door(3, 2, dungeon, 7);
        door7.add();


        // pick up the key
        Key key = new Key(3, 3, dungeon, 7);
        key.add();
        dungeon.tickPlayerAfterMoving();
        // move to the door
        player.playerMove(Direction.UP);
        // open the door
        dungeon.tickPlayerAfterMoving();
        assertTrue(door7.isOpen());

        player.setPosition(new Position(1, 1));

        
        // mercenary moves into the swamp tile
        Mercenary.move();
        int MercenaryX = Mercenary.getX();
        int MercenaryY = Mercenary.getY();
        assertEquals(3, MercenaryX);
        assertEquals(2, MercenaryY);

        // mercenary should be trapped on the swamp tile
        Mercenary.move();
        MercenaryX = Mercenary.getX();
        MercenaryY = Mercenary.getY();
        assertEquals(3, MercenaryX);
        assertEquals(3, MercenaryY);
    }

    @Test
    public void SwampeMoveTest() {
        // test the situation when the mercenary cannot find the player
        Dungeon dungeon = new Dungeon("1", "name");
        Player player = new Player(0, 1, dungeon);
        dungeon.setPlayer(player);
        Mercenary Mercenary = new Mercenary(2, 1, dungeon, 0);
        Mercenary.add();
        // add an obstacle
        Door door = new Door(1, 0, dungeon, 0);
        door.add();
        Door door10 = new Door(1, 1, dungeon, 0);
        door10.add();
        Door door1 = new Door(1, 2, dungeon, 0);
        door1.add();
        Door door2 = new Door(2, 0, dungeon, 0);
        door2.add();
        Door door3 = new Door(2, 2, dungeon, 0);
        door3.add();
        Door door4 = new Door(3, 0, dungeon, 0);
        door4.add();
        Door door5 = new Door(3, 1, dungeon, 0);
        door5.add();
        Door door6 = new Door(3, 2, dungeon, 0);
        door6.add();

        // mercenary should stay on the same place
        Mercenary.move();
        int MercenaryX = Mercenary.getX();
        int MercenaryY = Mercenary.getY();
        assertEquals(2, MercenaryX);
        assertEquals(1, MercenaryY);

        // mercenary should stay on the same place
        Mercenary.move();
        MercenaryX = Mercenary.getX();
        MercenaryY = Mercenary.getY();
        assertEquals(2, MercenaryX);
        assertEquals(1, MercenaryY);
    }

}

