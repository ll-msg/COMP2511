package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.ZombieToast;

public class ZombieToastSpawner extends StaticEntities {
    public static int count = 0;

    public ZombieToastSpawner(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        super.setId("zombietoastspawner" + count);
        super.setType("zombietoastspawner");
        this.setInteractable(true);
        this.setCanBlock(true);
        count++;
    }

    @Override
    public void interact(Player player) {
        player.interact(this);
    }

    @Override
    public void add() {
        super.add();
        getDungeon().zombieToastSpawnersAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().zombieToastSpawnersRemove(this);
    }
    /**
     * spawn zombies to the dungeon
     */
    public void spawnZombie() {
        ZombieToast zombie = new ZombieToast(getX(), getY(), getDungeon());
        zombie.add();
    }
}
