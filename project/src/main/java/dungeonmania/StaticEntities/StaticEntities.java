package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.movingentity.MovingEntity;

public abstract class StaticEntities extends Entity {

    public StaticEntities(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    @Override
    public void add() {
        getDungeon().staticEntitiesAdd(this);
    }

    @Override
    public void remove() {
        getDungeon().staticEntitiesRemove(this);
    }

    /**
     * for visitor pattern, different static entities will have different boolean returned for different moving entity.
     * @param movingEntity the moving entity who's visiting the static entity
     * @return a boolean shows whether or not the movingentity can pass this static entity.
     */
    public boolean accepted(MovingEntity movingEntity) {
        return true;
    }
}