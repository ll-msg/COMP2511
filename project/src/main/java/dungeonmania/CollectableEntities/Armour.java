package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Armour extends CollectableEntities {
    public static int count = 0;
    private int durability = 10;

    public Armour(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("armour" + count);
        setType("armour");
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
            inventory.removeArmour(this);
        }
    }

    public void reduceEndurance() {
        this.durability = durability - 1;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public void react(Inventory inventory) {
        super.react(inventory);
        inventory.addArmour(this);
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

}
