package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Wood extends CollectableEntities {
    public static int count = 0;

    public Wood(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("wood" + count);
        setType("wood");
        count++;
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
    }
}
