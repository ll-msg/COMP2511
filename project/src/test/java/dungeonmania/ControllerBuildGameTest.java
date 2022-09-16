package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
public class ControllerBuildGameTest {
    @Test
    public void testBuildShieldSuccess() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() ->{controller.newGame("build", "standard");});
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertDoesNotThrow(()->{controller.build("shield");}); 
    }

    @Test
    public void testBuildBowSuccess() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() ->{controller.newGame("buildbow", "standard");});
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertDoesNotThrow(()->{controller.build("bow");}); 
    }

    @Test
    public void testBuildWrongType() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() ->{controller.newGame("build", "standard");});
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertThrows(IllegalArgumentException.class,()->{controller.build("wall");}); 
    }

    @Test
    public void testBuildNotEnoughMaterial() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() ->{controller.newGame("build", "standard");});
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class,()->{controller.build("bow");}); 
    }
}
