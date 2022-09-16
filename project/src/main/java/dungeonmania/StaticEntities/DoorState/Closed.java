package dungeonmania.StaticEntities.DoorState;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Inventory;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.StaticEntities.Door;

public class Closed implements DoorState{
    Door door;
    Inventory inventory;
    public Closed(Door door) {
        this.door = door;
        inventory = door.getDungeon().getInventory();
    }

    public boolean isBlocked() {
        return true;
    }

    public void tryOpen() {
        if (inventory.isHasSunstone()) {
            this.door.setState(this.door.getOpen());
            this.door.setType("openDoor");
        } else {
            List<Key> keys = new ArrayList<>();
            keys.addAll(inventory.getKeys());
            for (Key key : keys) {
                if (key.getKey() == door.getKey()) {
                    inventory.removeKey(key);
                    this.door.setState(this.door.getOpen());
                    this.door.setType("openDoor");
                }
            }
        }
    }
}
