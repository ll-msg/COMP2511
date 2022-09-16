package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class SwampTileTest {
    @Test
    public void swampTileResponse() {
        Dungeon dungeon = new Dungeon("1", "name");
        SwampTile SwampTile0 = new SwampTile(1, 1, dungeon, 1);
        EntityResponse SwampTile0Response = new EntityResponse(SwampTile0.getId(), SwampTile0.getType(), SwampTile0.getPosition(), SwampTile0.isInteractable());
        assertEquals("swampTile", SwampTile0Response.getType());
        assertEquals(new Position(1,1), SwampTile0Response.getPosition());
        assertEquals(false, SwampTile0Response.isInteractable());
        assertEquals(1, SwampTile0.getMovementFactor());
    }
}
