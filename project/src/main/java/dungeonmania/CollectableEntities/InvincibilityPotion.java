package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.movingentity.Player;

public class InvincibilityPotion extends CollectableEntities implements ItemUsedStrategy {
    public static int count = 0;
    public final int duration = 10;

    public InvincibilityPotion(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("invincibilityPotion" + count);
        setType("invincibility_potion");
        count++;
    }

    @Override
    public void apply(Player player) {
        if (!getDungeon().getGameMode().equals("hard")) {
            player.setActionInvincibility();
            player.setInvincibleBattleState();
            player.setRemainingTime(duration);   
        }
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addItemToUse(this);
    }
}