package dungeonmania.movingentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.util.Position;

public class Spider extends NonPlayer {
    private List<Position> adjacent;
    private Player player;
    private boolean isFirstSpawn = true;
    private boolean isOrigin;
    public static int count = 0;
    private final double health = 20;
    private final int layer = 3;
    private final int damage = 1;
    private Random random = new Random();
    private Position spawnPoint;

    public Spider(int x, int y, Dungeon dungeon, boolean isOrigin) {
        super(x, y, dungeon);
        setOrigin(isOrigin);
        setId("spider" + count);
        count++;
        setType("spider");
        setInteractable(false);
        setPlayer(dungeon.getPlayer());
        if (isOrigin == false) {
            Position spawn = new Position(randomSpawnX(), randomSpawnY(), layer);
            setPosition(spawn);
            setSpawnPoint(spawn);
        } else {
            Position position = new Position(x, y, layer);
            setPosition(position);
            setSpawnPoint(position);
        }
        
        adjacent = getPositionsAdjacent(getPosition());
        setHealth(health);
        setAlly(false);
        setCanBlock(true);
        setAttackDamage(damage);
        setInitialHealthBar(health);
    }
    
    public Spider(int x, int y, Dungeon dungeon, boolean isOrigin, long seed) {
        super(x, y, dungeon);
        setOrigin(isOrigin);
        setId("spider" + count);
        count++;
        setType("spider");
        setInteractable(false);
        setPlayer(dungeon.getPlayer());
        
        random = new Random(seed);
        Position spawn = new Position(randomSpawnX(), randomSpawnY(), layer);
        setPosition(spawn);
        setSpawnPoint(spawn);
        
        adjacent = getPositionsAdjacent(getPosition());
        setHealth(health);
        setAlly(false);
        setCanBlock(true);
        setAttackDamage(damage);
    }

    private void setSpawnPoint(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
 
    private void setPlayer(Player player) {
        this.player = player;
    }

    private void setOrigin(boolean isOrigin) {
        this.isOrigin = isOrigin;
    }

    public boolean getOrigin() {
        return isOrigin;
    }

    /**
     * random x number within 3 cells of the player
     * @return
     */
    private int randomSpawnX() {
        int startX = player.getX() - 3;
        return random.nextInt(7) + startX;
    }

    /**
     * random y number within 3 cells of the player
     * @return
     */
    private int randomSpawnY() {
        int startY = player.getY() - 3;
        return random.nextInt(7) + startY;
    }


    @Override
    public Position normalMove() {
        // if the spider is not in the original loop
        // start a new loop at current position
        if (!adjacent.contains(getPosition()) && !getPosition().equals(spawnPoint)) {
            setAdjacent(getPosition());
        }

        // normally circling
        Position nextMove = adjacent.get(0);
        adjacent.remove(0);
        adjacent.add(nextMove);

        // if spawn on top of the boulder
        // trapped until boulder is removed
        if (!checkPositionValid(getPosition())) {
            return getPosition();
        }

        if (isFirstSpawn) {

            isFirstSpawn = false;
            if (!checkPositionValid(nextMove)) {

                adjacent.add(adjacent.get(1));
                adjacent.add(adjacent.get(2));
                adjacent.add(adjacent.get(3));

                adjacent.remove(0);
                adjacent.remove(0);
                adjacent.remove(0);

                return getPosition();
            }
        }

        // if reach the blocked way
        // stay in the same cell
        if (!checkPositionValid(nextMove)) {
            Collections.reverse(adjacent);
            adjacent.add(adjacent.get(0));
            adjacent.add(adjacent.get(1));
            adjacent.remove(0);
            adjacent.remove(0);

            return getPosition();
        }

        nextMove.asLayer(layer);
        return nextMove;
    }

    @Override
    protected Position invisibleMove() {
        return normalMove();
    }

    @Override
    public boolean visit(Wall wall) {
        return true;
    }

    @Override
    public boolean visit(Boulder boulder) {
        return false;
    }

    @Override
    public boolean visit(Door door) {
        return true;
    }

    @Override
    public void add() {
        super.add();
        getDungeon().spiderAdd();
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().spiderRemove();
    }

    /**
     * get adjacent positions in an fixed order
     * for spider moving
     * @param position
     * @return
     */
    protected List<Position> getPositionsAdjacent(Position position) {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(getX(), getY() - 1));
        adjacentPositions.add(new Position(getX() + 1, getY() - 1));
        adjacentPositions.add(new Position(getX() + 1, getY()));
        adjacentPositions.add(new Position(getX() + 1, getY() + 1));
        adjacentPositions.add(new Position(getX(), getY() + 1));
        adjacentPositions.add(new Position(getX() - 1, getY() + 1));
        adjacentPositions.add(new Position(getX() - 1, getY()));
        adjacentPositions.add(new Position(getX() - 1, getY() - 1));
        return adjacentPositions;
    }

    private void setAdjacent(Position position) {
        this.adjacent = getPositionsAdjacent(position);
    }

    @Override
    public boolean visit(SwitchDoor switchdoor) {
        return true;
    }

}



