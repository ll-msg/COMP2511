package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Arrows;
import dungeonmania.CollectableEntities.Wood;

public class PickupTest {
    @Test
    public void testPickup() {
        Dungeon dungeon = new Dungeon("1", "name");
        Inventory inventory = new Inventory(dungeon);
        List<String> list1 = new ArrayList<>();
        list1.add("bow");
        Wood wood1 = new Wood(5, 5, dungeon);
        Wood wood2 = new Wood(5, 5, dungeon);
        Wood wood3 = new Wood(5, 5, dungeon);
        Wood wood4 = new Wood(5, 5, dungeon);
        Arrows arrows1 = new Arrows(5, 5, dungeon);
        Arrows arrows2 = new Arrows(5, 5, dungeon);
        Arrows arrows3 = new Arrows(5, 5, dungeon);
        Arrows arrows4 = new Arrows(5, 5, dungeon);
        wood1.react(inventory);
        wood2.react(inventory);
        wood3.react(inventory);
        wood4.react(inventory);
        arrows1.react(inventory);
        arrows2.react(inventory);
        arrows3.react(inventory);
        arrows4.react(inventory);
        assertEquals(4, inventory.getWoodNum());
        assertEquals(4, inventory.getArrowNum());
        assertEquals(list1, inventory.getBuildableList());
        inventory.buildBow();
        assertEquals(1, inventory.getArrowNum());
    }
}
