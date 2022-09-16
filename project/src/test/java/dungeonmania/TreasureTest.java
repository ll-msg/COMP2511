package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class TreasureTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Treasure treasure = new Treasure(5, 5, dungeon);
        EntityResponse keyResponse = new EntityResponse(treasure.getId(), treasure.getType(), treasure.getPosition(), treasure.isInteractable());
        assertEquals(new Position(5, 5), keyResponse.getPosition());
        assertEquals("treasure", keyResponse.getType());
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
