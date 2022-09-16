package dungeonmania.battle;

import java.util.Random;

import dungeonmania.Inventory;
import dungeonmania.movingentity.NonPlayer;

public class InvincibleBattleStrategy implements NonPlayerBattleStrategy {
    private Inventory inventory;

    public InvincibleBattleStrategy(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void battle(NonPlayer nonPlayer) {
        if (nonPlayer.isAlly()) {
            return;
        }
        // Kill enemy at once
        nonPlayer.remove();
        // get the random insatance from inventory
        Random random = inventory.getRandom();
        // generate Ring randomly

        if (random.nextInt(100) <= 10) {
            inventory.addNewRing();
        }

        if (random.nextInt(100) <= 10) {
            inventory.addNewAnduril();
        }

    }

}
