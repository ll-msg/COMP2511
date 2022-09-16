package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.Sunstone;
import dungeonmania.CollectableEntities.Wood;

public class SceptreTest {
    @Test
    public void sceptreBuildTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        Wood Wood0 = new Wood(1, 2,dungeon);
        Key key = new Key(1, 4, dungeon,1);
        Sunstone sunstone = new Sunstone(1, 3, dungeon);
        Inventory inventory = dungeon.getInventory();
        Wood0.add();
        key.add();
        sunstone.add();
        Wood0.react(inventory);
        key.react(inventory);
        sunstone.react(inventory);
        assertTrue(inventory.getBuildableList().contains("sceptre"));
        assertEquals(3, inventory.getItems().size());
        inventory.buildSceptre();
        assertEquals(1, inventory.getBuildables().size());
        assertEquals(0, inventory.getItems().size());
    }
}