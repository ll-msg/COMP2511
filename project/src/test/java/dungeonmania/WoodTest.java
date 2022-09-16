package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Wood;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class WoodTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Wood wood = new Wood(5, 5, dungeon);
        EntityResponse keyResponse = new EntityResponse(wood.getId(), wood.getType(), wood.getPosition(), wood.isInteractable());
        assertEquals(new Position(5, 5), keyResponse.getPosition());
        assertEquals("wood", keyResponse.getType());
    }
}
