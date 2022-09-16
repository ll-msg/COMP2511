package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.HealthPotion;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class HealthPotionTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        HealthPotion healthPotion = new HealthPotion(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(healthPotion.getId(), healthPotion.getType(), healthPotion.getPosition(), healthPotion.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("health_potion", entityResponse.getType());
    }
}

