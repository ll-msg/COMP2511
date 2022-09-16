package dungeonmania.movingentity;

import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;

public class PlayerNormalStrategy implements PlayerVisitStrategy {
    private Player player;

    public PlayerNormalStrategy(Player player) {
        this.player = player;
    }

    @Override
    public boolean visit(Wall wall) {
        return false;
    }

    @Override
    public boolean visit(Boulder boulder) {
        // check whether the boulder can be pushed
        if (boulder.canBePushed(player.getDirection())) {
            boulder.push(player.getDirection());
            return true;
        }
        return false;
    }

    @Override
    public boolean visit(Door door) {
        // try to open door first
        door.react();
        // check door status
        return door.isOpen();
    }

    @Override
    public boolean visit(SwitchDoor switchdoor) {
        return switchdoor.isActive();
    }

}
