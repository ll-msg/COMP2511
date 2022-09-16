package dungeonmania.Goals;

import dungeonmania.Dungeon;

public class EnemiesGoal extends Goal{
    public EnemiesGoal(Dungeon dungeon) {
        super(dungeon);
        setComposite(false);
    }

    @Override
    public void update() {
        setAchieved(getDungeon().getEnemies().size() == getDungeon().getPlayer().getNumOfAllies());
    }

    @Override
    public String toString() {
        return ":mercenary";
    }
}
