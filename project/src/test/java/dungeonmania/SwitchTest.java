package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.ActiveStrategy.OrStrategy;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Switch;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
public class SwitchTest {
    @Test
    public void switch_response() {
        Dungeon dungeon = new Dungeon("1", "name");
        Switch Switch0 = new Switch(1, 1, dungeon, new OrStrategy());
        EntityResponse Switch0Response = new EntityResponse(Switch0.getId(), Switch0.getType(), Switch0.getPosition(), Switch0.isInteractable());
        assertEquals("switch", Switch0Response.getType());
        assertEquals(new Position(1,1), Switch0Response.getPosition());
        assertEquals(false, Switch0Response.isInteractable());
    }

    @Test
    public void switchTriggered() {
        Dungeon dungeon = new Dungeon("1", "name");
        Switch switch0 = new Switch(1, 1, dungeon, new OrStrategy());
        Boulder boulder0 = new Boulder(1, 1, dungeon);
        assertEquals(false, switch0.isSwitched());
        dungeon.entitiesAdd(switch0);
        dungeon.entitiesAdd(boulder0);
        assertEquals(true, switch0.isSwitched());
    }
}
