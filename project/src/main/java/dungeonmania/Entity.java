package dungeonmania;

import dungeonmania.movingentity.Player;
import dungeonmania.util.Position;

public abstract class Entity {
    private Position position;
    private String id;
    private String type;
    private Dungeon dungeon;
    private boolean isInteractable;
    private boolean canBlock;
    
    public Entity(int x, int y, Dungeon dungeon) {
        position = new Position(x, y);
        this.dungeon = dungeon;
    }

    public boolean isCanBlock() {
        return canBlock;
    }

    public void setCanBlock(boolean canBlock) {
        this.canBlock = canBlock;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    /**
     * Add this entity into it's binded dungeon's corresponding lists.
     */
    public void add() {}

    /**
     * Remove this entity from it's binded dungeon's corresponding lists.
     */
    public void remove() {}
    
    /**
     * Will do different reaction when the player is in the same loaction with the entity (normally for static entities).
     */
    public void react() {}

    /**
     * Will be picked up by player and be put inside the inventory when the player is in the same location with the entity.
     * @param inventory the inventory the collectiable entity will be put in.
     */
    public void react(Inventory inventory) {}

    /**
     * will interact with the player if the entity is interactable
     * @param player the player whos interacting with the entity
     */
    public void interact(Player player) {}
}
