package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.Portal;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Player;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTest  {
    @Test
    public void portalResponse() {
        Dungeon dungeon = new Dungeon("1", "name");
        Portal Portal0 = new Portal(1, 1, dungeon, "Blue");
        EntityResponse Portal0Response = new EntityResponse(Portal0.getId(), Portal0.getType(), Portal0.getPosition(), Portal0.isInteractable());
        assertEquals("portal", Portal0Response.getType());
        assertEquals(new Position(1,1), Portal0Response.getPosition());
        assertEquals(false, Portal0Response.isInteractable());
        assertEquals("Blue", Portal0.getColour());
    }
    
    @Test
    public void portalTeleportPlayerTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        Portal Portal0 = new Portal(1, 1, dungeon, "Blue");
        Portal Portal1 = new Portal(10, 11, dungeon, "Blue");
        Position position = new Position(10, 11, 5);
        Portal0.add();
        Portal1.add();
        dungeon.getPlayer().initialisePlayerPosition(1, 0);
        Player player = dungeon.getPlayer();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        assertEquals(position, player.getPosition());
    }

    @Test
    public void portalTeleportNonPlayerTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        Portal Portal0 = new Portal(1, 1, dungeon, "Blue");
        Portal Portal1 = new Portal(10, 11, dungeon, "Blue");
        Mercenary mercenary = new Mercenary(1, 1, dungeon);
        Position position = new Position(10, 11, 4);
        Portal0.add();
        Portal1.add();
        mercenary.add();
        mercenary.teleport(Portal0);
        assertEquals(position, mercenary.getPosition());
    }
}