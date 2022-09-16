package dungeonmania.movingentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dungeonmania.DoubleCalculation;
import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.battle.InvincibleBattleStrategy;
import dungeonmania.battle.NonPlayerBattleStrategy;
import dungeonmania.battle.NormalBattleStrategy;
import dungeonmania.battle.PeacefulBattleStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Entity;

public class Player extends MovingEntity {
    private double maxHealth = 100;
    private final int layer = 5;
    private final int damage = 1;
    private int remainingTime = 0;
    private Direction direction;
    private Inventory inventory;
    private String gameMode = "normal";
    private PlayerVisitStrategy currentPlayerState;
    private PlayerVisitStrategy playerNormalState;
    private PlayerVisitStrategy playerInvisibleState = new PlayerInvisibleStrategy();

    private EnemyActionStrategy currentEnemyState;
    private EnemyActionStrategy normalState = new NormalStrategy();
    private EnemyActionStrategy invisibilityState = new InvisibilityStrategy();
    private EnemyActionStrategy invincibilityState;

    private NonPlayerBattleStrategy peacefulBattleState = new PeacefulBattleStrategy();
    private NonPlayerBattleStrategy normalBattleState;
    private NonPlayerBattleStrategy invincibleBattleState;
    private NonPlayerBattleStrategy currentNonPlayerBattleState;

