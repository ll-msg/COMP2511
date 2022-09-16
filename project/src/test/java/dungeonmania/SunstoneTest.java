package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Sunstone;
import dungeonmania.StaticEntities.Door;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class SunstoneTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Sunstone sunstone = new Sunstone(5, 5, dungeon);
        EntityResponse sunstoneResponse = new EntityResponse(sunstone.getId(), sunstone.getType(), sunstone.getPosition(), sunstone.isInteractable());
        assertEquals(new Position(5, 5), sunstoneResponse.getPosition());
        assertEquals("sunstone", sunstoneResponse.getType());
    }

    @Test
    public void testPickupAndOpenDoor() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Sunstone sunstone = new Sunstone(5, 5, dungeon);
        Door door = new Door(1, 1, dungeon, 1);
        sunstone.add();
        sunstone.react(dungeon.getInventory());
        assertTrue(dungeon.isHasSunstone());
        assertTrue(dungeon.getInventory().getItems().contains(sunstone));
        assertFalse(door.isOpen());
        door.react();
        assertTrue(door.isOpen());
    }
    /*
    @Test
    public void testDungeons() {
        Dungeon dungeon = new Dungeon(10, 10);
        Treasure treasure = new Treasure(2, 2);
        Player player = new Player(dungeon, 1, 2);
        dungeon.addEntity(treasure);
        player.moveDown();
    }
    */
}
