package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.ActiveStrategy.ActiveStrategy;

public class Switch extends StaticEntities implements Trigger {
    public static int count = 0;
    private List<Trigger> adjTriggers = new ArrayList<>();
    private ActiveStrategy strategy;
    private boolean isOn = false;

    public Switch(int x, int y, Dungeon dungeon, ActiveStrategy strategy) {
        super(x, y, dungeon);
        super.setId("switch" + count);
        super.setType("switch");
        this.setInteractable(false);
        this.setCanBlock(false);
        this.strategy = strategy;
        count++;
    }

    @Override
    public void add() {
        super.add();
        super.getDungeon().switchAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().switchRemove(this);
    }

    /**
     * check if there is a boulder on the switch, and return true if there is a boulder which means the switch is switched
     * @return a boolean which shows if the switch is switched
     */
    public boolean isSwitched() {
		return getDungeon().getEntitiesWithPosition(getX(), getY()).stream().anyMatch(entity -> entity.getType().equals("boulder"));
	}

    @Override
    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public ActiveStrategy getStrategy() {
        return strategy;
    }

    @Override
    public List<Trigger> getAdjTriggers() {
        return adjTriggers;
    }

    @Override
    public boolean getIsOn() {
        return isOn;
    }

    @Override
    public void triggersAdd(Trigger trigger) {
        adjTriggers.add(trigger);
    }

    @Override
    public void updateState(List<Trigger> ls) {
        int count = 0;
        for (Trigger trigger : adjTriggers) {
            if (!ls.contains(trigger)) {
                trigger.setIsOn(trigger.getStrategy().isActive(trigger.getAdjTriggers()));
                ls.add(trigger);
                count++;
            }
        }

        if (count == 0) {
            return;
        } else {
            for (Trigger trigger : adjTriggers) {
                trigger.updateState(ls);
            }
        }
    }
}
