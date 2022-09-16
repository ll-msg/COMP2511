package dungeonmania.movingentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.StaticEntities;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.util.Position;

public class Mercenary extends NonPlayer {
    private Armour armour;
    private Player player;
    public static int count = 0;
    private final double health = 80;
    private final int layer = 4;
    private boolean detectedBattle = false;
    private final int damage = 1;
    private Random random = new Random();
    private int controlTime = -1;

    public Mercenary(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("mercenary" + count);
        count++;
        setType("mercenary");
        setInteractable(true);
        setPosition(new Position(x, y, layer));
        setPlayer(dungeon.getPlayer());
        setHealth(health);
        setAlly(false);
        setCanBlock(true);
        setArmour();
        setAttackDamage(damage);
        setInitialHealthBar(health);
    }

    public Mercenary(int x, int y, Dungeon dungeon, long seed) {
        super(x, y, dungeon);
        setId("mercenary" + count);
        count++;
        setType("mercenary");
        setInteractable(true);
        setPosition(new Position(x, y, layer));
        setPlayer(dungeon.getPlayer());
        setHealth(health);
        setAlly(false);
        setCanBlock(true);
        random = new Random(seed);
        setArmour();
        setAttackDamage(damage);
    }

    @Override
    public Armour getArmour() {
        return this.armour;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * randomly set armour for the mercenary
     */
    private void setArmour() {
        if (random.nextInt(100) < 20) {
            Armour armour = new Armour(1, 1, getDungeon());
            this.armour = armour;
        }
    }

    /**
     * set the mercenary as bribed
     */
    public void setBribe() {
        setAlly(true);
        setInteractable(false);
    }

    /**
     * set the mercenary as non-bribed
     */
    public void setNonBribe() {
        setAlly(false);
        setInteractable(true);
    }

    @Override
    public void interact(Player player) {
        player.interact(this);
    }

    /**
     *
     * find next position that move towards to the player
     *
     * @param grid
     * @param direction
     * @return
     */
    private Position towardsMove(List<Position> grid, Position direction) {

        Map<Position, Integer> dist = new HashMap<Position, Integer>();
        Map<Position, Position> visited = new HashMap<Position, Position>();
        PriorityQueue<Position> queue = new PriorityQueue<Position>((a, b) -> a.getValue() - b.getValue());
        boolean found = false;
        Position curr = direction;

        // initialize
        for (Position p : grid) {
            dist.put(p, Integer.MAX_VALUE);
            visited.put(p, null);
        }

        // distance from source to the source is 0
        dist.put(direction, 0);
        direction.setValue(0);
        visited.put(curr, curr);
        queue.offer(direction);

        int tick = getTrappedTick();

        while (!queue.isEmpty() && !found) {
            curr = queue.poll();
            if (!checkPositionValid(curr)) {
                setTrappedTick(tick);
                continue;
            }

            List<Position> positions = getAdjacentPositions(curr);

            for (Position p : positions) {

                if (!grid.contains(p)) {
                    continue;
                }

                int distance = 1;

                // check if there is stamp tile on the adjacent position
                StaticEntities entity = getDungeon().findStaticEntity(p);
                if (entity != null) {
                    entity.accepted(this);
                    distance = getTrappedTick();
                    setTrappedTick(tick);
                }

                if ((dist.get(curr) + distance) < dist.get(p)) {
                    dist.put(p, dist.get(curr) + distance);
                    visited.put(p, curr);
                    p.setValue(dist.get(p));
                    queue.offer(p);
                }
                // if find the player
                if (p.getX() == player.getX() && p.getY() == player.getY()) {
                    found = true;
                    visited.put(p, curr);
                    curr = p;
                }
            }
        }

        // do not find the player
        if (found == false) {
            return getPosition();
        }

        while (visited.get(curr) != direction) {
            curr = visited.get(curr);
        }

        curr.asLayer(layer);
        // if detect the battle
        // the mercenary moves twice as fast to take advantage
        if (!isAlly() && isDetectedBattle()) {
            setDetectedBattle(false);
            return towardsMove(grid, curr);
        }

        return curr;
    }

    @Override
    protected Position normalMove() {

        if (getPosition().equals(player.getPosition())) {
            return getPosition();
        }

        List<Position> grid = new ArrayList<Position>();

        int max_x = getDungeon().getMaxX() + 5;
        int max_y = getDungeon().getMaxY() + 5;
        int min_x = getDungeon().getMinX() - 5;
        int min_y = getDungeon().getMinY() - 5;

        for (int i = min_x; i < max_x; i++) {
            for (int j = min_y; j < max_y; j++) {
                Position p = new Position(i, j);
                grid.add(p);
            }
        }

        Position nextStep = towardsMove(grid, getPosition());
        StaticEntities entity = getDungeon().findStaticEntity(nextStep);
        if (entity != null) {
            entity.accepted(this);
        }
        return nextStep;
    }

    @Override
    protected Position invisibleMove() {
        return getPosition();
    }

    @Override
    public void remove() {
        if (isAlly()) {
            player.removeAlly(this);
        }
        getDungeon().enemiesRemove(this);
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
        return door.isOpen();
    }

    public boolean isDetectedBattle() {
        return detectedBattle;
    }

    public void setDetectedBattle(boolean detectedBattle) {
        this.detectedBattle = detectedBattle;
    }

    public int getControlTime() {
        return controlTime;
    }

    public void setControlTime(int controlTime) {
        this.controlTime = controlTime;
    }

}