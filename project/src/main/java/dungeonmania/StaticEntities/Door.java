package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.StaticEntities.DoorState.Closed;
import dungeonmania.StaticEntities.DoorState.DoorState;
import dungeonmania.StaticEntities.DoorState.Open;
import dungeonmania.movingentity.MovingEntity;

public class Door extends StaticEntities{
    public static int count = 0;
    private DoorState state;
    private DoorState closed;
    private DoorState open;
    public int key;

    public Door(int x, int y,Dungeon dungeon, int key) {
        super(x, y, dungeon);
        super.setId("door" + count);
        super.setType("lockedDoor");
        closed = new Closed(this);
        open = new Open(this);
        state = closed;
        this.key = key;
        super.setInteractable(false);
        this.setCanBlock(false);
        count++;
    }
    
    public DoorState getState() {
        return state;
    }

    public void setState(DoorState state) {
        this.state = state;
    }

    public DoorState getClosed() {
        return closed;
    }
    
    public DoorState getOpen() {
        return open;
    }

    @Override
    public void react() {
        this.state.tryOpen();
    }

    public int getKey() {
        return key;
    }


    public boolean isOpen() {
        return !this.getState().isBlocked();
    }
    
    @Override
    public boolean accepted(MovingEntity movingEntity) {
        return movingEntity.visit(this);
    }
}
