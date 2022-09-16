package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Anduril;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class AndurilTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Anduril anduril = new Anduril(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(anduril.getId(), anduril.getType(), anduril.getPosition(), anduril.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("anduril", entityResponse.getType());
    }
}
