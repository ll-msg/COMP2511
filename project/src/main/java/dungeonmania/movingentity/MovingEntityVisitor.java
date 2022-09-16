package dungeonmania.movingentity;

import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;

public interface MovingEntityVisitor {
    /**
     * Check whether the position of moving entity will be blocked by the wall in
     * current movement
     * 
     * @param wall that overlaps the position of moving entity's position
     * @return true if the position of moving entity won't be blocked by the input
     *         wall, otherwise false
     */
    public boolean visit(Wall wall);

    /**
     * Check whether the position of moving entity will be blocked by the input
     * boulder in current movement
     * 
     * @param boulder that overlapsthe position of moving entity's position
     * @return true if the position of moving entity won't be blocked by the input
     *         boulder, otherwise false
     */
    public boolean visit(Boulder boulder);

    /**
     * Check whether the position of moving entity will be blocked by the input door
     * in current movement
     * 
     * @param door that overlaps the position of moving entity's position
     * @return true if the position of moving entity won't be blocked by the input
     *         door, otherwise false
     */
    public boolean visit(Door door);

    /**
     * Obtain the number of ticks it takes to traverse the tile
     * 
     * @param swampTile that overlaps the position of moving entity's position
     * @return true if the position of moving entity won't be blocked by the input
     *         door, otherwise false
     */
    public boolean visit(SwampTile swampTile);

    /**
     * Check whether the position of moving entity will be blocked by the input switch door
     * in current movement
     * 
     * @param door that overlaps the position of moving entity's position
     * @return true if the position of moving entity won't be blocked by the input
     *         switch door, otherwise false
     */
    public boolean visit(SwitchDoor switchdoor);
}
