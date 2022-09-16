package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Key;
import dungeonmania.StaticEntities.Door;
import dungeonmania.movingentity.Player;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DoorTest {
    @Test
    public void doorResponse() {
        Dungeon dungeon = new Dungeon("1", "name");
        Door Door0 = new Door(1, 1,dungeon, 0);
        EntityResponse Door0Response = new EntityResponse(Door0.getId(), Door0.getType(), Door0.getPosition(), Door0.isInteractable());
        assertEquals("lockedDoor", Door0Response.getType());
        assertEquals(new Position(1,1), Door0Response.getPosition());
        //assertEquals(true, Door0.isCanBlock());
        Door0.react();
        //assertEquals(false, Door0.isCanBlock());
        assertEquals(0, Door0.getKey());
    }

    @Test
    public void playerWithoutKey() {
        Dungeon dungeon = new Dungeon("1", "name");
        Door Door0 = new Door(1, 1,dungeon, 0);
        dungeon.getPlayer().initialisePlayerPosition(1, 0);
        Door0.add();
        Player player = dungeon.getPlayer();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        Position position = new Position(1, 0, 5);
        assertEquals(position, player.getPosition());
    }

    @Test
    public void playerWithKey() {
        Dungeon dungeon = new Dungeon("1", "name");
        Door Door0 = new Door(1, 3,dungeon, 0);
        Key key0 = new Key(1, 2, dungeon, 0);
        dungeon.getPlayer().initialisePlayerPosition(1, 1);
        Door0.add();
        key0.add();
        Player player = dungeon.getPlayer();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        Position position = new Position(1, 3, 5);
        player.playerMove(Direction.DOWN);
        player.playerTick();
        assertEquals(position, player.getPosition());
    }
}
