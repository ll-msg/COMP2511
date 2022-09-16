package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.BuildableEntities.Shield;
import dungeonmania.response.models.EntityResponse;

public class ShieldTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "name");
        Shield shield = new Shield(dungeon.getInventory());
        EntityResponse entityResponse = new EntityResponse(shield.getId(), shield.getType(), null, shield.isInteractable());
        assertEquals("shield", entityResponse.getType());
    }
}
