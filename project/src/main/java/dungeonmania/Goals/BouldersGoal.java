package dungeonmania.Goals;

import dungeonmania.Dungeon;
import dungeonmania.StaticEntities.Switch;

public class BouldersGoal extends Goal{
    public BouldersGoal(Dungeon dungeon) {
		super(dungeon);
		setComposite(false);
	}
	
	@Override
	public void update() {
		setAchieved(getDungeon().getSwitches().stream().allMatch(Switch::isSwitched));
	}

	@Override
    public String toString() {
        return ":switch";
    }
}
