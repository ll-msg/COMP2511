package dungeonmania.Goals;

import dungeonmania.Dungeon;

public class TreasureGoal extends Goal{
    public TreasureGoal(Dungeon dungeon) {
		super(dungeon);
		setComposite(false);
	}

    public void update() {
        setAchieved(getDungeon().getTreasures().size() == 0);
    }

    @Override
    public String toString() {
        return ":treasure";
    }
}
