package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities {
    public static int count = 0;

    public Boulder(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        super.setId("boulder" + count);
        super.setType("boulder");
        this.setInteractable(false);
        this.setCanBlock(true);
        count++;
    }

    /**
     * test if the boulder can be pushed with given direction
     * @param direction the direction given to be test if the boulder can be pushed
     * @return the result shows that if the boulder can be pushed with given direction
     */
    public boolean canBePushed(Direction direction) {
        Position newPosition = this.getPosition().translateBy(direction);
        List<Entity> entitiesOnThatPosition = getDungeon().getEntitiesWithPosition(newPosition.getX(),
                newPosition.getY());
        if (entitiesOnThatPosition.size() == 0) {
            return true;
        }

        return entitiesOnThatPosition.stream().allMatch(entity -> entity.isCanBlock() == false);
    }

    /**
     * push the boulder to the given direction
     * @param direction the direction which the boulder is pushed
     */
    public void push(Direction direction) {
        if (getDungeon().getSwitchByPosition(getX(), getY()) != null) {
            Switch sw = getDungeon().getSwitchByPosition(getX(), getY());
            sw.setIsOn(false);
            sw.updateState(new ArrayList<Trigger>());
        }

        this.setPosition(this.getPosition().translateBy(direction));
        if (getDungeon().getSwitchByPosition(getX(), getY()) != null) {
            Switch sw1 = getDungeon().getSwitchByPosition(getX(), getY());
            //sw1.explodeAdjacentBomb();
            sw1.setIsOn(true);
            sw1.updateState(new ArrayList<Trigger>());
        }
    }

    @Override
    public boolean accepted(MovingEntity movingEntity) {
        return movingEntity.visit(this);
    }
}
