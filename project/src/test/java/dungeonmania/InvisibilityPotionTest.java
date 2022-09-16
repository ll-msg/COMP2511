package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class InvisibilityPotionTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        InvisibilityPotion invisiPotion = new InvisibilityPotion(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(invisiPotion.getId(), invisiPotion.getType(), invisiPotion.getPosition(), invisiPotion.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("invisibility_potion", entityResponse.getType());
    }
}

