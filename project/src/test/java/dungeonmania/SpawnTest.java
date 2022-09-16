package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.Goals.ExitGoal;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.Spider;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class SpawnTest {
    @Test
    public void assassinSpawnTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 2);
        dungeon.setGameMode("peaceful");
        dungeon.setGoals(new ExitGoal(dungeon));
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        for (int i = 0; i < 25; i++) {
            dungeon.spawnEnemies();
            dungeon.updateGoal();
        }

        dungeon.spawnEnemies();

        assertEquals(6, dungeon.getEnemies().size());
        assertEquals("assassin", dungeon.getEnemies().get(5).getType());
    }

    @Test
    public void spawnEnemyTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = assertDoesNotThrow(() -> controller.newGame("allEntities", "peaceful"));
        
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        newRes = controller.tick(null, Direction.RIGHT);
        List<EntityResponse> entities = newRes.getEntities();
        assertEquals(2, entities.stream().filter(n -> n.getType().equals("spider")).collect(Collectors.toList()).size());
        
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        assertEquals(2, entities.stream().filter(n -> n.getType().equals("zombieToast")).collect(Collectors.toList()).size());
    
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        assertEquals(2, entities.stream().filter(n -> n.getType().equals("mercenary")).collect(Collectors.toList()).size() + entities.stream().filter(n -> n.getType().equals("assassin")).collect(Collectors.toList()).size());
        assertEquals(5, entities.stream().filter(n -> n.getType().equals("spider")).collect(Collectors.toList()).size());
    }

    @Test
    public void spawnEnemiesHardModeTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse newRes = assertDoesNotThrow(() -> controller.newGame("allEntities", "hard"));
        
        for (int i = 0; i < 14; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        newRes = controller.tick(null, Direction.RIGHT);
        List<EntityResponse> entities = newRes.getEntities();
        assertEquals(2, entities.stream().filter(n -> n.getType().equals("zombieToast")).collect(Collectors.toList()).size());
        
        for (int i = 0; i < 34; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        newRes = controller.tick(null, Direction.RIGHT);
        entities = newRes.getEntities();
        assertEquals(1, entities.stream().filter(n -> n.getType().equals("hydra")).collect(Collectors.toList()).size());
    }
}
