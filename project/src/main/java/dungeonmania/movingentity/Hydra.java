package dungeonmania.movingentity;

import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends NonPlayer {
    private final double MaxHealth = 60;
    private static int counter = 0;
    private final int layer = 9;
    private final int damage = 1;
    private Random random;

    public Hydra(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("hydra" + counter);
        setType("hydra");
        random = new Random();
        setInteractable(false);
        setAlly(false);
        counter++;
        Position position = new Position(x, y, layer);
        setPosition(position);
        setCanBlock(true);
        setHealth(MaxHealth);
        setAttackDamage(damage);
        setIsBoss(true);
        setInitialHealthBar(MaxHealth);
    }

    public Hydra(int x, int y, Dungeon dungeon, int seed) {
        this(x, y, dungeon);
        // set up the seed of the random number for testing usage
        random = new Random(seed);
    }

    @Override
    public boolean visit(Wall wall) {
        return false;
    }

    @Override
    public boolean visit(Boulder boulder) {
        return false;
    }

    @Override
    public boolean visit(Door door) {
        // check door status
        return door.isOpen();
    }

    @Override
    protected Position normalMove() {
        // generate a random direction
        Direction randomDirection = randomDirection();
        Position targePosition = getPosition().translateBy(randomDirection);
        // check whether the target position is reachable
        if (checkPositionValid(targePosition)) {
            return targePosition;
        } else
            return getPosition();
    }

    @Override
    protected Position invisibleMove() {
        return normalMove();
    }

    @Override
    public void teleport(Portal portal) {
        // hydra cant use portal
    }

    /**
     * Move randomly according to the generated random number from 0 to 99
     * 
     * @return the direction of next movement
     */
    private Direction randomDirection() {
        // generate random number from 0-99
        int randomNum = random.nextInt(100);
        if (randomNum < 25) {
            return Direction.DOWN;
        } else if (randomNum < 50 && randomNum >= 25) {
            return Direction.UP;
        } else if (randomNum >= 50 && randomNum < 75) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

}
