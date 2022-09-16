package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.Boulder;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class BoulderTest {
    @Test
    public void boulder_response() {
        Dungeon dungeon = new Dungeon("1", "name");
        Boulder boulder0 = new Boulder(1, 1, dungeon);
        EntityResponse boulder0Response = new EntityResponse(boulder0.getId(), boulder0.getType(), boulder0.getPosition(), boulder0.isInteractable());
        assertEquals("boulder", boulder0Response.getType());
        assertEquals(new Position(1,1), boulder0Response.getPosition());
        assertEquals(false, boulder0Response.isInteractable());
    }
}
