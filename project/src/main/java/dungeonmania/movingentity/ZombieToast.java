package dungeonmania.movingentity;

import java.util.Random;
import dungeonmania.Dungeon;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends NonPlayer {
    private final int amourRate = 10;
    private final double MaxHealth = 50;
    private static int counter = 0;
    private final int layer = 6;
    private Armour armour = null;
    private Random random;
    private final int damage = 1;

    public ZombieToast(int x, int y, Dungeon d) {
        super(x, y, d);
        setId("zombieToast " + counter);
        setType("zombieToast");
        random = new Random();
        setInteractable(false);
        setAlly(false);
        counter++;
        Position position = new Position(x, y, layer);
        setPosition(position);
        setCanBlock(true);
        setHealth(MaxHealth);
        setArmour();
        setAttackDamage(damage);
        setInitialHealthBar(MaxHealth);

    }

    public ZombieToast(int x, int y, Dungeon d, int seed) {
        this(x, y, d);
        // set up the seed of the random number for testing usage
        armour = null;
        random = new Random(seed);
        setArmour();

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
    public Armour getArmour() {
        return armour;
    }

    /**
     * Randomly generate armour for the current zombie based on amour generate rate
     */
    private void setArmour() {
        // randomly set armour for the zombieToast
        int testNum = random.nextInt(100);
        if (testNum < amourRate) {
            Armour armour = new Armour(1, 1, getDungeon());
            this.armour = armour;
        }
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
        // zombie cant use portal
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
