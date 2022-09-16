package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Anduril;
import dungeonmania.CollectableEntities.Arrows;
import dungeonmania.CollectableEntities.Sword;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.CollectableEntities.Wood;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Player;

public class playerUnitTestInteract {
    @Test
    public void testBribeSuccess() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a mercenary next to player
        Mercenary mercenary = new Mercenary(1, 2, dungeon);
        mercenary.add();
        // crate a treasure at the position of the player
        Treasure gold1 = new Treasure(1, 1, dungeon);
        gold1.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        assertEquals(1, inventory.getTreasureNum());
        // bribe the mercenary
        player.interact(mercenary);
        assertEquals(1, player.getNumOfAllies());

        // create another treasure at the position of the player
        Treasure gold2 = new Treasure(1, 1, dungeon);
        gold2.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        // create another mercenary next to player
        Mercenary mercenary2 = new Mercenary(2, 1, dungeon);
        mercenary2.add();
        // bribe mercenary2
        player.interact(mercenary2);
        assertEquals(2, player.getNumOfAllies());

    }

    @Test
    public void testDestorySpawner() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a sword at the position of player
        Sword testSword = new Sword(1, 1, dungeon);
        testSword.add();
        dungeon.tickPlayerAfterMoving();
        // player now has sword
        assertTrue(dungeon.getInventory().isHaveSword());
        // create spawner next to the position of the player
        ZombieToastSpawner spawner = new ZombieToastSpawner(1, 2, dungeon);
        spawner.add();
        // destorty the spawner with sword
        player.interact(spawner);
        assertEquals(0, dungeon.getStaticEntities().size());
    }

    @Test
    public void testNoMoneyToBribe() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a mercenary next to player
        Mercenary mercenary = new Mercenary(1, 2, dungeon);
        mercenary.add();
        Inventory inventory = dungeon.getInventory();
        assertEquals(0, inventory.getTreasureNum());
        // bribe the mercenary
        assertThrows(InvalidActionException.class, () -> player.interact(mercenary));
    }

    @Test
    public void testDestorySpawnerWithBow() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // build bow
        Wood wood1 = new Wood(1, 1, dungeon);
        wood1.add();
        dungeon.tickPlayerAfterMoving();
        Arrows arrows1 = new Arrows(1, 1, dungeon);
        arrows1.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows2 = new Arrows(1, 1, dungeon);
        arrows2.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows3 = new Arrows(1, 1, dungeon);
        arrows3.add();
        dungeon.tickPlayerAfterMoving();
        dungeon.getInventory().buildBow();
        Inventory inventory = dungeon.getInventory();
        assertTrue(inventory.isHaveBow());
        // create a spawner
        ZombieToastSpawner spawner = new ZombieToastSpawner(1, 2, dungeon);
        assertDoesNotThrow(() -> spawner.interact(player));
    }

    @Test
    public void testDestorySpawnerWithShangFangBaoJian() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        Anduril superSword = new Anduril(1, 1, dungeon);
        superSword.add();
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        assertTrue(inventory.isHaveAnduril());
        // create a spawner
        ZombieToastSpawner spawner = new ZombieToastSpawner(1, 2, dungeon);
        assertDoesNotThrow(() -> spawner.interact(player));
    }
}
