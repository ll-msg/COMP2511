package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.Wall;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Walltest {
    @Test
    public void Wall_response() {
        Dungeon dungeon = new Dungeon("1", "name");
        Wall wall0 = new Wall(1, 1, dungeon);
        EntityResponse wall0Response = new EntityResponse(wall0.getId(), wall0.getType(), wall0.getPosition(), wall0.isInteractable());
        assertEquals("wall", wall0Response.getType());
        assertEquals(new Position(1,1), wall0Response.getPosition());
        assertEquals(false, wall0Response.isInteractable());
    }
    
}