    private List<Mercenary> allies = new ArrayList<>();

    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        // initialise current player movement state
        setId("Obiwan");
        setType("player");
        playerNormalState = new PlayerNormalStrategy(this);
        currentPlayerState = playerNormalState;
        // bind the inventory to player
        this.inventory = dungeon.getInventory();
        // setup different NonPlayer battle states
        normalBattleState = new NormalBattleStrategy(this, inventory);
        invincibleBattleState = new InvincibleBattleStrategy(inventory);
        // initialise current NonPlayer battle state
        currentNonPlayerBattleState = normalBattleState;
        invincibilityState = new InvincibilityStrategy(this);
        setHealth(maxHealth);
        setAttackDamage(damage);
    }

    /**
     * Regenerate player to full health
     */
    public void setFullHealth() {
        setHealth(maxHealth);
    }

    /**
     * Use the entered items and notify non-players of the current combat and
     * movement status
     * 
     * @param item
     */
    public void applyItem(ItemUsedStrategy item) {
        item.apply(this);
        notifyEnemies();
    }

    /**
     * Move the player's position according to the input direction
     * 
     * @param direction The direction the player will move next
     */
    public void playerMove(Direction direction) {
        // record the direction
        setDirection(direction);
        // move
        Position targetPosition = getPosition().translateBy(direction);
        if (checkPositionValid(targetPosition)) {
            setPosition(targetPosition);
        }
        // update Poition duration after the movement
        updateDuration();
        notifyEnemies();
    }

    /**
     * Let player interacts with entities that overlap the current player position
     */
    public void playerTick() {
        notifyEnemies();
        // Interact with overlapping objects
        List<Entity> overlappingEntities = getDungeon().getEntitiesWithPosition(getX(), getY());
        for (Entity entity : overlappingEntities) {
            entity.react();
            entity.react(inventory);
        }
    }

    /**
     * Set the player's current movement state to invisible
     */
    public void setPlayerInvisibleState() {
        currentPlayerState = playerInvisibleState;
    }

    /**
     * Set the current movement state of non-players to the state of facing
     * invisible player
     */
    public void setActionInvisibility() {
        currentEnemyState = invisibilityState;
    }

    /**
     * Set the current movement state of non-players to the state of facing the
     * invincible player
     */
    public void setActionInvincibility() {
        currentEnemyState = invincibilityState;
    }

    /**
     * Set the current battle state of non-players to peaceful
     */
    public void setPeacefulBattleState() {
        currentNonPlayerBattleState = peacefulBattleState;
    }

    /**
     * Set the current battle state of non-players to the state of facing the
     * invincible player
     */
    public void setInvincibleBattleState() {
        currentNonPlayerBattleState = invincibleBattleState;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Reset the player position according to the input x and y coordinates, and
     * update the health or nonPlayer battle state according to the game mode of the
     * dungeon
     * 
     * @param x x coordinate of the player
     * @param y y coordinate of the player
     */
    public void initialisePlayerPosition(int x, int y) {
        // set up player heath according to the game mode
        if (getDungeon().getGameMode().equals("hard")) {
            maxHealth = 80;
        }
        setHealth(maxHealth);
        // set up battle state according to the game mode
        if (getDungeon().getGameMode().equals("peaceful")) {
            gameMode = "Peaceful";
            currentNonPlayerBattleState = peacefulBattleState;
        }
        Position initialisePosition = new Position(x, y, layer);
        this.setPosition(initialisePosition);
    }

    /**
     * Let the player bribe the input mercenary
     * 
     * @param mercenary target mercenary for bribery
     * @throws InvalidActionException If the player is not within 2 cardinal tiles
     *                                to the input mercenary or if the player does
     *                                not have any gold and attempts to bribe the
     *                                input mercenary
     */
    public void interact(Mercenary mercenary) throws InvalidActionException {
        Position mercenaryPosition = mercenary.getPosition();
        // check range
        if (!checkBribeRange(mercenaryPosition)) {
            throw new InvalidActionException("Target mercenary is out of range!");
        }
        // check sceptre
        if (inventory.isHaveSceptre()) {
            // control the mercenary
            addAlly(mercenary);
            // set control time 10 ticks, add 1 as offset
            mercenary.setControlTime(10);
        } else {
            if (inventory.isHasSunstone()) {
                // check sunstone
                addAlly(mercenary);
            } else if (inventory.getTreasureNum() > 0) {
                // check gold
                inventory.deleteCoin();
                addAlly(mercenary);

            } else {
                throw new InvalidActionException("You don't have enough item to pay bribes!");
            }
        }

    }

    /**
     * Let the player destroy the input zombie spawner
     * 
     * @param spawner target zombie spawner to destory
     * @throws InvalidActionException If the player is not cardinally adjacent to
     *                                the input spawner or if the player does not
     *                                have a weapon
     */
    public void interact(ZombieToastSpawner spawner) throws InvalidActionException {
        Position spawnerPosition = spawner.getPosition();
        if (!checkSpawnerAdjacent(spawnerPosition)) {
            throw new InvalidActionException("Player is not cardinally adjacent to the target spawner!");
        }
        // the durability of bow or sword is reduced when checking them

        if (inventory.isHaveSword()) {
            inventory.useSword();
        } else if (inventory.isHaveBow()) {
            inventory.useBow();
        } else if (inventory.isHaveAnduril()) {
            inventory.useAnduril();
        } else {
            throw new InvalidActionException("Player does not have a weapon to destroy the target spawner!");
        }

        spawner.remove();
    }

    /**
     * Let the player bribe the input assassin
     * 
     * @param assassin target assassin for bribery
     * @throws InvalidActionExceptionIf the player is not within 2 cardinal tiles to
     *                                  the input assassin or if the player does not
     *                                  have enough items and attempts to bribe the
     *                                  input mercenary
     */
    public void bribeAssassin(Assassin assassin) throws InvalidActionException {
        Position assassinPosition = assassin.getPosition();
        // check range
        if (!checkBribeRange(assassinPosition)) {
            throw new InvalidActionException("Target mercenary is out of range!");
        }
        // check sceptre
        if (inventory.isHaveSceptre()) {
            // control the assassin
            addAlly(assassin);
            // set control time 10 ticks, add 1 as offset
            assassin.setControlTime(10);
        } else {
            if (inventory.checkOneRing()) {
                // check oneRing
                if (inventory.isHasSunstone()) {
                    // check sunstone
                    inventory.useOneRing();
                    addAlly(assassin);
                } else if (inventory.getTreasureNum() > 0) {
                    inventory.deleteCoin();
                    inventory.useOneRing();
                    addAlly(assassin);
                } else {
                    throw new InvalidActionException("You don't have enough item to pay bribes!");
                }
            } else {
                throw new InvalidActionException("You don't have enough item to pay bribes!");
            }

        }

    }

    /**
     * Remove the input ally
     */
    public void removeAlly(Mercenary mercenary) {
        allies.remove(mercenary);
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Calculate the damage bonus provided by the player's allies
     * 
     * @return the number of damage
     */
    public double AlliesDamage() {
        double damage = 0;
        for (Mercenary ally : allies) {
            damage += DoubleCalculation.mul(ally.getHealth(), ally.getAttackDamage());
        }
        return damage;
    }

    /**
     * Calculate the number of allies of the player
     * 
     * @return the number of allies
     */
    public int getNumOfAllies() {
        return allies.size();
    }

    @Override
    public boolean visit(Wall wall) {
        return currentPlayerState.visit(wall);
    }

    @Override
    public boolean visit(Boulder boulder) {
        return currentPlayerState.visit(boulder);
    }

    @Override
    public boolean visit(Door door) {
        return currentPlayerState.visit(door);
    }

    @Override
    public boolean visit(SwampTile swampTile) {
        return true;
    }

    @Override
    public boolean visit(SwitchDoor switchdoor) {
        return currentPlayerState.visit(switchdoor);
    }

    @Override
    public void remove() {
        getDungeon().entitiesRemove(this);
    }

    @Override
    public void add() {
        getDungeon().entitiesAdd(this);
    }

    /**
     * Update the poition duration time of the player
     */
    private void updateDuration() {
        if (remainingTime != 0) {
            remainingTime = remainingTime - 1;
        } else {
            // update state for player and non-player entities
            currentEnemyState = normalState;
            currentPlayerState = playerNormalState;
            currentNonPlayerBattleState = normalBattleState;
            if (gameMode == "Peaceful") {
                currentNonPlayerBattleState = peacefulBattleState;
            }

            // notify non-player entities current state
            notifyEnemies();
        }
    }

    /**
     * Notify all non-players of their current movement and combat state
     */
    private void notifyEnemies() {
        List<NonPlayer> enemies = getDungeon().getEnemies();
        if (!enemies.isEmpty()) {
            for (NonPlayer nonPlayer : enemies) {
                nonPlayer.setMovingState(currentEnemyState);
                nonPlayer.setBattleState(currentNonPlayerBattleState);
            }
        }

    }

    private void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Check whether the entered mercenary and the player are within the two cells
     * of the top, bottom, left and right
     * 
     * @param mercenaryPosition the position of target mercenary
     * @return true if the position of the mercenary in the range, otherwise return
     *         false
     */
    private boolean checkBribeRange(Position mercenaryPosition) {
        int playerX = getX();
        int playerY = getY();
        int mercenaryX = mercenaryPosition.getX();
        int mercenaryY = mercenaryPosition.getY();
        // check player is within 2 cardinal tiles to the mercenary, only
        // up/down/left/right
        if (playerX == mercenaryX) {
            return Math.abs(playerY - mercenaryY) <= 2;
        }
        if (playerY == mercenaryY) {
            return Math.abs(playerX - mercenaryX) <= 2;
        }
        return false;
    }

    /**
     * Add the input mercenary to the player's allies
     * 
     * @param mercenary Bribed mercenary
     */
    private void addAlly(Mercenary mercenary) {
        allies.add(mercenary);
        mercenary.setBribe();
    }

    /**
     * Check whether the input spawner position is adjacent to the player
     * 
     * @param spawnerPosition position of the target spawner
     * @return true if the position of the spawner is adjacent to the player,
     *         otherwise return false
     */
    private boolean checkSpawnerAdjacent(Position spawnerPosition) {
        int spawnerX = spawnerPosition.getX();
        int spawnerY = spawnerPosition.getY();
        List<Position> adjacentPositions = getPosition().getAdjacentPositions();
        return adjacentPositions.stream().anyMatch(e -> e.getX() == spawnerX && e.getY() == spawnerY);
    }

    /**
     * Check whether allies are still friendly
     */
    public void checkAlliesControlTime() {

        Iterator<Mercenary> it = allies.iterator();
        while (it.hasNext()) {
            Mercenary ally = it.next();
            int controlTime = ally.getControlTime();
            if (controlTime == 1) {
                ally.setControlTime(controlTime - 1);
                ally.setNonBribe();
                it.remove();
            } else if (controlTime > 1) {
                ally.setControlTime(controlTime - 1);
            }
        }
    }

}
