package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Armour;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class ArmourTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Armour armour = new Armour(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(armour.getId(), armour.getType(), armour.getPosition(), armour.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("armour", entityResponse.getType());
    }
}
