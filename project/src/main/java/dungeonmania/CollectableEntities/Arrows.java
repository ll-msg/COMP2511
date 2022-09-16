package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Arrows extends CollectableEntities {
    public static int count = 0;

    public Arrows(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("arrows" + count);
        setType("arrows");
        count++;
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
    }
}
