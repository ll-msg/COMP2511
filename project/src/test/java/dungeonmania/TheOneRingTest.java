package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.TheOneRing;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class TheOneRingTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        TheOneRing theOneRing = new TheOneRing(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(theOneRing.getId(), theOneRing.getType(), theOneRing.getPosition(), theOneRing.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("theOneRing", entityResponse.getType());
    }
}
