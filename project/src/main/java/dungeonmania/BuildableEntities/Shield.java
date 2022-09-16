package dungeonmania.BuildableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Inventory;
import dungeonmania.CollectableEntities.CollectableEntities;

public class Shield extends BuildableEntities {
    public static int count = 0;
    private int durability = 10;

    public Shield(Inventory inventory) {
        super(inventory);
        setId("shield" + count);
        setType("shield");
        count++;
    }

    @Override
    public void build(){
        List<CollectableEntities> newItems = new ArrayList<>();
        List<CollectableEntities> items = this.getInventory().getItems();
        newItems.addAll(items);
        int woodCount = 0;
        int metalCount = 0;
        for(CollectableEntities item : newItems) {
            if (item.getType().equals("wood") && woodCount < 2) {
                items.remove(item);
                woodCount++;
            }

            if (this.getInventory().isHasSunstone()) {
                metalCount++;
                this.getInventory().removeSunstone();
            } else{
                if ((item.getType().equals("treasure") || item.getType().contains("Key")) && metalCount < 1) {
                    items.remove(item);
                    if (item.getType().contains("Key")) {
                        this.getInventory().getKeys().remove(item);
                    }
                    metalCount++;
                }
            }
        }
    }

    @Override
    public void reduceEndurance(Inventory inventory) {
        this.durability = durability - 1;
        if (durability == 0) {
            inventory.removeBuildables(this);
            inventory.removeShield(this);
        }
    }
}

