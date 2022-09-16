package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

public class LogicSwitchTest {
    @Test
    public void andBulbTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = controller.newGame("logic1", "peaceful");
        List<EntityResponse> entities = newRes.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_off")).collect(Collectors.toList()).size() == 1);

        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_off")).collect(Collectors.toList()).size() == 0);
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_on")).collect(Collectors.toList()).size() == 1);
    }

    @Test
    public void xorBulbTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = controller.newGame("logic2", "peaceful");
        List<EntityResponse> entities = newRes.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_off")).collect(Collectors.toList()).size() == 1);

        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_on")).collect(Collectors.toList()).size() == 0);
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_off")).collect(Collectors.toList()).size() == 1);
    }

    @Test
    public void notDoorTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = controller.newGame("logic3", "peaceful");
        List<EntityResponse> entities = newRes.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("switch_door_open")).collect(Collectors.toList()).size() == 1);

        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        
        assertTrue(entities.stream().filter(n -> n.getType().equals("switch_door_open")).collect(Collectors.toList()).size() == 0);
        assertTrue(entities.stream().filter(n -> n.getType().equals("switch_door")).collect(Collectors.toList()).size() == 1);
    }

    @Test
    public void orBombTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("logic4", "peaceful");

        DungeonResponse newRes = controller.tick(null, Direction.DOWN);
        List<ItemResponse> inventory = newRes.getInventory();
        assertTrue(inventory.stream().filter(n -> n.getType().equals("bomb")).collect(Collectors.toList()).size() == 1);

        String id = inventory.stream().filter(n -> n.getType().equals("bomb")).collect(Collectors.toList()).get(0).getId();
        controller.tick(id, Direction.NONE);

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        newRes = controller.tick(null, Direction.RIGHT);

        List<EntityResponse> entities = newRes.getEntities();
        assertEquals(0, entities.stream().filter(n -> n.getType().equals("switch")).collect(Collectors.toList()).size());
        assertEquals(0, entities.stream().filter(n -> n.getType().equals("boulder")).collect(Collectors.toList()).size());
        assertEquals(0, entities.stream().filter(n -> n.getType().equals("bombUsed")).collect(Collectors.toList()).size());
        assertEquals(1, entities.stream().filter(n -> n.getType().equals("player")).collect(Collectors.toList()).size());
    }

    @Test
    public void coandBulbTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = controller.newGame("logic5", "peaceful");
        List<EntityResponse> entities = newRes.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_off")).collect(Collectors.toList()).size() == 1);

        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_off")).collect(Collectors.toList()).size() == 0);
        assertTrue(entities.stream().filter(n -> n.getType().equals("light_bulb_on")).collect(Collectors.toList()).size() == 1);
    }
}
