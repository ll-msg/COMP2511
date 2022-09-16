package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Spider;
import dungeonmania.response.models.EntityResponse;

public class DungeonTest {
    @Test
    public void testaddItemsToDungeon() {
        Dungeon dungeon = new Dungeon("dungeon1", "advanced.json");
        
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();
        assertNotEquals(dungeon.findEntityById(spider.getId()), null);
        
        Wall wall = new Wall(4, 4, dungeon);
        wall.add();
        assertNotEquals(dungeon.findEntityById(wall.getId()), null);

        Treasure treasure = new Treasure(5, 5, dungeon);
        treasure.add();
        assertNotEquals(dungeon.findEntityById(treasure.getId()), null);
    }

    @Test
    public void testLoad() {
        DungeonManiaController controller = new DungeonManiaController();
        for (EntityResponse er : controller.newGame("advanced", "standard").getEntities()) {
            System.out.println(er.getId());
        }

    }

}
