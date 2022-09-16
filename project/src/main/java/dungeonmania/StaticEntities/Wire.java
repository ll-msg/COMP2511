package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.ActiveStrategy.ActiveStrategy;
import dungeonmania.ActiveStrategy.OrStrategy;

public class Wire extends StaticEntities implements Trigger {
    public static int count = 0;
    private List<Trigger> adjTriggers = new ArrayList<>();
    private ActiveStrategy strategy = new OrStrategy();
    private boolean isOn;
    
    public Wire(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        super.setId("wire" + count);
        super.setType("wire");
        this.setInteractable(false);
        this.setCanBlock(false);
        count++;
    }

    @Override
    public void add() {
        super.add();
        super.getDungeon().wireAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().wireRemove(this);
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
