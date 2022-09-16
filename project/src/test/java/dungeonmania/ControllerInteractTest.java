package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ControllerInteractTest {
    @Test
    public void testInteractBribe() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("allEntities", "peaceful");

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        DungeonResponse res = controller.tick(null, Direction.LEFT);

        List<EntityResponse> entities = res.getEntities();
        String mercenaryId = entities.stream().filter(n -> n.getType().equals("mercenary")).collect(Collectors.toList())
                .get(0).getId();
        assertThrows(InvalidActionException.class, () -> controller.interact(mercenaryId));

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(() -> controller.interact(mercenaryId));

    }

    @Test
    public void bribeWithoutEnoughItemTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced-2", "standard");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        DungeonResponse res1 = controller.tick(null, Direction.LEFT);
        List<EntityResponse> entities1 = res1.getEntities();
        String mercenaryId1 = entities1.stream().filter(n -> n.getType().equals("mercenary"))
                .collect(Collectors.toList()).get(0).getId();
        assertThrows(InvalidActionException.class, () -> controller.interact(mercenaryId1));

    }

    @Test
    public void testInteractDestoy() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = assertDoesNotThrow(() -> controller.newGame("allEntities", "peaceful"));
        List<EntityResponse> entities = newRes.getEntities();
        String spawner = entities.stream().filter(n -> n.getType().equals("zombietoastspawner"))
                .collect(Collectors.toList()).get(0).getId();
        assertThrows(InvalidActionException.class, () -> controller.interact(spawner));

        controller.tick(null, Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> controller.interact(spawner));
    }

    @Test
    public void testWrongId() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("Interact", "standard");
        controller.tick(null, Direction.RIGHT);
        assertThrows(IllegalArgumentException.class, () -> {
            controller.interact("wall0");
        });
    }
}
