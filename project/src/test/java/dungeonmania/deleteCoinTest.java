package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.Treasure;

public class deleteCoinTest {
    @Test
    public void testCoindelete() {
        Dungeon dungeon = new Dungeon("standard", "maze");
        Inventory inventory = dungeon.getInventory();
        Treasure treasure = new Treasure(1, 1, dungeon);
        treasure.add();
        treasure.react(inventory);
        assertEquals(1, inventory.getItemResponse().size());
        inventory.deleteCoin();
        assertEquals(0, inventory.getItemResponse().size());
    }

    @Test
    public void testCoindeleteWithOtherItems() {
        Dungeon dungeon = new Dungeon("standard", "maze");
        Inventory inventory = dungeon.getInventory();
        Treasure treasure = new Treasure(1, 1, dungeon);
        Key key = new Key(1,1,dungeon,1);
        key.add();
        key.react(inventory);
        treasure.add();
        treasure.react(inventory);
        assertEquals(2, inventory.getItemResponse().size());
        inventory.deleteCoin();
        assertEquals(1, inventory.getItemResponse().size());
    }

    @Test
    public void testCoindeleteNoTreasure() {
        Dungeon dungeon = new Dungeon("standard", "maze");
        Inventory inventory = dungeon.getInventory();
        Key key = new Key(1,1,dungeon,1);
        key.add();
        key.react(inventory);
        assertEquals(1, inventory.getItemResponse().size());
        inventory.deleteCoin();
        assertEquals(1, inventory.getItemResponse().size());
    }

}
