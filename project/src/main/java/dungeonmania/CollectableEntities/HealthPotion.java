package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.movingentity.Player;

public class HealthPotion extends CollectableEntities implements ItemUsedStrategy{
    public static int count = 0;

    public HealthPotion(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("healthPotion" + count);
        setType("health_potion");
        count++;
    }

    @Override
    public void apply(Player player) {
        player.setFullHealth();
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addItemToUse(this);
    }

}
