package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderPushWithoutPlayerTest {
    @Test
    public void boulderMoveBlocked() {
        Dungeon dungeon = new Dungeon("1", "name");
        Boulder boulder0 = new Boulder(1, 1, dungeon);
        Wall wall0 = new Wall(1, 2, dungeon);
        dungeon.entitiesAdd(boulder0);
        dungeon.entitiesAdd(wall0);
        assertEquals(false, boulder0.canBePushed(Direction.DOWN));
        assertEquals(true, boulder0.canBePushed(Direction.RIGHT));
        boulder0.push(Direction.RIGHT);
        Position position = new Position(2, 1);
        assertEquals(position, boulder0.getPosition());
    }
    @Test
    public void boulderNotBlocked() {
        Dungeon dungeon = new Dungeon("1", "name");
        Boulder boulder0 = new Boulder(1, 1, dungeon);
        dungeon.entitiesAdd(boulder0);
        assertEquals(true, boulder0.canBePushed(Direction.RIGHT));
        boulder0.push(Direction.RIGHT);
        Position position = new Position(2, 1);
        assertEquals(position, boulder0.getPosition());
    }
}
