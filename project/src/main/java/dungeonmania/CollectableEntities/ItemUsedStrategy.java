package dungeonmania.CollectableEntities;

import dungeonmania.movingentity.Player;

public abstract interface ItemUsedStrategy {
    /**
     * Apply the item to be used
     * @param player: the player to use the item
     */
    public void apply(Player player);
}
