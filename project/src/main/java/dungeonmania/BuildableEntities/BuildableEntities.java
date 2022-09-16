package dungeonmania.BuildableEntities;

import dungeonmania.Inventory;

public abstract class BuildableEntities {
    private String id;
    private String type;
    private boolean isInteractable;
    private Inventory inventory;

    public BuildableEntities(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * build a buildableEntity
     */
    public abstract void build();

    /**
     * reduce 1 durability of the current buildableEntity
     * 
     * @param inventory: the inventory which the current entity belongs to
     */
    public void reduceEndurance(Inventory inventory) {
    }
}
