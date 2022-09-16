package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.Exit;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Exittest {
    @Test
    public void exit_response() {
        Dungeon dungeon = new Dungeon("1", "name");
        Exit exit0 = new Exit(1, 1, dungeon);
        EntityResponse exit0Response = new EntityResponse(exit0.getId(), exit0.getType(), exit0.getPosition(), exit0.isInteractable());
        assertEquals("exit", exit0Response.getType());
        assertEquals(new Position(1,1), exit0Response.getPosition());
        assertEquals(false, exit0Response.isInteractable());
    }
    
}
