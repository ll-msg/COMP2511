package dungeonmania.BuildableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Inventory;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.CollectableEntities;

public class MidnightArmour extends BuildableEntities {
    public static int count = 0;

    public MidnightArmour(Inventory inventory) {
        super(inventory);
        setId("midnight_armour" + count);
        setType("midnight_armour");
        count++;
    }

    @Override
    public void build() {
        List<CollectableEntities> newItems = new ArrayList<>();
        List<CollectableEntities> items = this.getInventory().getItems();
        newItems.addAll(items);
        int armourCount = 0;
        for (CollectableEntities item : newItems) {
            if (item.getType().equals("armour") && armourCount < 1) {
                items.remove(item);
                this.getInventory().removeArmour((Armour) item);
                armourCount++;
            }
        }
        this.getInventory().removeSunstone();
    }

}
