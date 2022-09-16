package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.CollectableEntities.InvisibilityPotion;

public class ZombieTest {
    @Test
    public void movementBlockedByWalls() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        ZombieToast zombie = new ZombieToast(initialX, initialY, d);
        // block the zombie by wall
        Wall wall1 = new Wall(2, 1, d);
        wall1.add();
        Wall wall2 = new Wall(2, 3, d);
        wall2.add();
        Wall wall3 = new Wall(1, 2, d);
        wall3.add();
        Wall wall4 = new Wall(3, 2, d);
        wall4.add();
        zombie.move();
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();
        // zombie should not move
        assertEquals(initialX, zombieX);
        assertEquals(initialY, zombieY);

    }

    @Test
    public void movementBlockedByBoulder() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        ZombieToast zombie = new ZombieToast(initialX, initialY, d);
        // block the zombie by Boulder
        Boulder wall1 = new Boulder(2, 1, d);
        wall1.add();
        Boulder wall2 = new Boulder(2, 3, d);
        wall2.add();
        Boulder wall3 = new Boulder(1, 2, d);
        wall3.add();
        Boulder wall4 = new Boulder(3, 2, d);
        wall4.add();
        zombie.move();
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();
        // zombie should not move
        assertEquals(initialX, zombieX);
        assertEquals(initialY, zombieY);

    }

    @Test
    public void zombieMovmentTest() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // use random seed for geanerating number [0, 100)
        ZombieToast zombie = new ZombieToast(initialX, initialY, d, 0);
        // assert health
        assertEquals(50, zombie.getHealth());
        // aseert attackDamage
        assertEquals(1, zombie.getAttackDamage());
        // 60(setup armour) 48 29 47 15 53 91
        zombie.move();
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();

        // 48 -> zombie should move up
        assertEquals(initialX, zombieX);
        assertEquals(initialY - 1, zombieY);
        // 29 -> zombie should move up
        zombie.move();
        zombieX = zombie.getX();
        zombieY = zombie.getY();
        assertEquals(initialX, zombieX);
        assertEquals(initialY - 2, zombieY);
        // 47 -> zombie should move up
        zombie.move();
        zombieX = zombie.getX();
        zombieY = zombie.getY();
        assertEquals(initialX, zombieX);
        assertEquals(initialY - 3, zombieY);
        // 15 -> zombie should move down
        zombie.move();
        zombieX = zombie.getX();
        zombieY = zombie.getY();
        assertEquals(initialX, zombieX);
        assertEquals(initialY - 2, zombieY);
        // 53 -> zombie should move left
        zombie.move();
        zombieX = zombie.getX();
        zombieY = zombie.getY();
        assertEquals(initialX - 1, zombieX);
        assertEquals(initialY - 2, zombieY);
        // 91 -> zombie should move right
        zombie.move();
        zombieX = zombie.getX();
        zombieY = zombie.getY();
        assertEquals(initialX, zombieX);
        assertEquals(initialY - 2, zombieY);
    }

    @Test
    public void testZombieArmour() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // use random seed for geanerating number [0, 100)
        ZombieToast zombie = new ZombieToast(initialX, initialY, d, 2);

        assertTrue(zombie.getArmour() != null);
    }

    @Test
    public void testZombieMoveLockedDoor() {
        Dungeon d = new Dungeon("1", "name");
        int initialX = 2;
        int initialY = 2;
        // use random seed for geanerating number [0, 100)
        ZombieToast zombie = new ZombieToast(initialX, initialY, d, 0);
        zombie.add();
        Door lockedDoor = new Door(2, 1, d, 1);
        lockedDoor.add();
        // 60(setup armour) 48
        zombie.move();
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();

        // 48 -> zombie should move up
        assertEquals(initialX, zombieX);
        assertEquals(initialY, zombieY);
    }

    @Test
    public void testZombieMoveWhenPlayerInvisibleWithSeed0() {
        // test movement when player is invincible
        int Zx = 1;
        int Zy = 3;
        int Px = 3;
        int Py = 3;
        Dungeon d = new Dungeon("1", "InvincibleMovement");
        Player player = new Player(Px, Py, d);
        player.add();
        d.setPlayer(player);
        ZombieToast zombie = new ZombieToast(Zx, Zy, d, 0);
        zombie.add();

        // create an invicinble potion
        ItemUsedStrategy potion = new InvisibilityPotion(4, 4, d);
        player.applyItem(potion);
        player.playerMove(Direction.NONE);
        zombie.move();
        // zombie movement should not be affected
        assertEquals(Zx, zombie.getX());
        assertEquals(Zy - 1, zombie.getY());

    }

}
