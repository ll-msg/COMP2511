package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;

public class Key extends CollectableEntities{
    public static int count = 0;
    public int key;

    public Key(int x, int y, Dungeon dungeon, int key) {
        super(x, y, dungeon);
        setId("key" + count);
        if(getDungeon().getKeyNum() == 0) {
            setType("silverKey");
        } else {
            setType("goldKey");
        }
        this.key = key;
        count++;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public void react(Inventory inventory) {
        if(!inventory.isHaveKey()){
            super.react(inventory);
            inventory.getKeys().add(this);
        }
    }
}
