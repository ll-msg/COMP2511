package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.ActiveStrategy.OrStrategy;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.StaticEntities.BombUsed;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Switch;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Direction;

public class BombExplodeTest {
    @Test
    public void ExplodeTest() {
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        Boulder boulder = new Boulder(2, 1, d);
        boulder.add();
        Switch switch1 = new Switch(3, 1, d, new OrStrategy());
        switch1.add();
        Wall wall = new Wall(3, 0, d);
        wall.add();
        d.initializeLogic();
        BombUsed bomb = new BombUsed(4, 1, d, new OrStrategy());
        bomb.add();
        d.initializeBombUsed();
        player.playerMove(Direction.RIGHT);
        d.updateLogicState();
        player.playerMove(Direction.UP);
        player.playerMove(Direction.RIGHT);
        assertEquals(3, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void ExplodeKillAllyTest() {
        int initialX = 1;
        int initialY = 1;
        Dungeon d = new Dungeon("1", "game1");
        Player player = new Player(initialX, initialY, d);
        player.add();
        d.setPlayer(player);
        Treasure gold = new Treasure(1, 1, d);
        gold.add();
        d.tickPlayerAfterMoving();
        Inventory inventory = d.getInventory();
        assertTrue(inventory.getTreasureNum() > 0);
        Boulder boulder = new Boulder(1, 2, d);
        boulder.add();
        Switch switch1 = new Switch(2, 2, d, new OrStrategy());
        switch1.add();
        Mercenary mercenary = new Mercenary(3, 1, d);
        mercenary.add();
        d.initializeLogic();
        BombUsed bomb = new BombUsed(3, 2, d, new OrStrategy());
        bomb.add();
        d.initializeBombUsed();
        // mercenary is not ally before interacting
        assertFalse(mercenary.isAlly());
        assertEquals(0, player.getNumOfAllies());
        assertDoesNotThrow(() -> mercenary.interact(player));
        // mercenary is ally after interacting
        assertTrue(mercenary.isAlly());
        assertEquals(1, player.getNumOfAllies());
        player.playerMove(Direction.LEFT);
        player.playerMove(Direction.DOWN);
        player.playerMove(Direction.RIGHT);
        d.updateLogicState();

        assertEquals(0, player.getNumOfAllies());
        assertEquals(0, d.getEnemies().size());

    }
}
