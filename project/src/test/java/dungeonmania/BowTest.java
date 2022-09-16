package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.BuildableEntities.Bow;
import dungeonmania.response.models.EntityResponse;

public class BowTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "name");
        Bow bow = new Bow(dungeon.getInventory());
        EntityResponse entityResponse = new EntityResponse(bow.getId(), bow.getType(), null, bow.isInteractable());
        assertEquals("bow", entityResponse.getType());
    }
}
