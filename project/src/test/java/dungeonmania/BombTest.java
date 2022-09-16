package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.ActiveStrategy.OrStrategy;
import dungeonmania.CollectableEntities.BombCollectable;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class BombTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        BombCollectable bomb = new BombCollectable(5, 5, dungeon, new OrStrategy());
        EntityResponse entityResponse = new EntityResponse(bomb.getId(), bomb.getType(), bomb.getPosition(), bomb.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("bomb", entityResponse.getType());
    }
}
