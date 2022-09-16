package dungeonmania.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.DoubleCalculation;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.NonPlayer;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Position;

public class NormalBattleStrategy implements NonPlayerBattleStrategy {
    private final double swordDamage = 50;
    private final double armourDefence = 0.7;
    private final double shieldDefence = 0.8;
    private Player player;
    private Inventory inventory;
    private Dungeon dungeon;
    private double playerTotalDamage;
    private double enemyTotalDamage;
    private double playerAttackDamage;
    private Armour armour = null;

    public NormalBattleStrategy(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        dungeon = player.getDungeon();
    }

    @Override
    public void battle(NonPlayer nonPlayer) {
        // player wont fight with ally
        if (nonPlayer.isAlly()) {
            return;
        }
        // update mercenaries detectedBattle field
        List<Position> DoubleAdjacentPositions = getDoubleAdjacentPositions();
        List<NonPlayer> NonPlayers = new ArrayList<>();
        for (Position position : DoubleAdjacentPositions) {
            NonPlayers.addAll(dungeon.getEnemiesWithPosition(position.getX(), position.getY()));
        }
        for (NonPlayer enemy : NonPlayers) {
            if (enemy instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) enemy;
                mercenary.setDetectedBattle(true);
            }
        }
        // set up enemy total damage
        playerAttackDamage = DoubleCalculation.mul(player.getHealth(), player.getAttackDamage());
        enemyTotalDamage = DoubleCalculation.mul(nonPlayer.getHealth(), nonPlayer.getAttackDamage());
        playerTotalDamage = playerAttackDamage;

        // check battle items
        if (inventory.isHaveBow()) {
            playerTotalDamage = DoubleCalculation.sum(playerTotalDamage, playerTotalDamage);
            inventory.useBow();
        }

        if (inventory.isHaveAnduril()) {
            if (nonPlayer.getIsBoss()) {
                playerTotalDamage = DoubleCalculation.sum(DoubleCalculation.mul(swordDamage, 3.0), playerTotalDamage);
                inventory.useAnduril();
            } else {
                playerTotalDamage = DoubleCalculation.sum(swordDamage, playerTotalDamage);
                inventory.useAnduril();
            }
        } else if (inventory.isHaveSword()) {
            playerTotalDamage = DoubleCalculation.sum(swordDamage, playerTotalDamage);
            inventory.useSword();
        }

        if (inventory.isHaveArmour()) {
            enemyTotalDamage = DoubleCalculation.mul(armourDefence, enemyTotalDamage);
            inventory.useArmour();
        }

        if (inventory.isHaveShield()) {
            enemyTotalDamage = DoubleCalculation.mul(shieldDefence, enemyTotalDamage);
            inventory.useShield();
        }

        if (inventory.isHaveMidnightArmour()) {
            enemyTotalDamage = DoubleCalculation.mul(shieldDefence, enemyTotalDamage);
            playerTotalDamage = DoubleCalculation.sum(swordDamage, playerTotalDamage);
        }

        if (nonPlayer.getArmour() != null) {
            Armour checkArmour = nonPlayer.getArmour();
            if (checkArmour.getDurability() > 0) {
                checkArmour.reduceEndurance();
                playerTotalDamage = DoubleCalculation.mul(armourDefence, playerTotalDamage);
                armour = checkArmour;
            } else {
                armour = null;
            }
        }

        if (nonPlayer.getType().equals("hydra")) {
            // get the random instance from inve tory
            Random random = inventory.getRandom();
            double currPlayerDamage = Math.abs(DoubleCalculation.div(playerTotalDamage, 5));

            if (inventory.isHaveAnduril()) {
                nonPlayer.receiveDamage(currPlayerDamage);
            } else {
                if (random.nextInt(100) < 50) {
                    nonPlayer.receiveDamage(currPlayerDamage);
                } else {
                    nonPlayer.setHealth(DoubleCalculation.sum(nonPlayer.getHealth(), currPlayerDamage));
                }
            }

            double currAllyDamage = Math.abs(DoubleCalculation.div(player.AlliesDamage(), 5));

            if (random.nextInt(100) < 50) {
                nonPlayer.receiveDamage(currAllyDamage);
            } else {
                nonPlayer.setHealth(DoubleCalculation.sum(nonPlayer.getHealth(), currAllyDamage));
            }

        } else {
            // check allies damage
            playerTotalDamage += player.AlliesDamage();

            // enemy receive damage accordingly
            nonPlayer.receiveDamage(Math.abs(DoubleCalculation.div(playerTotalDamage, 5)));
        }

        // player receive damage accordingly
        player.receiveDamage(Math.abs(DoubleCalculation.div(enemyTotalDamage, 10)));

        if (checkAlive(nonPlayer)) {
            battle(nonPlayer);
        }
    }

    /**
     * Find the positions in the range that the mercenary could detect a happneing
     * battle
     * 
     * @return List<Position>: list of positions in range
     */
    private List<Position> getDoubleAdjacentPositions() {
        Position battlePosition = player.getPosition();
        List<Position> DoubleAdjacentPositions = battlePosition.getAdjacentPositions();
        DoubleAdjacentPositions.add(new Position(battlePosition.getX() + 2, battlePosition.getY()));
        DoubleAdjacentPositions.add(new Position(battlePosition.getX() - 2, battlePosition.getY()));
        DoubleAdjacentPositions.add(new Position(battlePosition.getX(), battlePosition.getY() + 2));
        DoubleAdjacentPositions.add(new Position(battlePosition.getX(), battlePosition.getY() - 2));
        return DoubleAdjacentPositions;
    }

    /**
     * Check whether the both sides of the battle are alive or not; Use the One Ring
     * and transfer armour in specific situations. Remove entity if he is dead.
     * 
     * @param nonPlayer: the enemy of the battle
     * @return boolean: whether the two sides of the battle are both alive
     */
    private boolean checkAlive(NonPlayer nonPlayer) {
        boolean playerAlive = true;
        boolean enemyAlive = true;
        if (player.checkDeath()) {
            if (inventory.checkOneRing()) {
                player.setFullHealth();
                inventory.useOneRing();
            } else {
                playerAlive = false;
                player.remove();
            }
        }

        if (nonPlayer.checkDeath()) {
            enemyAlive = false;
            nonPlayer.remove();
            if (armour != null) {
                inventory.addArmour(armour);
                inventory.addItem(armour);
            }
            // get the random instance from inve tory
            Random random = inventory.getRandom();
            // generate Ring randomly
            if (random.nextInt(100) <= 10) {
                inventory.addNewRing();
            }

            // generate Anduril randomly
            if (random.nextInt(100) <= 10) {
                inventory.addNewAnduril();
            }

        }
        return playerAlive && enemyAlive;
    }

}
