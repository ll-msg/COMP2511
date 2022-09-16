package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.movingentity.Player;

public class InvisibilityPotion extends CollectableEntities implements ItemUsedStrategy {
    public static int count = 0;
    public final int duration = 10;

    public InvisibilityPotion(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("invisibilityPotion" + count);
        setType("invisibility_potion");
        count++;
    }

    @Override
    public void apply(Player player) {
        player.setPlayerInvisibleState();
        player.setActionInvisibility();
        player.setPeacefulBattleState();
        player.setRemainingTime(duration);
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addItemToUse(this);
    }
}
