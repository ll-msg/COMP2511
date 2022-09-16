package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Treasure extends CollectableEntities {
    public static int count = 0;

    public Treasure(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("treasure" + count);
        setType("treasure");
        count++;
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        getDungeon().treasureRemove(this);
    }

    @Override
    public void add() {
        getDungeon().collectableEntitiesAdd(this);
        getDungeon().treasureAdd(this);
    }
}
