package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class ZombieToastSpawnerTest {
    @Test
    public void exit_response() {
        Dungeon dungeon = new Dungeon("1", "name");
        ZombieToastSpawner ZombieToastSpawner0 = new ZombieToastSpawner(1, 1, dungeon);
        EntityResponse ZombieToastSpawner0Response = new EntityResponse(ZombieToastSpawner0.getId(), ZombieToastSpawner0.getType(), ZombieToastSpawner0.getPosition(), ZombieToastSpawner0.isInteractable());
        assertEquals("zombietoastspawner", ZombieToastSpawner0Response.getType());
        assertEquals(new Position(1,1), ZombieToastSpawner0Response.getPosition());
        assertEquals(true, ZombieToastSpawner0Response.isInteractable());
    }
    
}