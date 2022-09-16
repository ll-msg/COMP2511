package dungeonmania.movingentity;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DoubleCalculation;
import dungeonmania.Dungeon;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.battle.NonPlayerBattleStrategy;
import dungeonmania.battle.NormalBattleStrategy;
import dungeonmania.util.Position;

public abstract class NonPlayer extends MovingEntity {
    private EnemyActionStrategy movingState = new NormalStrategy();
    private NonPlayerBattleStrategy BattleState;
    private boolean isAlly;
    private int trappedTick = 0;
    private boolean isBoss = false;
    private double initialHealthBar;

    public NonPlayer(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        BattleState = new NormalBattleStrategy(dungeon.getPlayer(), dungeon.getInventory());
    }

    /**
     * enemy move in normal state
     * 
     * @param direction
     */
    protected abstract Position normalMove();

    /**
     * enemy move in invisible state
     * 
     * @param direction
     */
    protected abstract Position invisibleMove();

    public void setMovingState(EnemyActionStrategy currentEnemyState) {
        this.movingState = currentEnemyState;
    }

    public void setBattleState(NonPlayerBattleStrategy BattleState) {
        this.BattleState = BattleState;
    }

    /**
     * enemy invincible state run away
     * 
     * @param player
     */
    protected Position flee(Player player) {

        List<Position> positions = getAdjacentPositions(getPosition());

        int maxDistance = findDistance(player.getPosition(), getPosition());
        Position direction = getPosition();
        // loop through four directions
        // check if it's farest from the player and whether it's blocked
        for (Position position : positions) {
            if (findDistance(player.getPosition(), position) > maxDistance && checkPositionValid(position)) {
                maxDistance = findDistance(player.getPosition(), position);
                direction = position;
            }
        }
        return direction;
    }

    /**
     * find polyline distance between two position
     * 
     * @param a
     * @param b
     * @return
     */
    protected int findDistance(Position a, Position b) {
        Position distancePosition = Position.calculatePositionBetween(a, b);
        return Math.abs(distancePosition.getX()) + Math.abs(distancePosition.getY());
    }

    /**
     * get the up/right/down/left adjacent positions of current posiiton
     * 
     * @param position
     * @return
     */
    protected List<Position> getAdjacentPositions(Position position) {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(position.getX(), position.getY() - 1));
        adjacentPositions.add(new Position(position.getX() + 1, position.getY()));
        adjacentPositions.add(new Position(position.getX(), position.getY() + 1));
        adjacentPositions.add(new Position(position.getX() - 1, position.getY()));
        return adjacentPositions;
    }

    @Override
    public void remove() {
        getDungeon().enemiesRemove(this);
    }

    @Override
    public void add() {
        getDungeon().enemiesAdd(this);
    }

    public void move() {
        Position newPosition = null;
        if (getTrappedTick() == 0) {
            newPosition = movingState.move(this);
        } else {
            // trapped by Swamp Tile
            newPosition = getPosition();
            setTrappedTick(getTrappedTick() - 1);
        }
        setPosition(newPosition);
    }

    @Override
    public void react() {
        BattleState.battle(this);
    }

    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean isAlly) {
        this.isAlly = isAlly;
    }

    public Armour getArmour() {
        return null;
    }

    @Override
    public boolean visit(SwampTile swampTile) {
        setTrappedTick(swampTile.getMovementFactor());
        return true;
    }

    /**
     * Teleport the current non-player moving entity by input portal
     * 
     * @param portal
     */
    public void teleport(Portal portal) {
        Portal piredPortal = portal.getPairedPortal();
        int layer = getPosition().getLayer();
        setPosition(new Position(piredPortal.getX(), piredPortal.getY(), layer));
    }

    public int getTrappedTick() {
        return trappedTick;
    }

    public void setTrappedTick(int trappedTick) {
        this.trappedTick = trappedTick;
    }

    public boolean getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(boolean state) {
        this.isBoss = state;
    }

    public void setInitialHealthBar(double initialHealthBar) {
        this.initialHealthBar = initialHealthBar;
    }

    /**
     * calculate healthbar and get healthbar within 0 - 1
     * 
     * @return
     */
    public double getHealthBar() {
        return DoubleCalculation.div(getHealth(), initialHealthBar);
    }
}
