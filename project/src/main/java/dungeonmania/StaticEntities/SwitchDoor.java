package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.ActiveStrategy.ActiveStrategy;
import dungeonmania.movingentity.MovingEntity;

public class SwitchDoor extends StaticEntities implements logicInteract {
    public static int count = 0;
    private List<Trigger> adjTriggers = new ArrayList<>();
    private ActiveStrategy strategy;
    private boolean active = false;

    public SwitchDoor(int x, int y, Dungeon dungeon, ActiveStrategy strategy) {
        super(x, y, dungeon);
        super.setId("switch_door" + count);
        super.setType("switch_door");
        super.setInteractable(false);
        this.setCanBlock(false);
        this.strategy = strategy;
        count++;
    }
    
    @Override
    public boolean accepted(MovingEntity movingEntity) {
        return movingEntity.visit(this);
    }

    @Override
    public void add() {
        super.add();
        super.getDungeon().doorAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().doorRemove(this);
    }

    @Override
    public void updatestate() {
        this.active = strategy.isActive(adjTriggers);
        if (active) {
            setType("switch_door_open");
        } else {
            setType("switch_door");
        }
    }

    @Override
    public void triggersAdd(Trigger trigger) {
        adjTriggers.add(trigger);
    }

    public boolean isActive() {
        return active;
    }
}
