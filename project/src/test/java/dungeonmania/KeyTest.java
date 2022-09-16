package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Key;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class KeyTest {
    @Test
    public void testDungeons() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Key key = new Key(1, 1, dungeon, 0);
        EntityResponse keyResponse = new EntityResponse(key.getId(), key.getType(), key.getPosition(), key.isInteractable());
        assertEquals(new Position(1, 1), keyResponse.getPosition());
        assertEquals(0, key.getKey());
    }
}
