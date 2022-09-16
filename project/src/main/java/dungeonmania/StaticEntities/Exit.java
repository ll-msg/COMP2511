package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;

public class Exit extends StaticEntities{
    public static int count = 0;
    public boolean isPlayerOnExit = false;
    public Exit(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        super.setId("exit" + count);
        super.setType("exit");
        this.setInteractable(false);
        this.setCanBlock(false);
        count++;
    }
    
    public boolean isPlayerOnExit() {
        return isPlayerOnExit;
    }

    public void setPlayerOnExit(boolean isPlayerOnExit) {
        this.isPlayerOnExit = isPlayerOnExit;
    }
    
    @Override
    public void react() {
        this.setPlayerOnExit(true);
    }
}
