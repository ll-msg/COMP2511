package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dungeonmania.movingentity.Assassin;
import dungeonmania.movingentity.NonPlayer;
import dungeonmania.movingentity.Player;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.Wall;

/**
 * This test file mainly tests the movement of assassin
 * 
 * @author Yanran Wang
 */

public class AssassinTest {
    @Test
    public void basicMoveTest() {
        // test basic movement
        // without any obstacle
        int Mx = 2;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "BasicMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        Assassin assassin = new Assassin(Mx, My, dungeon);
        // assert assassin health
        assertEquals(80, assassin.getHealth());
        // assert damage
        assertEquals(2, assassin.getAttackDamage());
        assassin.add();

        assassin.move();
        // assassin moves towards to the player
        assertEquals(Mx + 1, assassin.getX());
        assertEquals(My, assassin.getY());
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
        Assassin assassin = new Assassin(Mx, My, dungeon);
        assassin.add();

        // create an invisible potion
        ItemUsedStrategy potion = new InvisibilityPotion(2, 2, dungeon);
        player.applyItem(potion);

        assassin.move();
        // assassin cannot find the player
        // stay in the same place
        assertEquals(Mx, assassin.getX());
        assertEquals(My, assassin.getY());
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
        Assassin assassin = new Assassin(Mx, My, dungeon);
        assassin.add();

        // create an invicinble potion
        ItemUsedStrategy potion = new InvincibilityPotion(2, 2, dungeon);
        player.applyItem(potion);

        assassin.move();
        // assassin run away from the player
        assertEquals(Mx, assassin.getX());
        assertEquals(My - 1, assassin.getY());
    }

    @Test
    public void wallBlockTest() {
        // test movement when assassin blocked by the wall
        int Mx = 5;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "WallBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        Assassin assassin = new Assassin(Mx, My, dungeon);
        assassin.add();
        Wall wall = new Wall(3, 3, dungeon);
        wall.add();

        assassin.move();
        assertEquals(Mx - 1, assassin.getX());
        assertEquals(My, assassin.getY());

        assassin.move();
        assertEquals(Mx - 2, assassin.getX());
        assertEquals(My, assassin.getY());
    }

    @Test
    public void boulderBlockTest() {
        // test movement when assassin blocked by the boulder
        // should stay at the same place
        int Mx = 5;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "BoulderBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        Assassin assassin = new Assassin(Mx, My, dungeon);
        assassin.add();
        Boulder boulder = new Boulder(3, 3, dungeon);
        boulder.add();

        assassin.move();
        assertEquals(Mx - 1, assassin.getX());
        assertEquals(My, assassin.getY());

        assassin.move();
        assertEquals(Mx - 2, assassin.getX());
        assertEquals(My, assassin.getY());
    }

    @Test
    public void DoorBlockTest() {
        // test movement when assassin blocked by the unopened door
        // should stay at the same place
        int Mx = 5;
        int My = 3;
        int Px = 3;
        int Py = 3;
        Dungeon dungeon = new Dungeon("1", "DoorBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        Assassin assassin = new Assassin(Mx, My, dungeon);
        assassin.add();
        Door door = new Door(3, 3, dungeon, 0);
        door.add();

        assassin.move();
        assertEquals(Mx - 1, assassin.getX());
        assertEquals(My, assassin.getY());

        assassin.move();
        assertEquals(Mx - 2, assassin.getX());
        assertEquals(My, assassin.getY());
    }

    @Test
    public void randomArmourTest() {
        // test Assassin has armour randomly
        int Mx = 5;
        int My = 3;
        int Px = 3;
        int Py = 3;
        long seed = 3;
        Dungeon dungeon = new Dungeon("1", "DoorBlockMovement");
        Player player = new Player(Px, Py, dungeon);
        dungeon.setPlayer(player);
        NonPlayer Assassin = new Assassin(Mx, My, dungeon, seed);
        Assassin.add();

        assertFalse(Assassin.getArmour() != null);

    }

    @Test
    public void NoSwampMovementTest() {
        // test Assassin will not move into the swamp tile
        // due to the bfs searching
        Dungeon dungeon = new Dungeon("1", "name");
        Player player = new Player(2, 0, dungeon);
        dungeon.setPlayer(player);
        Assassin Assassin = new Assassin(2, 2, dungeon, 0);
        Assassin.add();
        // set the movement factor to 10
        SwampTile swampTile = new SwampTile(2, 1, dungeon, 10);
        swampTile.add();
        // add an obstacle
        Door door = new Door(1, 2, dungeon, 0);
        door.add();
        
        Assassin.move();
        int AssassinX = Assassin.getX();
        int AssassinY = Assassin.getY();
        assertEquals(3, AssassinX);
        assertEquals(2, AssassinY);

        Assassin.move();
        AssassinX = Assassin.getX();
        AssassinY = Assassin.getY();
        assertEquals(3, AssassinX);
        assertEquals(1, AssassinY);

        Assassin.move();
        AssassinX = Assassin.getX();
        AssassinY = Assassin.getY();
        assertEquals(3, AssassinX);
        assertEquals(0, AssassinY);

        Assassin.move();
        AssassinX = Assassin.getX();
        AssassinY = Assassin.getY();
        assertEquals(2, AssassinX);
        assertEquals(0, AssassinY);
    }

    @Test
    public void SwampMoveTest() {
        // test the situation when the Assassin moves into the swamp tile
        Dungeon dungeon = new Dungeon("1", "name");
        Player player = new Player(1, 1, dungeon);
        dungeon.setPlayer(player);
        Assassin Assassin = new Assassin(3, 1, dungeon, 0);
        Assassin.add();
        // set the movement factor to 3
        SwampTile swampTile = new SwampTile(2, 1, dungeon, 3);
        swampTile.add();
        // add an obstacle
        Door door = new Door(0, 0, dungeon, 0);
        door.add();
        Door door1 = new Door(0, 2, dungeon, 0);
        door1.add();
        Door door2 = new Door(1, 0, dungeon, 0);
        door2.add();
        Door door3 = new Door(1, 2, dungeon, 0);
        door3.add();
        Door door4 = new Door(2, 0, dungeon, 0);
        door4.add();
        Door door5 = new Door(2, 2, dungeon, 0);
        door5.add();
        Door door6 = new Door(3, 0, dungeon, 0);
        door6.add();
        Door door7 = new Door(3, 2, dungeon, 0);
        door7.add();

        // Assassin moves into the swamp tile
        Assassin.move();
        int AssassinX = Assassin.getX();
        int AssassinY = Assassin.getY();
        assertEquals(2, AssassinX);
        assertEquals(1, AssassinY);

        // Assassin should be trapped on the swamp tile
        Assassin.move();
        AssassinX = Assassin.getX();
        AssassinY = Assassin.getY();
        assertEquals(2, AssassinX);
        assertEquals(1, AssassinY);
    }

    @Test
    public void SwampeMoveTest() {
        // test the situation when the Assassin cannot find the player
        Dungeon dungeon = new Dungeon("1", "name");
        Player player = new Player(0, 1, dungeon);
        dungeon.setPlayer(player);
        Assassin Assassin = new Assassin(2, 1, dungeon, 0);
        Assassin.add();
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

        // Assassin should stay on the same place
        Assassin.move();
        int AssassinX = Assassin.getX();
        int AssassinY = Assassin.getY();
        assertEquals(2, AssassinX);
        assertEquals(1, AssassinY);

        // Assassin should stay on the same place
        Assassin.move();
        AssassinX = Assassin.getX();
        AssassinY = Assassin.getY();
        assertEquals(2, AssassinX);
        assertEquals(1, AssassinY);
    }

}

