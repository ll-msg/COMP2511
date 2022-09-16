package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Sunstone extends CollectableEntities {
    public static int count = 0;

    public Sunstone(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("sunstone" + count);
        setType("sunstone");
        count++;
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
    }
}
