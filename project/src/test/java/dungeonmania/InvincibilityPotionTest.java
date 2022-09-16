package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class InvincibilityPotionTest {
    @Test
    public void testCreation() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        InvincibilityPotion invincPotion = new InvincibilityPotion(5, 5, dungeon);
        EntityResponse entityResponse = new EntityResponse(invincPotion.getId(), invincPotion.getType(), invincPotion.getPosition(), invincPotion.isInteractable());
        assertEquals(new Position(5, 5), entityResponse.getPosition());
        assertEquals("invincibility_potion", entityResponse.getType());
    }
}

