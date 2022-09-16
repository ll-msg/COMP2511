package dungeonmania.battle;

import dungeonmania.movingentity.NonPlayer;

public interface NonPlayerBattleStrategy {
    /**
     * Based on different state, conduct the battle between the player and the given
     * enemy
     * 
     * @param nonPlayer: the enemy of the battle
     */
    public void battle(NonPlayer nonPlayer);
}
