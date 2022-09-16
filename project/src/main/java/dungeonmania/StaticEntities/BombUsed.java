package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.ActiveStrategy.ActiveStrategy;
import dungeonmania.util.Position;

public class BombUsed extends StaticEntities implements logicInteract {
    public static int count = 0;
    private ActiveStrategy strategy;
    private List<Trigger> adjTriggers = new ArrayList<>();
    
    public BombUsed(int x, int y, Dungeon dungeon, ActiveStrategy strategy) {
        super(x, y, dungeon);
        setId("bombUsed" + count);
        setType("bombUsed");
        count++;
        super.setInteractable(false);
        this.setCanBlock(true);
        this.strategy = strategy;
    }

    public void explode() {
        Dungeon dungeon = super.getDungeon();
        List<Position> adjacents = this.getPosition().getAdjacentPositions();
        adjacents.add(getPosition());
        adjacents.stream().forEach(n -> {
            dungeon.getCollectableEntitiesWithPosition(n.getX(), n.getY()).stream().forEach(i -> i.remove());
            dungeon.getStaticEntitiesWithPosition(n.getX(), n.getY()).stream().forEach(i -> i.remove());
            dungeon.getEnemiesWithPosition(n.getX(), n.getY()).stream().forEach(i -> i.remove());
        });
    }

    @Override
    public void add() {
        super.add();
        super.getDungeon().bombAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().bombRemove(this);
    }

    @Override
    public void updatestate() {
        if (strategy.isActive(adjTriggers)) {
            explode();
        } else {
            return;
        }
    }

    @Override
    public void triggersAdd(Trigger trigger) {
        adjTriggers.add(trigger);
    }
}
