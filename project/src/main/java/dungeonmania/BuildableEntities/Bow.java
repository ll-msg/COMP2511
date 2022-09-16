package dungeonmania.BuildableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Inventory;
import dungeonmania.CollectableEntities.CollectableEntities;

public class Bow extends BuildableEntities {
    public static int count = 0;
    private int durability = 10;

    public Bow(Inventory inventory) {
        super(inventory);
        setId("bow" + count);
        setType("bow");
        count++;
    }

    @Override
    public void build(){
        List<CollectableEntities> newItems = new ArrayList<>();
        List<CollectableEntities> items = this.getInventory().getItems();
        newItems.addAll(items);
        int woodCount = 0;
        int arrowCount = 0;
        for(CollectableEntities item : newItems) {
            if (item.getType().equals("wood") && woodCount < 1) {
                items.remove(item);
                woodCount++;
            }

            if (item.getType().equals("arrows") && arrowCount < 3) {
                items.remove(item);
                arrowCount++;
            }
        }
    }

    @Override
    public void reduceEndurance(Inventory inventory) {
        this.durability = durability - 1;
        if (durability == 0) {
            inventory.removeBuildables(this);
            inventory.removeBow(this);
        }
    }
}
