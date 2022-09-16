package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.ActiveStrategy.ActiveStrategy;

public class LightBulb extends StaticEntities implements logicInteract {
    public static int count = 0;
    private ActiveStrategy strategy;
    private List<Trigger> adjTriggers = new ArrayList<>();

    public LightBulb(int x, int y, Dungeon dungeon, ActiveStrategy strategy) {
        super(x, y, dungeon);
        super.setId("bulb" + count);
        super.setType("light_bulb_off");
        this.setInteractable(false);
        this.setCanBlock(false);
        this.strategy = strategy;
        count++;
    }

    @Override
    public void add() {
        super.add();
        super.getDungeon().bulbAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().bulbRemove(this);
    }

    @Override
    public void updatestate() {
        if (strategy.isActive(adjTriggers)) {
            setType("light_bulb_on");
        } else {
            setType("light_bulb_off");
        }
    }

    @Override
    public void triggersAdd(Trigger trigger) {
        adjTriggers.add(trigger);
    }
    
}
