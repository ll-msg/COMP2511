package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.Sunstone;
import dungeonmania.movingentity.ZombieToast;

public class MidnightArmourTest {
    @Test
    public void BuildTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        Armour armour0 = new Armour(1, 1, dungeon);
        Sunstone sunstone = new Sunstone(1, 3, dungeon);
        Inventory inventory = dungeon.getInventory();
        armour0.add();
        sunstone.add();
        armour0.react(inventory);
        sunstone.react(inventory);
        assertTrue(inventory.getBuildableList().contains("midnight_armour"));
        assertEquals(2, inventory.getItems().size());
        inventory.buildMidnightArmour();
        assertEquals(1, inventory.getBuildables().size());
        assertEquals(0, inventory.getItems().size());
    }

    @Test
    public void BuildTestWithZombie() {
        Dungeon dungeon = new Dungeon("1", "name");
        Armour armour0 = new Armour(1, 1, dungeon);
        Sunstone sunstone = new Sunstone(1, 3, dungeon);
        Inventory inventory = dungeon.getInventory();
        ZombieToast zombie = new ZombieToast(1,4, dungeon);
        zombie.add();
        armour0.add();
        sunstone.add();
        armour0.react(inventory);
        sunstone.react(inventory);
        assertFalse(inventory.getBuildableList().contains("midnight_armour"));
    }
}