package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class TheOneRing extends CollectableEntities {
    public static int count = 0;

    public TheOneRing(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("theOneRing" + count);
        setType("theOneRing");
        count++;
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addRing(this);
    }
}
