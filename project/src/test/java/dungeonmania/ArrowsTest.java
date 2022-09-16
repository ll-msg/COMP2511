package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Arrows;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class ArrowsTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Arrows arrows = new Arrows(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(arrows.getId(), arrows.getType(), arrows.getPosition(), arrows.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("arrows", entityResponse.getType());
    }
}
