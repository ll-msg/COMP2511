package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.CollectableEntities.Wood;


public class InventoryGetItemResponseTest {
    @Test
    public void testHaveItem() {
        Dungeon dungeon = new Dungeon("standard", "maze");
        Treasure treasure = new Treasure(1, 1, dungeon);
        treasure.add();
        assertEquals(0, dungeon.getInventory().getItemResponse().size());
        treasure.react(dungeon.getInventory());
        assertEquals(1, dungeon.getInventory().getItemResponse().size());
    }

    @Test
    public void testHaveCollectable() {
        Dungeon dungeon = new Dungeon("standard", "maze");
        Treasure treasure = new Treasure(1, 1, dungeon);
        Wood wood0 = new Wood(1, 2, dungeon);
        Wood wood1 = new Wood(1, 3, dungeon);
        treasure.add();
        wood0.add();
        wood1.add();
        treasure.react(dungeon.getInventory());
        wood0.react(dungeon.getInventory());
        wood1.react(dungeon.getInventory());
        dungeon.getInventory().buildShield();
        assertEquals(1, dungeon.getInventory().getItemResponse().size());
    }
}
