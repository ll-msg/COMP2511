package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Sword extends CollectableEntities {
    public static int count = 0;
    private int durability = 10;

    public Sword(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("sword" + count);
        setType("sword");
        count++;
    }

    /**
     * reduce 1 durability of the current buildableEntity
     * 
     * @param inventory: the inventory which the current entity belongs to
     */
    public void reduceEndurance(Inventory inventory) {
        setDurability(durability - 1);
        if (durability == 0) {
            inventory.removeItem(this);
            inventory.removeSword(this);
        }
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addSword(this);
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

}
