package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

public class ControllerTickTest {
    @Test
    public void loadNewGameTest() throws IllegalArgumentException, IOException {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = assertDoesNotThrow(() -> controller.newGame("allEntities", "peaceful"));
        assertEquals(newRes.getDungeonName(), "allEntities");

        List<String> ls = new ArrayList<String>();
        ls.add("allEntities0");
        assertEquals(controller.allGames(), ls);

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        
        assertThrows(InvalidActionException.class, () -> controller.tick("key10086", Direction.NONE));

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        
        DungeonResponse res = controller.tick(null, Direction.DOWN);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("zombieToast")).collect(Collectors.toList()).size() > 0);
        assertTrue(entities.stream().filter(n -> n.getType().equals("mercenary")).collect(Collectors.toList()).size() > 0);
        assertTrue(entities.stream().filter(n -> n.getType().equals("spider")).collect(Collectors.toList()).size() > 0);

        List<ItemResponse> inventory = res.getInventory();
        assertTrue(inventory.stream().filter(n -> n.getType().equals("health_potion")).collect(Collectors.toList()).size() == 1);
        assertTrue(inventory.stream().filter(n -> n.getType().equals("invisibility_potion")).collect(Collectors.toList()).size() == 1);
        assertTrue(inventory.stream().filter(n -> n.getType().equals("invincibility_potion")).collect(Collectors.toList()).size() == 1);

        String keyId = inventory.stream().filter(n -> n.getType().contains("Key")).collect(Collectors.toList()).get(0).getId();
        assertThrows(IllegalArgumentException.class, () -> controller.tick(keyId, Direction.NONE));
    }
}
