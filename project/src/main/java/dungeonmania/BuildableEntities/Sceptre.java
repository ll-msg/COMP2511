package dungeonmania.BuildableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Inventory;
import dungeonmania.CollectableEntities.CollectableEntities;

public class Sceptre extends BuildableEntities {
    public static int count = 0;

    public Sceptre(Inventory inventory) {
        super(inventory);
        setId("sceptre" + count);
        setType("sceptre");
        count++;
    }

    @Override
    public void build(){
        List<CollectableEntities> newItems = new ArrayList<>();
        List<CollectableEntities> items = this.getInventory().getItems();
        Inventory inventory = this.getInventory();
        newItems.addAll(items);
        int woodCount = 0;
        int arrowCount = 0;
        int metalCount = 0;
        this.getInventory().removeSunstone();
        for(CollectableEntities item : newItems) {
            if (inventory.getWoodNum() > 0){
                if (item.getType().equals("wood") && woodCount < 1) {
                    items.remove(item);
                    woodCount++;
                }
            } else {
                if (item.getType().equals("arrows") && arrowCount < 2) {
                    items.remove(item);
                    arrowCount++;
                }
            }
            if (this.getInventory().isHasSunstone() && metalCount < 1) {
                metalCount++;
                this.getInventory().removeSunstone();
            } 
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
