package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.ActiveStrategy.ActiveStrategy;
import dungeonmania.StaticEntities.BombUsed;
import dungeonmania.movingentity.Player;

public class BombCollectable extends CollectableEntities implements ItemUsedStrategy {
    public static int count = 0;
    private ActiveStrategy strategy;

    public BombCollectable(int x, int y, Dungeon dungeon, ActiveStrategy strategy) {
        super(x, y, dungeon);
        setId("bombCollectable" + count);
        setType("bomb");
        this.strategy = strategy;
        count++;
    }

    @Override
    public void apply(Player player) {
        BombUsed bombUsed = new BombUsed(player.getX(), player.getY(), getDungeon(), strategy);
        bombUsed.add();
        getDungeon().initializeBombUsed();
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addItemToUse(this);
    }    
}
