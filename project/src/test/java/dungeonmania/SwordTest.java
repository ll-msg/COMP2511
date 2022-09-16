package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Sword;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class SwordTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Sword sword = new Sword(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(sword.getId(), sword.getType(), sword.getPosition(), sword.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("sword", entityResponse.getType());
    }
}
