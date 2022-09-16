package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.movingentity.MovingEntity;

public class Wall extends StaticEntities{
    public static int count = 0;
    
    public Wall(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        super.setId("wall" + count);
        super.setType("wall");
        this.setInteractable(false);
        this.setCanBlock(true);
        count++;
    }
    
    @Override
    public boolean accepted(MovingEntity movingEntity) {
        return movingEntity.visit(this);
    }
}
