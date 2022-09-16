package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntities {
    public static int count = 0;
    private int movementFactor;
    private final int layer = 0;

    public SwampTile(int x, int y, Dungeon dungeon, int movementFactor) {
        super(x, y, dungeon);
        this.movementFactor = movementFactor;
        super.setId("swampTile" + count);
        super.setType("swampTile");
        this.setInteractable(false);
        this.setCanBlock(false);
        count++;
        Position position = new Position(x, y, layer);
        setPosition(position);
    }

    public int getMovementFactor() {
        return movementFactor;
    }
    
    @Override
    public boolean accepted(MovingEntity movingEntity) {
        return movingEntity.visit(this);
    }
}
