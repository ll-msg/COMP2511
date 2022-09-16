package dungeonmania.movingentity;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

    private final int damage = 2;
    private final int layer = 10;
    private final double health = 80;
    public static int counter = 0;

    public Assassin(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        setId("assassin" + counter);
        setType("assassin");
        setAttackDamage(damage);
        setHealth(health);
        setPosition(new Position(x, y, layer));
        counter++;
        setInitialHealthBar(health);
    }

    public Assassin(int x, int y, Dungeon dungeon, long seed) {
        super(x, y, dungeon, seed);
        setId("assassin" + counter);
        setType("assassin");
        setAttackDamage(damage);
        setHealth(health);
        setPosition(new Position(x, y, layer));
        counter++;
        setInitialHealthBar(health);
    }

    @Override
    public void interact(Player player) {
        player.bribeAssassin(this);
    }
}
