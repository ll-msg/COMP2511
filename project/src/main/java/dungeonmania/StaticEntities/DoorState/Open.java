package dungeonmania.StaticEntities.DoorState;

import dungeonmania.StaticEntities.Door;

public class Open implements DoorState{
    Door door;

    public Open(Door door) {
        this.door = door;
    }

    public boolean isBlocked() {
        return false;
    }

    public void tryOpen() {
        return; //do nothing 
    }
}
