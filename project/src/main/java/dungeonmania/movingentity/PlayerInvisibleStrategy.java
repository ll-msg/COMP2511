package dungeonmania.movingentity;

import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;

public class PlayerInvisibleStrategy implements PlayerVisitStrategy {

    @Override
    public boolean visit(Wall wall) {
        return true;
    }

    @Override
    public boolean visit(Boulder boulder) {
        return true;
    }

    @Override
    public boolean visit(Door door) {
        return true;
    }

    @Override
    public boolean visit(SwitchDoor switchdoor) {
        return true;
    }
}
