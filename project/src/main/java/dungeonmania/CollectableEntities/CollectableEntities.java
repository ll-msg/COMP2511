package dungeonmania.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.Inventory;

public abstract class CollectableEntities extends Entity {
    private boolean isPicked;
    
    public CollectableEntities(int x, int y,Dungeon dungeon) {
        super(x,y,dungeon);
        this.setInteractable(false);
        this.setCanBlock(false);
        isPicked = false;
    }

    @Override
    public void react(Inventory inventory) {
        this.isPicked = true;
        this.remove();
        inventory.addItem(this);
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean isPicked) {
        this.isPicked = isPicked;
    }

    @Override
    public void add() {
        getDungeon().collectableEntitiesAdd(this);
    }

    @Override
    public void remove() {
        getDungeon().collectableEntitesRemove(this);
    }
}
